package com.jjbeto.microservice.gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

import static java.util.function.Predicate.not;

@Service
public class IpDiscoveryService {

    private final String[] ipHeaderLookup;

    public IpDiscoveryService(
            @Value("${app.ip-header-lookup}")
            String[] ipHeaderLookup
    ) {
        this.ipHeaderLookup = ipHeaderLookup;
    }

    public String getClientIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "0.0.0.0";
        }

        String ip = Arrays.stream(ipHeaderLookup)
                .map(request::getHeader)
                .filter(Objects::nonNull)
                .filter(not(String::isBlank))
                .filter(header -> !"unknown".equalsIgnoreCase(header))
                .map(header -> header.split(",")[0])
                .findFirst()
                .orElse(request.getRemoteAddr());

        if (ip == null || ip.isBlank()) {
            return "0.0.0.0";
        }
        return ip;
    }

}
