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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
class IpLocatorServiceITCase {

    @Container
    static GenericContainer<?> ipLocator = Containers.IP_LOCATOR;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("feign.client.ip-locator.url", () -> "http://localhost:%s/api/ip-locator".formatted(ipLocator.getFirstMappedPort()));
    }

    @Autowired
    IpLocatorService ipLocatorService;

    @Test
    void findCanadianDollarForCanadianIp() {
        final String ip = "24.48.0.1";
        String currency = ipLocatorService.getCurrencyByIp(ip);
        assertEquals("CAD", currency);
    }

    @Test
    void findDefaultEuroForInvalidIp() {
        final String ip = "0.0.0.0";
        String currency = ipLocatorService.getCurrencyByIp(ip);
        assertEquals("EUR", currency);
    }

}
