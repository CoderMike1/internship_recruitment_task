package com.example.remitlyrecruitmenttask.restAPI;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"address","bankName","countryISO2","isHeadquarter","swiftCode"})
class SwiftCodeJSONFormat {

    private String address;
    private String bankName;
    private String countryISO2;
    private Boolean isHeadquarter;
    private String swiftCode;

    public SwiftCodeJSONFormat(String address, String bankName, String countryISO2, Boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public Boolean getIsHeadquarter() {
        return isHeadquarter;
    }

    public void setIsHeadquarter(Boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}




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
