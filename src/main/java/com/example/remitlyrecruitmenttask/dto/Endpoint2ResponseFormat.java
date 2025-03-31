package com.example.remitlyrecruitmenttask.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;



@JsonPropertyOrder({"countryISO2","countryName","swiftCodes","message"})
public class Endpoint2ResponseFormat {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String countryISO2;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String countryName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SwiftCodeJSONFormat> swiftCodes;

    public Endpoint2ResponseFormat(String message){
        this.message = message;
    }

    public Endpoint2ResponseFormat(){}

    public Endpoint2ResponseFormat(String countryISO2, String countryName, List<SwiftCodeJSONFormat> swiftCodes){
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.swiftCodes = swiftCodes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<SwiftCodeJSONFormat> getSwiftCodes() {
        return swiftCodes;
    }

    public void setSwiftCodes(List<SwiftCodeJSONFormat> swiftCodes) {
        this.swiftCodes = swiftCodes;
    }
}
