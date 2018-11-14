package org.fkjava.layout;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("org.fkjava")//如果不写，则只扫描当前包，不扫identity
@EnableJpaRepositories
public class LayoutConfig {

	public static void main(String[] args) {
		SpringApplication.run(LayoutConfig.class, args);
	}
	//增加spring-boot的自定义过滤器
	@Bean 
	public FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter(){
		
		ConfigurableSiteMeshFilter filter=new ConfigurableSiteMeshFilter();
		FilterRegistrationBean<ConfigurableSiteMeshFilter> bean=new FilterRegistrationBean<>();
		// 这种过滤器的本质，是拦截器，Spring Boot里面，所有的静态文件都不会进入Spring MVC中
		// 不会把js、css拦截到！
		bean.addUrlPatterns("/*");
		bean.setFilter(filter);
		bean.setAsyncSupported(true);//激活异步Servlet请求
		bean.setEnabled(true);//激活使用
		//仅处理来自浏览器的请求转发、错误转发的，Forward,include请求都不处理
		bean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ERROR);
		Map<String, String> initParameters =new HashMap<>();
		initParameters.put("decoratorMappings", "/*=/WEB-INF/views/layout/index.jsp\n"
				+ "/security/login=/WEB-INF/views/layout/simple.jsp");
		//initParameters.put("exclude", "/menu,/identity/role/*");
		//initParameters.put("exclude", "/security/index");
		bean.setInitParameters(initParameters);
		
		return bean;
	}
}
