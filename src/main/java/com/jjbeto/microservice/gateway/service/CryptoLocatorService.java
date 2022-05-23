package com.jjbeto.microservice.gateway.service;

import com.jjbeto.microservice.gateway.client.CryptoLocatorClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CryptoLocatorService {

    public static final String CACHE_CRYPTO_LOCATOR = "CryptoLocatorService.getCryptoPrice";

    private final CryptoLocatorClient cryptoLocatorClient;

    public CryptoLocatorService(CryptoLocatorClient cryptoLocatorClient) {
        this.cryptoLocatorClient = cryptoLocatorClient;
    }

    @Cacheable(CACHE_CRYPTO_LOCATOR)
    public String getCryptoPrice(String currency) {
        var priceDto = cryptoLocatorClient.getCryptoPrice(currency);
        if (priceDto == null || priceDto.getValue() == null || priceDto.getValue().isBlank()) {
            return "No price available";
        }
        return "%s %s".formatted(priceDto.getCode(), priceDto.getValue());
    }

}
