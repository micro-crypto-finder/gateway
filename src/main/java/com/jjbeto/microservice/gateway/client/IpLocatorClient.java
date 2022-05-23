package com.jjbeto.microservice.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "${feign.client.ip-locator.name}", url = "${feign.client.ip-locator.url}")
public interface IpLocatorClient {

    @RequestMapping(method = GET, path = "/currency/{ip}")
    String getCurrency(@PathVariable(value = "ip") String ip);

}
