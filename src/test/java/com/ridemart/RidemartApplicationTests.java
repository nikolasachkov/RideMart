package com.ridemart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class RidemartApplicationTests {

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> getEnv("DB_URL"));
		registry.add("spring.datasource.username", () -> getEnv("DB_USER"));
		registry.add("spring.datasource.password", () -> getEnv("DB_PASSWORD"));
		registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
		registry.add("spring.jpa.show-sql", () -> "true");
		registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQLDialect");
	}

	private static String getEnv(String key) {
		String value = System.getenv(key);
		if (value == null) throw new IllegalStateException("Missing required environment variable: " + key);
		return value;
	}

	@Test
	void contextLoads() {
	}
}
