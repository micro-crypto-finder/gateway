package com.jjbeto.microservice.gateway.controller;

import com.jjbeto.microservice.gateway.service.CryptoLocatorService;
import com.jjbeto.microservice.gateway.service.IpLocatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTest {

    @MockBean
    CryptoLocatorService cryptoLocatorService;

    @MockBean
    IpLocatorService ipLocatorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void returnedPriceWhenNoIpIsGivenOrItsInitialPage() throws Exception {
        when(ipLocatorService.getCurrencyByIp("127.0.0.1")).thenReturn("EUR");
        when(cryptoLocatorService.getCryptoPrice("EUR")).thenReturn("EUR 1.99");

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("filledIp", equalTo("")))
                .andExpect(model().attribute("price", is("Current unit price is<br>EUR 1.99")));
    }

    @Test
    public void returnedPriceWhenAnIpIsGiven() throws Exception {
        when(ipLocatorService.getCurrencyByIp("10.10.10.10")).thenReturn("JPY");
        when(cryptoLocatorService.getCryptoPrice("JPY")).thenReturn("JPY 1.99");

        mockMvc.perform(get("/?ip=10.10.10.10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("filledIp", equalTo("10.10.10.10")))
                .andExpect(model().attribute("price", is("Current unit price is<br>JPY 1.99")));
    }

}
