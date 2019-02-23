package cn.com.gczj.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("org.fkjava")
@EnableJpaRepositories
public class DepartmentConfig {
    
	public static void main(String[] args) {
		SpringApplication.run(DepartmentConfig.class, args);
	}
}
