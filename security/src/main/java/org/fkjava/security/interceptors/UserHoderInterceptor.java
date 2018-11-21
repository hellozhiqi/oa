package org.fkjava.security.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fkjava.identity.domain.User;
import org.fkjava.identity.util.UserHoder;
import org.fkjava.security.domain.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserHoderInterceptor extends HandlerInterceptorAdapter {

	//拦截请求之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取Spring Security里面的UserDetails对象,将该对象转化为User ,并存储到当前线程
		
//		if (SecurityContextHolder//
//				.getContext()//
//				.getAuthentication() == null) {
//			return true;
//		}

		Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (details instanceof UserDetails) {

			UserDetails userDetails = (UserDetails) details;
			User user = new User();
			user.setId(userDetails.getId());
			user.setName(userDetails.getName());
			UserHoder.set(user);//存放到ThreaLocal里面
		}
		return true;
	}

	//拦截请求之后
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		 
		UserHoder.remove();//清理现场
	}
}
