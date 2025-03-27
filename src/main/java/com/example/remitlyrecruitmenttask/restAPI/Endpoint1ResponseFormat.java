package com.example.remitlyrecruitmenttask.restAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({"address","bankName","countryISO2","countryName","isHeadquarter","swiftCode","branches","message"})

public class Endpoint1ResponseFormat {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bankName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String countryISO2;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String countryName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isHeadquarter;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String swiftCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Endpoint1ResponseFormat> branches;

    public Endpoint1ResponseFormat(String message){
        this.message = message;
    }

    public Endpoint1ResponseFormat(String address, String bankName, String countryISO2, String countryName, Boolean isHeadquarter, String swiftCode, List<Endpoint1ResponseFormat> branches) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
        this.branches = branches;
    }

    public Endpoint1ResponseFormat(String address, String bankName, String countryISO2, Boolean isHeadquarter, String swiftCode){
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public List<Endpoint1ResponseFormat> getBranches() {
        return branches;
    }

    public void setBranches(List<Endpoint1ResponseFormat> branches) {
        this.branches = branches;
    }



}
