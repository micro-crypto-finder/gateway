package com.jjbeto.microservice.gateway.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class IpDiscoveryServiceTest {

    @Mock
    HttpServletRequest request;

    @Autowired
    IpDiscoveryService ipDiscoveryService;


    @Test
    void getClientIpAddressWithNullRequest() {
        var ip = ipDiscoveryService.getClientIpAddress(null);
        assertEquals("0.0.0.0", ip);
    }

    @Test
    void getClientIpAddressWithNoHeadersAndWithRemoteAddr() {
        when(request.getRemoteAddr()).thenReturn("1.1.1.1");
        var ip = ipDiscoveryService.getClientIpAddress(request);
        assertEquals("1.1.1.1", ip);
    }

    @Test
    void getClientIpAddressWithRemoteAddrHeader() {
        when(request.getHeader("REMOTE_ADDR")).thenReturn("1.1.1.1");
        when(request.getRemoteAddr()).thenReturn("2.2.2.2");
        var ip = ipDiscoveryService.getClientIpAddress(request);
        assertEquals("1.1.1.1", ip);
    }

    @Test
    void getClientIpAddressWithForwardIpListHeader() {
        when(request.getHeader("X-Forwarded-For")).thenReturn("1.1.1.1,2.2.2.2");
        when(request.getRemoteAddr()).thenReturn("3.3.3.3");
        var ip = ipDiscoveryService.getClientIpAddress(request);
        assertEquals("1.1.1.1", ip);
    }

}
