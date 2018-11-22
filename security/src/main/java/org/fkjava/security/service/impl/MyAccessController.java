package org.fkjava.security.service.impl;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

public class MyAccessController {

	private static final Logger LOG = LoggerFactory.getLogger(MyAccessController.class);

	public boolean check(Authentication authentication, HttpServletRequest request) {

		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Set<String> urls = (Set<String>) session.getAttribute("urls");
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		if (!contextPath.isEmpty()) {
			// 如果有contextPath需要截取掉，因为数据库里面记录的URL都没有ContextPath
			requestURI = requestURI.substring(contextPath.length());
		}
		for (String url : urls) {

			if (url.equals(requestURI)) {
				return true;
			}
			//requestURL:/identity/show/edit/00642106-7c8a-48f8-b124-1516d3b7a076
			//url:/identity/show/edit/[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}
			// url里面是包含正则表达，例如/identity/show/edit/[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}
			if (requestURI.matches(url)) {
				// 匹配了正则表达式
				return true;
			}
		}
		LOG.trace("访问被拒绝，访问URL：{}，用户的URL集合：{}", requestURI, urls);
		return false;
	}
}
