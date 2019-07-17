package com.acooly.module.redpack;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Money;
import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.event.EventBus;
import com.acooly.module.member.service.MemberRealNameService;
import com.acooly.module.member.service.MemberService;
import com.acooly.module.redpack.business.service.cache.RedPackCacheDataService;
import com.acooly.module.redpack.dto.SendRedPackDto;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.enums.RedPackStatusEnum;
import com.acooly.module.redpack.enums.result.RedPackResultCodeEnum;
import com.acooly.module.redpack.event.dto.RedPackDto;
import com.acooly.module.redpack.event.dto.RedPackOrderDto;
import com.acooly.module.redpack.event.order.InsideRedPackOrderEvent;
import com.acooly.module.redpack.utils.RedPackUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 红包测试
 * 
 * @author CuiFuQ
 *
 */
@Slf4j
public class RedPackSendTest extends AbstractComponentsTest {

	static final String TEST_USERNAME = "cuifuq";
	
	
    //设置环境
    static {
        Apps.setProfileIfNotExists("cuifuq");
    }

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRealNameService memberRealNameService;

	static {
		Apps.setProfileIfNotExists("dlock");
	}

	@Autowired
	private DistributedLockFactory distributedLockFactory;

	private final int threads = 100;

	@Test
	public void testTrylock() throws Exception {
		final Lock lock = distributedLockFactory.newLock("dlock");
		LongAdder countSuccess = new LongAdder();
		long timeTasks = runTasks(threads, () -> {

			while (lock.tryLock()) {
				try {
					countSuccess.increment();
					log.info("dlock {} 获得锁成功：{}", Thread.currentThread().getName(),
							Dates.format(new Date(), "HH:mm:ss.SSS"));
				} finally {
					lock.unlock();
				}
				break;
			}

		});
		log.info("dlock test. threads:{},success:{}, ms:{}", threads, countSuccess.intValue(), timeTasks);
	}

	@Autowired
	private RedPackCacheDataService redPackCacheDataService;
	@Autowired
	private DistributedLockFactory factory;

	@Autowired
	private EventBus eventBus;

	@Test
	public void testTrylockByTimeout() throws Exception {
//	        final Lock lock = distributedLockFactory.newLock("dlock");
//	        LongAdder countSuccess = new LongAdder();
//	        long timeTasks = runTasks(threads, () -> {

		LongAdder countSuccess = new LongAdder();

		String redPackIds = "185";
		SendRedPackDto sendDto = new SendRedPackDto();
		sendDto.setRedPackId(Long.parseLong(redPackIds));
		sendDto.setUserId(100L);
		sendDto.setUserName("cuifuq");

		Long redPackId = sendDto.getRedPackId();
		String redPackLockKey = redPackCacheDataService.getRedPackLockKey(redPackId);

		Lock locks = factory.newLock(redPackLockKey);
		long timeTasks = runTasks(threads, () -> {
			RedPackOrderDto orderDto = new RedPackOrderDto();
			try {
				if (locks.tryLock(1, TimeUnit.SECONDS)) {
					log.info("红包组件:[发送红包],获取锁成功,lockKey:{},红包id:{}", redPackLockKey, redPackId);

					try {

						countSuccess.increment();
						RedPackDto redPackDto = redPackCacheDataService.getRedPackRedisDataByKey(redPackId);

						// 红包状态校验
//	        					checkRedPackStatus(redPackDto);

						// 用户允许参与的最大次数
//	        					checkUserPartakeNum(redPackDto, sendDto);

						// 校验过期
						Date overdueDateTime = redPackDto.getOverdueTime();
						if (overdueDateTime != null) {
							Long overdueTime = redPackDto.getOverdueTime().getTime();
							Long currentTime = (new Date()).getTime();
							if (currentTime > overdueTime) {
								throw new BusinessException(RedPackResultCodeEnum.RED_PACK_OVERDUE.message(),
										RedPackResultCodeEnum.RED_PACK_OVERDUE.code());
							}
						}

						// 红包算法
						long totalAmount = redPackDto.getTotalAmount();
						long surplusAmount = totalAmount - redPackDto.getSendOutAmount() - redPackDto.getRefundAmount();
						long surplusNum = redPackDto.getTotalNum() - redPackDto.getSendOutNum();

						Long redPackAmount = RedPackUtils.redPack(redPackCacheDataService.getRedPackRedisKey(redPackId),
								totalAmount, surplusAmount, surplusNum);

						redPackDto.setSendOutAmount(redPackDto.getSendOutAmount() + redPackAmount);
						redPackDto.setSendOutNum(redPackDto.getSendOutNum() + 1);
						redPackDto.setStatus(RedPackStatusEnum.PROCESSING);

						log.info("红包组件:[发送红包]lockKey:{},红包id:{},领取人 userId:{},userName:{},领取金额：{}", redPackLockKey,
								redPackId, sendDto.getUserId(), sendDto.getUserName(), Money.cent(redPackAmount));

						// 组建order订单
						orderDto = buildSendRedPackOrderDto(sendDto, redPackId, redPackAmount);

						// 设置红包缓存数据（红包，红包订单）
						redPackCacheDataService.setRedPackRedisData(redPackDto);
						redPackCacheDataService.setRedPackOrderRedisData(orderDto);

						// 发红包事件
//						InsideRedPackOrderEvent insideEvent = new InsideRedPackOrderEvent(orderDto);
//						eventBus.publishAfterTransactionCommitted(insideEvent);

					} catch (BusinessException e) {
						log.info("红包组件:[发送红包],业务处理失败,lockKey:{},红包id:{},{}", redPackLockKey, redPackId, e);
						throw new BusinessException(e.getMessage(), e.getCode());
					} finally {
						locks.unlock();
					}
				} else {
					log.info("红包组件:[发送红包],获取锁失败,lockKey:{},红包id:{}", redPackLockKey, redPackId);
//					throw new BusinessException(RedPackResultCodeEnum.RED_PACK_SEND_LOCK_ERROR.message(),
//							RedPackResultCodeEnum.RED_PACK_SEND_LOCK_ERROR.code());
					
					  log.info("dlock {} 未获得锁", Thread.currentThread().getName());
				}
			} catch (Exception e) {
				log.error("红包组件:[发送红包]发送红包失败,红包id:{},{}", redPackId, e);
				throw new BusinessException("红包组件:[发送红包]发送红包失败");
			}

		});
		log.info("dlock test. threads:{},success:{}, ms:{}", threads, countSuccess.intValue(), timeTasks);
	}
	
	
    @Test
    public void testTrylockByTimeout2() throws Exception {
    	
		String redPackIds = "185";
		SendRedPackDto sendDto = new SendRedPackDto();
		sendDto.setRedPackId(Long.parseLong(redPackIds));
		sendDto.setUserId(100L);
		sendDto.setUserName("cuifuq");

		Long redPackId = sendDto.getRedPackId();
		String redPackLockKey = redPackCacheDataService.getRedPackLockKey(redPackId);
    	
        final Lock lock = distributedLockFactory.newLock("dlock");
        LongAdder countSuccess = new LongAdder();
        long timeTasks = runTasks(threads, () -> {
            try {
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
        			RedPackOrderDto orderDto = new RedPackOrderDto();

                    try {
                        countSuccess.increment();
                        
                        
                        RedPackDto redPackDto = redPackCacheDataService.getRedPackRedisDataByKey(redPackId);

						// 红包状态校验
//	        					checkRedPackStatus(redPackDto);

						// 用户允许参与的最大次数
//	        					checkUserPartakeNum(redPackDto, sendDto);

						// 校验过期
						Date overdueDateTime = redPackDto.getOverdueTime();
						if (overdueDateTime != null) {
							Long overdueTime = redPackDto.getOverdueTime().getTime();
							Long currentTime = (new Date()).getTime();
							if (currentTime > overdueTime) {
								throw new BusinessException(RedPackResultCodeEnum.RED_PACK_OVERDUE.message(),
										RedPackResultCodeEnum.RED_PACK_OVERDUE.code());
							}
						}

						// 红包算法
						long totalAmount = redPackDto.getTotalAmount();
						long surplusAmount = totalAmount - redPackDto.getSendOutAmount() - redPackDto.getRefundAmount();
						long surplusNum = redPackDto.getTotalNum() - redPackDto.getSendOutNum();

						Long redPackAmount = RedPackUtils.redPack(redPackCacheDataService.getRedPackRedisKey(redPackId),
								totalAmount, surplusAmount, surplusNum);

						redPackDto.setSendOutAmount(redPackDto.getSendOutAmount() + redPackAmount);
						redPackDto.setSendOutNum(redPackDto.getSendOutNum() + 1);
						redPackDto.setStatus(RedPackStatusEnum.PROCESSING);

						log.info("红包组件:[发送红包]lockKey:{},红包id:{},领取人 userId:{},userName:{},领取金额：{}", redPackLockKey,
								redPackId, sendDto.getUserId(), sendDto.getUserName(), Money.cent(redPackAmount));

						// 组建order订单
						orderDto = buildSendRedPackOrderDto(sendDto, redPackId, redPackAmount);

						// 设置红包缓存数据（红包，红包订单）
						redPackCacheDataService.setRedPackRedisData(redPackDto);
						redPackCacheDataService.setRedPackOrderRedisData(orderDto);
                        
                        
                        
                        log.info("dlock {} 获得锁成功：{}", Thread.currentThread().getName(), Dates.format(new Date(), "HH:mm:ss.SSS"));
                    } catch (Exception e) {
                        log.warn("dlock {} 执行异常", Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                    }
                } else {
                    log.info("dlock {} 未获得锁", Thread.currentThread().getName());
                }
            } catch (Exception e) {
                log.error("dlock trylock error", e);
            }

        });
        log.info("dlock test. threads:{},success:{}, ms:{}", threads, countSuccess.intValue(), timeTasks);
    }
	
	

	@Test
	public void testlock() throws Exception {

		final Lock lock = distributedLockFactory.newLock("dlock");
		LongAdder countSuccess = new LongAdder();
		long timeTasks = runTasks(threads, () -> {
			try {
				lock.lock();
				countSuccess.increment();
				log.info("dlock {} 获得锁成功：{}", Thread.currentThread().getName(), System.currentTimeMillis());
			} finally {
				lock.unlock();
			}
		});
		log.info("dlock test. threads:{},success:{}, ms:{}", threads, countSuccess.intValue(), timeTasks);
	}

	/**
	 * 指定多个线程同时运行一个任务，测试并发性
	 *
	 * @param nThreads
	 * @param task
	 * @return
	 * @throws InterruptedException
	 */
	public long runTasks(int nThreads, final Runnable task) throws InterruptedException {
		// 真正的测试
		// 使用同步工具类，保证多个线程同时（近似同时）执行
		final CountDownLatch startGate = new CountDownLatch(1);
		// 使用同步工具类，用于等待所有线程都运行结束时，再统计耗时
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
		long start = System.currentTimeMillis();
		log.info("runTasks start:{},ready-threads:{}", start, nThreads);
		startGate.countDown();
		endGate.await();
		long end = System.currentTimeMillis();
		log.info("runTasks end:{},ready-threads:{}", end, nThreads);
		return end - start;
	}

	/**
	 * 组建order订单
	 * 
	 * @param sendDto
	 * @param redPackId
	 * @param redPackAmount
	 * @return
	 */
	private RedPackOrderDto buildSendRedPackOrderDto(SendRedPackDto sendDto, Long redPackId, Long redPackAmount) {
		RedPackOrderDto orderDto;
		orderDto = new RedPackOrderDto();
		orderDto.setRedPackId(redPackId);
		orderDto.setUserId(sendDto.getUserId());
		orderDto.setUserName(sendDto.getUserName());
		orderDto.setAmount(redPackAmount);
		orderDto.setType(RedPackOrderTypeEnum.RED_PACK);
		orderDto.setCreateTime(new Date());
		return orderDto;
	}
}
