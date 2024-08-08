package com.example.nms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD

@SpringBootApplication
=======
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.nms.config.ConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
>>>>>>> da385fc319984c31edb53b74b74758c03b81d0e2
public class ConfigurationManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationManagementServiceApplication.class, args);
	}

}
