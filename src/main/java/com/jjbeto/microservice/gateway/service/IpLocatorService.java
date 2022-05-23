package com.jjbeto.microservice.gateway.service;

import com.jjbeto.microservice.gateway.client.IpLocatorClient;
import org.springframework.stereotype.Service;

@Service
public class IpLocatorService {

    private final IpLocatorClient ipLocatorClient;

    public IpLocatorService(IpLocatorClient ipLocatorClient) {
        this.ipLocatorClient = ipLocatorClient;
    }

    public String getCurrencyByIp(String ip) {
        return ipLocatorClient.getCurrency(ip);
    }

}
