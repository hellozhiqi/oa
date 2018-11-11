package org.fkjava.menu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("org.fkjava")//如果不写，则只扫描当前包，不扫identity
@EnableJpaRepositories
public class MenuConfig {

	public static void main(String[] args) {
		SpringApplication.run(MenuConfig.class, args);
	}
}
