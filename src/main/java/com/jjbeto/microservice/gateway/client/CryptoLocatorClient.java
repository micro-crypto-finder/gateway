package com.jjbeto.microservice.gateway.client;

import com.jjbeto.microservice.gateway.dto.PriceDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "${feign.client.crypto-locator.name}", url = "${feign.client.crypto-locator.url}")
public interface CryptoLocatorClient {

    @RequestMapping(method = GET, path = "/price/bitcoin/{currency}")
    PriceDataDto getCryptoPrice(@PathVariable(value = "currency") String currency);

}
