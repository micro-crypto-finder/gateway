package com.jjbeto.microservice.gateway.service;

import com.jjbeto.microservice.gateway.client.IpLocatorClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class IpLocatorService {

    public static final String CACHE_IP_LOCATOR = "IpLocatorService.getCurrencyByIp";

    private final IpLocatorClient ipLocatorClient;

    public IpLocatorService(IpLocatorClient ipLocatorClient) {
        this.ipLocatorClient = ipLocatorClient;
    }

    @Cacheable(CACHE_IP_LOCATOR)
    public String getCurrencyByIp(String ip) {
        return ipLocatorClient.getCurrency(ip);
    }

}
