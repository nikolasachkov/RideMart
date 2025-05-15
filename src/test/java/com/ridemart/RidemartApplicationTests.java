package com.ridemart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class RidemartApplicationTests {

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",
				() -> System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/ridemart"));
		registry.add("spring.datasource.username",
				() -> System.getenv().getOrDefault("DB_USER", "root"));
		registry.add("spring.datasource.password",
				() -> System.getenv().getOrDefault("DB_PASSWORD", "1234"));
		registry.add("spring.datasource.driver-class-name",
				() -> "com.mysql.cj.jdbc.Driver");
		registry.add("spring.jpa.hibernate.ddl-auto",
				() -> "update");
		registry.add("spring.jpa.show-sql",
				() -> "true");
		registry.add("spring.jpa.properties.hibernate.dialect",
				() -> "org.hibernate.dialect.MySQLDialect");
	}

	@Test
	void contextLoads() {
	}

}
