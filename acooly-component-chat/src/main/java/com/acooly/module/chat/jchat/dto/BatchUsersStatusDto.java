package com.acooly.module.chat.jchat.dto;

import java.util.List;

import com.acooly.core.utils.mapper.JsonMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ThinkPad
 *
 */
@Setter
@Getter
public class BatchUsersStatusDto {

	List<UsersStatusDto> usersStatusList;


	public static void main(String[] args) {
		JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
		String body = "{\"usersStatusList\":[{\"devices\":[],\"username\":\"adminUser0003\"},{\"devices\":[],\"username\":\"adminUser0001\"},{\"devices\":[],\"username\":\"adminUser0002\"},{\"devices\":[{\"login\":true,\"online\":false,\"platform\":\"a\"}],\"username\":\"cuifuq7\"}]}";
		System.out.println(body);
		BatchUsersStatusDto buso = jsonMapper.fromJson(body, BatchUsersStatusDto.class);
		System.out.println(buso.getUsersStatusList().get(3).getDevices().get(0).isOnline());
	}
}
