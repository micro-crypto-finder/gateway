package com.jjbeto.microservice.gateway.dto;

public class PriceDataDto {

    private String code;

    private String value;

    private String disclaimer;

    public PriceDataDto() {
    }

    public PriceDataDto(String code, String value, String disclaimer) {
        this.code = code;
        this.value = value;
        this.disclaimer = disclaimer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }
}
