package com.jjbeto.microservice.gateway.controller;

import com.jjbeto.microservice.gateway.service.CryptoLocatorService;
import com.jjbeto.microservice.gateway.service.IpDiscoveryService;
import com.jjbeto.microservice.gateway.service.IpLocatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private final IpDiscoveryService ipDiscoveryService;

    private final IpLocatorService ipLocatorService;

    private final CryptoLocatorService cryptoLocatorService;

    public IndexController(IpDiscoveryService ipDiscoveryService,
                           IpLocatorService ipLocatorService,
                           CryptoLocatorService cryptoLocatorService) {
        this.ipDiscoveryService = ipDiscoveryService;
        this.ipLocatorService = ipLocatorService;
        this.cryptoLocatorService = cryptoLocatorService;
    }

    @GetMapping("/")
    public String goToIndexPage(@RequestParam(name = "ip", required = false, defaultValue = "") String ip,
                                Model model,
                                HttpServletRequest request) {
        LOGGER.debug("ip '{}' informed", ip);

        if (ip.isBlank()) {
            model.addAttribute("filledIp", "");
            ip = ipDiscoveryService.getClientIpAddress(request);
            LOGGER.debug("ip '{}' recovered from request", ip);
        } else {
            model.addAttribute("filledIp", ip);
        }

        final var currency = ipLocatorService.getCurrencyByIp(ip);
        final var price = cryptoLocatorService.getCryptoPrice(currency);
        LOGGER.debug("Price found: {}", price);

        if ("No price available".equals(price)) {
            model.addAttribute("price", price);
        } else {
            model.addAttribute("price", "Current unit price is<br>%s".formatted(price));
        }
        return "index";
    }

}
