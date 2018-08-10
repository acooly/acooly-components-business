package com.acooly.module.account;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Money;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.dto.AccountKeepInfo;
import com.acooly.module.account.dto.TransferInfo;
import com.acooly.module.account.service.AccountManageService;
import com.acooly.module.account.service.AccountTradeService;
import com.acooly.module.account.enums.CommonTradeCodeEnum;
import com.acooly.module.test.Main;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-04 00:56
 */
@Slf4j
//@BootApp(sysName = "acooly-components-test", httpPort = 8080)
public class AccountTradeServiceConcurrentTest {

    public static void main(String[] args) throws Exception {
        Apps.setProfileIfNotExists("sdev");
        final ApplicationContext ctx = new SpringApplication(Main.class).run(args);
        final AccountTradeService accountTradeService = ctx.getBean(AccountTradeService.class);
        final AccountManageService accountManageService = ctx.getBean(AccountManageService.class);

        long initBalance = 1000000;
        // 初始化
        AccountInfo accountInfo1 = new AccountInfo(1l);
        AccountInfo accountInfo2 = new AccountInfo(2l);
        AccountInfo accountInfo3 = new AccountInfo(3l);
        AccountInfo accountInfo4 = new AccountInfo(4l);

        accountManageService.openAccount(accountInfo1);
        accountManageService.openAccount(accountInfo2);
        accountManageService.openAccount(accountInfo3);
        accountManageService.openAccount(accountInfo4);

        // 1 充值 100000
        // 2 充值 100000
        // 3 充值 100000
        // 4 充值 100000
        // 并行测试：随机生成转账关系(1-4)，随机生成1-10的转账金额,并发同时100线程执行(准实时)，查看测试结果的流水和余额情况（调用核账程序）
        // 如：1->2:10, 1->3 10 , 2->3:20, 2->4:30, 4->1:30,...

        // 准备阶段
        for (long i = 1; i <= 4; i++) {
            accountTradeService.keepAccount(new AccountKeepInfo(i, CommonTradeCodeEnum.deposit, Money.cent(initBalance)));
        }


        AccountTradeServiceConcurrentTest test = new AccountTradeServiceConcurrentTest();
        long timeTasks = test.timeTasks(100, new Runnable() {
            @Override
            public void run() {
                try {
                    Pair<Long, Long> pair = getPair(1, 5);
                    Money amount = getRandomAmount();
                    // 转账
                    accountTradeService.transfer(new TransferInfo(pair.getFirst(), pair.getSecond(), amount));
                    log.info("转账成功 Thread-{}, {} -> {}：{}", Thread.currentThread().getId(), pair.getFirst(), pair.getSecond(), amount);

                } catch (Exception e) {
                    log.error("转账失败", e);
                }
            }
        });
        System.out.println(timeTasks);


    }


    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
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
        startGate.countDown();
        endGate.await();
        long end = System.currentTimeMillis();
        return end - start;
    }

    private static Money getRandomAmount() {
        return Money.cent(RandomUtils.nextLong(1, 20));
    }

    private static Pair<Long, Long> getPair(long start, long end) {
        long from = RandomUtils.nextLong(start, end);
        long to = RandomUtils.nextLong(start, end);
        while (from == to) {
            to = RandomUtils.nextLong(start, end);
        }
        return Pair.of(from, to);
    }
}


