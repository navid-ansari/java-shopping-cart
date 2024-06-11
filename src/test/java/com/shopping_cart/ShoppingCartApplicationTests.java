package com.shopping_cart;

import com.shopping_cart.controller.HealthCheckController;
import com.shopping_cart.controller.ProductController;
import com.shopping_cart.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnableMongoAuditing
@DisplayName("Shopping cart application")
class ShoppingCartApplicationTests {

	@Autowired
	HealthCheckController healthCheckController;

	@Autowired
	UserController userController;

	@Autowired
	ProductController productController;

	@Test
	@DisplayName("Application context")
	void contextLoads() {
		assertThat(healthCheckController).isNotNull();
		assertThat(userController).isNotNull();
		assertThat(productController).isNotNull();
	}

}
