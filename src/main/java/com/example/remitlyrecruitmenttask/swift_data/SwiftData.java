package com.example.remitlyrecruitmenttask.swift_data;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class SwiftData {

    @Id
    @Column(name = "swiftCode")
    //@NotNull(message = "parameter is required: 'swiftCode' : string")
    @NotBlank(message = "parameter is required and cannot be blank: 'swiftCode' : string")
    private String swiftCode;

    @Column(name = "bankName")
    @NotNull(message = "parameter is required: 'bankName' : string")
    private String bankName;

    @Column(name = "address")
    @NotNull(message = "parameter is required: 'address' : string")
    private String address;

    @Column(name = "townName")
    private String townName;

    @Column(name = "countryName")
    @NotNull(message = "parameter is required: 'countryName' : string")
    private String countryName;

    @Column(name = "timeZone")
    private String timeZone;

    @Column(name = "codeType")
    private String codeType;

    @Column(name = "countryISO2")
    @NotNull(message = "parameter is required and cannot be blank: 'countryISO2' : string")
    private String countryISO2;

    @Column(name = "isHeadquarter")
    @NotNull(message = "parameter is required: 'isHeadquarter' : bool")
    private Boolean isHeadquarter;

    @Column(name = "headQuarterCode")
    private String headQuarterCode;
    public SwiftData(){}

    public SwiftData(String swiftCode, String bankName, String address, String townName, String countryName, String timeZone, String codeType, String countryISO2, Boolean isHeadquarter) {
        this.swiftCode = swiftCode;
        this.bankName = bankName;
        this.address = address;
        this.townName = townName;
        this.countryName = countryName.toUpperCase();;
        this.timeZone = timeZone;
        this.codeType = codeType;
        this.countryISO2 = countryISO2.toUpperCase();
        this.isHeadquarter = isHeadquarter;
        this.headQuarterCode = swiftCode.substring(0,8);
    }

    public SwiftData(String address, String bankName, String countryISO2, String countryName, Boolean isHeadquarter, String swiftCode){
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2.toUpperCase();
        this.countryName = countryName.toUpperCase();
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName != null ? countryName.toUpperCase() : null;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2 != null ? countryISO2.toUpperCase() : null;
    }

    public Boolean getIsHeadquarter() {
        return isHeadquarter;
    }

    public void setIsHeadquarter(Boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public String getHeadQuarterCode() {
        return headQuarterCode;
    }

    public void setHeadQuarterCode(String headQuarterCode) {
        this.headQuarterCode = headQuarterCode;
    }
}
