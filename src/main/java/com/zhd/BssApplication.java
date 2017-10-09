package com.zhd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhd.mapper")
public class BssApplication {

	public static void main(String[] args) {
		SpringApplication.run(BssApplication.class, args);
	}


}
