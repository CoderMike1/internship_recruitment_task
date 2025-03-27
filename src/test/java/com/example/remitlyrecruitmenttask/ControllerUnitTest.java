package com.example.remitlyrecruitmenttask;


import com.example.remitlyrecruitmenttask.restAPI.Controller;
import com.example.remitlyrecruitmenttask.swift_data.SwiftData;
import com.example.remitlyrecruitmenttask.swift_data.SwiftDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
public class ControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SwiftDataRepository swiftDataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnSwiftDataByCode() throws Exception {
        SwiftData data = new SwiftData();

        data.setSwiftCode("HBDCZIK892");
        data.setAddress("krakow karmelicka 190");
        data.setBankName("NeuBank");
        data.setCountryName("Poland");
        data.setCountryISO2("PL");
        data.setIsHeadquarter(false);

        Mockito.when(swiftDataRepository.findItemBySwiftCode("HBDCZIK892")).thenReturn(Optional.of(data));

        mockMvc.perform(get("/v1/swift-codes/HBDCZIK892"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("HBDCZIK892"))
                .andExpect(jsonPath("$.bankName").value("NeuBank"))
                .andExpect(jsonPath("$.countryISO2").value("PL"));
    }

    @Test
    void shouldNotFoundWhenWrongSwiftCode() throws Exception {

        Mockito.when(swiftDataRepository.findItemBySwiftCode("JDSBCHIW92X")).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/swift-codes/JDSBCHIW92X"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Swift code not found"));

    }

    @Test
    void shouldReturnSwiftCodesByCountry() throws Exception {
        SwiftData s1 = new SwiftData();
        s1.setSwiftCode("DRK8920XXX");
        s1.setCountryISO2("pl");
        s1.setCountryName("POLAND");
        s1.setIsHeadquarter(true);
        s1.setBankName("Bank1");
        s1.setAddress("krawcowa 1 90-890 krk");

        SwiftData s2 = new SwiftData();
        s2.setSwiftCode("KSHUERZ834XXX");
        s2.setCountryISO2("PL");
        s2.setCountryName("Poland");
        s2.setIsHeadquarter(true);
        s2.setBankName("Bank2");
        s2.setAddress("Address2");

        SwiftData s3 = new SwiftData();
        s3.setSwiftCode("DJ90230X9");
        s3.setCountryISO2("DE");
        s3.setCountryName("Germany");
        s3.setIsHeadquarter(false);
        s3.setBankName("Bank3");
        s3.setAddress("Address3");


        Mockito.when(swiftDataRepository.findSwiftCodesByCountry("PL")).thenReturn(List.of(s1,s2));

        mockMvc.perform(get("/v1/swift-codes/country/PL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2").value("PL"))
                .andExpect(jsonPath("$.countryName").value("POLAND"))
                .andExpect(jsonPath("$.swiftCodes[0].swiftCode").value("DRK8920XXX"))
                .andExpect(jsonPath("$.swiftCodes[1].countryISO2").value("PL"));


    }

    @Test
    void shouldReturnNotFoundWhenWrongCountry() throws Exception{

        Mockito.when(swiftDataRepository.findSwiftCodesByCountry("XX")).thenReturn(List.of());

        mockMvc.perform(get("/v1/swift-codes/country/XX"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Swift codes not found"));

    }


    @Test
    void shouldAddNewSwiftCode() throws Exception {
        SwiftData data = new SwiftData();
        data.setSwiftCode("ABC098XXX");
        data.setBankName("abcBank");
        data.setIsHeadquarter(true);
        data.setCountryName("Albania");
        data.setCountryISO2("AL");
        data.setAddress("random address 21b");

        mockMvc.perform(post("/v1/swift-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Successfully added new swift code"));
    }

    @Test
    void shouldReturnErrorWhenWrongJsonParameters() throws Exception{
        SwiftData s1 = new SwiftData();

        s1.setAddress("silver street 90b");
        s1.setBankName("silverBank");
        s1.setCountryISO2("FR");
        s1.setCountryName("France");
        s1.setIsHeadquarter(true);

        SwiftData s2 = new SwiftData();

        s2.setAddress("nowakowa 43");
        s2.setBankName("GermanBankV1");
        s2.setCountryName("Germany");
        s2.setIsHeadquarter(false);
        s2.setSwiftCode("FV34JKCMXXX");


        mockMvc.perform(post("/v1/swift-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(s1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("parameter is required and cannot be blank: 'swiftCode' : string"));

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("parameter is required and cannot be blank: 'countryISO2' : string"));


    }

    @Test
    void shouldReturnErrorWhenAddExistingSwiftCode() throws Exception{

        SwiftData sd = new SwiftData();
        sd.setSwiftCode("ABC098XXX");
        sd.setBankName("abcBank");
        sd.setIsHeadquarter(true);
        sd.setCountryName("Albania");
        sd.setCountryISO2("AL");
        sd.setAddress("random address 21b");
        Mockito.when(swiftDataRepository.findItemBySwiftCode(sd.getSwiftCode())).thenReturn(Optional.of(sd));

        mockMvc.perform(post("/v1/swift-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sd)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Swift code already exists"));


    }

    @Test
    void shouldDeleteSwiftCode() throws Exception {
        SwiftData newData = new SwiftData();

        newData.setSwiftCode("DELFIX635");

        Mockito.when(swiftDataRepository.findById("DELFIX635")).thenReturn(Optional.of(newData));

        mockMvc.perform(delete("/v1/swift-codes/DELFIX635"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully deleted swift code 'DELFIX635'"));

    }

    @Test
    void shouldReturnErrorWhenDeleteSwiftCode() throws Exception {

        Mockito.when(swiftDataRepository.findById("YBDSG729XXX")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/v1/swift-codes/YBDSG729XXX"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Error while deleting swift code - item does not exists"));

    }



}
