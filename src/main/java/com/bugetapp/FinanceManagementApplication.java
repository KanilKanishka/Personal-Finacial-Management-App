package com.bugetapp;

import com.bugetapp.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class FinanceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceManagementApplication.class, args);
	}

}
