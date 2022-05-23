package com.jjbeto.microservice.gateway.service;

import com.jjbeto.microservice.gateway.client.CryptoLocatorClient;
import com.jjbeto.microservice.gateway.dto.PriceDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CryptoLocatorServiceTest {

    @Autowired
    CryptoLocatorService cryptoLocatorService;

    @MockBean
    CryptoLocatorClient cryptoLocatorClient;

    @Test
    void getPriceForValidCurrency() {
        when(cryptoLocatorClient.getCryptoPrice("EUR")).thenReturn(new PriceDataDto("EUR", "1.99", "Disclaimer"));
        var price = cryptoLocatorService.getCryptoPrice("EUR");
        assertEquals("EUR 1.99", price);
    }

    @Test
    void getInformedThatPriceIsNotAvailable() {
        when(cryptoLocatorClient.getCryptoPrice("EUR")).thenReturn(new PriceDataDto());
        var price = cryptoLocatorService.getCryptoPrice("EUR");
        assertEquals("No price available", price);
    }

}
