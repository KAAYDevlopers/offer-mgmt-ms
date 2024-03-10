package com.abw12.absolutefitness.offermgmtms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Offer-Mgmt-MS",
		description = "Responsible to store and define offers based on some rules for the products and their categories.Also store the coupons which are applied by end customer",
		version = "3.0"))
@EnableTransactionManagement
public class OfferMgmtMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfferMgmtMsApplication.class, args);
	}

}
