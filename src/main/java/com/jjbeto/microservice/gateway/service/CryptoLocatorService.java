package com.jjbeto.microservice.gateway.service;

import com.jjbeto.microservice.gateway.client.CryptoLocatorClient;
import org.springframework.stereotype.Service;

@Service
public class CryptoLocatorService {

    private final CryptoLocatorClient cryptoLocatorClient;

    public CryptoLocatorService(CryptoLocatorClient cryptoLocatorClient) {
        this.cryptoLocatorClient = cryptoLocatorClient;
    }

    public String getCryptoPrice(String currency) {
        var priceDto = cryptoLocatorClient.getCryptoPrice(currency);
        if (priceDto == null || priceDto.getValue() == null || priceDto.getValue().isBlank()) {
            return "No price available";
        }
        return "%s %s".formatted(priceDto.getCode(), priceDto.getValue());
    }

}
