package org.fkjava.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fkjava.security.interceptors.UserHoderInterceptor;
import org.fkjava.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("org.fkjava") // 如果不写，则只扫描当前包，不扫identity
@EnableJpaRepositories
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	
	@Autowired
	private SecurityService securityConfig;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 登录页面
		registry.addViewController("/security/login").setViewName("security/login");
		//找到欢迎页
		registry.addViewController("/index").setViewName("security/index");
		//重定向到首页
		registry.addRedirectViewController("/", "/index");
	}

	// 应用拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserHoderInterceptor()).addPathPatterns("/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		String loginPage="/security/login";
		//登录失败,将用户名回显
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler(
				loginPage+"?error") {
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					request.getSession().setAttribute("loginName", request.getParameter("loginName"));
					//在重定向之前,将登录名存放在session中
					super.onAuthenticationFailure(request, response, exception);
				}
		};

		// 验证请求
		http.authorizeRequests()
				// 登录页面的地址和其他的静态页面都不要权限
				// /*表示目录下的任何地址，但是不包括子目录
				// /** 则连同子目录一起匹配
				.antMatchers("/security/login", "/css/**", "/js/**", "/webjars/**", "/static/**", "/public/**")
				.permitAll()// 不做访问判断
				.anyRequest()// 任何请求
				.authenticated()// 授权才能访问
				.and()// 且
				.formLogin()// 使用表单进行登录
				.loginPage("/security/login")// 登录页面的位置，默认是/login
				// 此页面不需要有对应的JSP，而且也不需要有对应代码，只要URL
				// 这个URL是Spring Security使用的，用来接收请求参数、调用Spring Security的鉴权模块
				.loginProcessingUrl("/security/do-login")// 处理登录的url
				.usernameParameter("loginName")// 登录 名的参数,与jsp的name关联
				.passwordParameter("password")// 同上
				.failureHandler(failureHandler)
				.and().logout().logoutUrl("/security/do-logout")
				// .and().httpBasic()// 也可以基于HTTP的标准验证方法（弹出对话框）
				.and().csrf();// 激活防跨站攻击功能
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setHideUserNotFoundExceptions(false);
		provider.setUserDetailsService(securityConfig);
		provider.setPasswordEncoder(passwordEncoder);
		auth.authenticationProvider(provider);
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityConfig.class, args);
	}
}
