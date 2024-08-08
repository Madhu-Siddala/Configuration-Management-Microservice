package com.example.nms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.nms.config.ConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class ConfigurationManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationManagementServiceApplication.class, args);
	}

}
