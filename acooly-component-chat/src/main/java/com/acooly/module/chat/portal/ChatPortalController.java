package com.acooly.module.chat.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.module.chat.ChatProperties;
import com.github.kevinsawicki.http.HttpRequest.Base64;

@Controller
@RequestMapping(value = "/portal/im/")
public class ChatPortalController {

	@Autowired
	private ChatProperties appProperties;

	@RequestMapping(value = "signature")
	@ResponseBody
	public String signature(HttpServletRequest request, HttpServletResponse response, Model model) {
		String appKey = appProperties.getChat().getAppKey();
		String masterSecret = appProperties.getChat().getMasterSecret();
		return Base64.encode(appKey + ':' + masterSecret);
	}
}
