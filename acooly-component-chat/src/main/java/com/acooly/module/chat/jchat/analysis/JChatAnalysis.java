package com.acooly.module.chat.jchat.analysis;

import java.util.List;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.chat.jchat.dto.BatchUsersStatusDto;
import com.acooly.module.chat.jchat.dto.UsersStatusDto;
import com.google.common.collect.Lists;

/**
 * im 解析器
 * 
 * @author ThinkPad
 *
 */
public class JChatAnalysis {

	/**
	 * 解析 在线的用户
	 * @param stringJson
	 * @return
	 */
	public static List<String> userOnline(String stringJson) {
		List<String> userOnline = Lists.newArrayList();
		JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
		String body = "{\"usersStatusList\":" + stringJson + "}";
		BatchUsersStatusDto buso = jsonMapper.fromJson(body, BatchUsersStatusDto.class);
		List<UsersStatusDto> usersStatusList = buso.getUsersStatusList();
		for (UsersStatusDto usersStatusDto : usersStatusList) {
			int size = usersStatusDto.getDevices().size();
			if (size > 0) {
				if (usersStatusDto.getDevices().get(0).isLogin())
					userOnline.add(usersStatusDto.getUserName());
			}
		}
		return userOnline;
	}
}
