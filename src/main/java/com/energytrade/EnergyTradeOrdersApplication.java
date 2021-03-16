package com.energytrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
/**
 * 
 * @author Soumyajit
 *
 */
@SpringBootApplication
@EnableJpaAuditing
public class EnergyTradeOrdersApplication {

	public static void main(String[] args) {
		System.out.println("Starting Energy Trade Orders Main");
		//System.setProperty("server.servlet.context-path");
		SpringApplication.run(EnergyTradeOrdersApplication.class, args);
		//, "/inmo"
		
	}
}
