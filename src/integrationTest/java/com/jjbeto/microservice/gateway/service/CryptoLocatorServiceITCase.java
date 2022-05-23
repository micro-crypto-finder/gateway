package com.jjbeto.microservice.gateway.service;

import com.jjbeto.microservice.gateway.Containers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class CryptoLocatorServiceITCase {

    // examples: 'EUR 47.8888', 'USD 30.3030'
    static final String PRICE_PATTERN = "^[A-Z]{3}\\s\\d+\\.\\d+$";

    @Container
    static GenericContainer<?> cryptoLocator = Containers.CRYPTO_LOCATOR;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("feign.client.crypto-locator.url", () -> "http://localhost:%s/api/crypto-locator".formatted(cryptoLocator.getFirstMappedPort()));
    }

    @Autowired
    CryptoLocatorService cryptoLocatorService;

    @Test
    void findCryptoPriceInEur() {
        final String currency = "EUR";
        String price = cryptoLocatorService.getCryptoPrice(currency);
        assertThat(price, matchesPattern(PRICE_PATTERN));
        assertTrue(price.startsWith("EUR"));
    }

    @Test
    void findCryptoPriceInCad() {
        final String currency = "CAD";
        String price = cryptoLocatorService.getCryptoPrice(currency);
        assertThat(price, matchesPattern(PRICE_PATTERN));
        assertTrue(price.startsWith("CAD"));
    }

}
