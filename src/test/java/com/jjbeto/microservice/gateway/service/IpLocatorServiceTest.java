package com.jjbeto.microservice.gateway.service;

import com.jjbeto.microservice.gateway.client.IpLocatorClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class IpLocatorServiceTest {

    @Autowired
    IpLocatorService ipLocatorService;

    @MockBean
    IpLocatorClient ipLocatorClient;

    @Test
    void getClientIpAddressWithNullRequest() {
        when(ipLocatorClient.getCurrency("1.1.1.1")).thenReturn("EUR");
        var currency = ipLocatorService.getCurrencyByIp("1.1.1.1");
        assertEquals("EUR", currency);
    }

}
