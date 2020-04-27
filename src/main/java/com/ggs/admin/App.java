package com.ggs.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ggs.admin.module.*.dao")
@ComponentScan(basePackageClasses= App.class)
@ServletComponentScan
public class App  extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(App.class,args);
	}
}
