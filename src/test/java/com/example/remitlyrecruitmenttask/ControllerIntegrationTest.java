package com.example.remitlyrecruitmenttask;

import com.example.remitlyrecruitmenttask.swift_data.SwiftData;
import com.example.remitlyrecruitmenttask.swift_data.SwiftDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class ControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    SwiftDataRepository repository;

    @Test
    void shouldReturnSwiftCode() throws Exception{
        SwiftData newData = new SwiftData();

        newData.setSwiftCode("YDH34GVF");
        newData.setAddress("krakow karmelicka 190");
        newData.setBankName("NeuBank");
        newData.setCountryName("Poland");
        newData.setCountryISO2("PL");
        newData.setIsHeadquarter(false);
        repository.save(newData);

        webTestClient.get()
                        .uri("/v1/swift-codes/"+newData.getSwiftCode())
                                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.swiftCode").isEqualTo(newData.getSwiftCode());
    }
    @Test
    void shouldReturnSwiftCodeFromDatabase() throws Exception{
        webTestClient.get()
                .uri("/v1/swift-codes/BCHICLR10R8")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.swiftCode").isEqualTo("BCHICLR10R8");
    }

    @Test
    void shouldReturnNotFoundWhenWrongSwiftCode() throws Exception{
        String testWrongSwiftCode = "abc89SSZ";
        webTestClient.get()
                .uri("/v1/swift-codes/"+testWrongSwiftCode)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("Swift code not found");
    }

    @Test
    void shouldReturnSwiftCodesBasedOnCountry() throws Exception{

    webTestClient.get().uri("v1/swift-codes/country/BG").exchange()
            .expectStatus().isOk().expectBody().jsonPath("$.swiftCodes[0].countryISO2").isEqualTo("BG");

    }

    @Test
    void shouldReturnErrorWhenWrongCountryCode() throws Exception{

        webTestClient.get().uri("/v1/swift-codes/country/prf").exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("Swift codes not found");
    }

    @Test

    void shouldAddNewSwiftCode() throws Exception{

        SwiftData s = new SwiftData();

        s.setSwiftCode("PSK837XJZ");
        s.setCountryISO2("PL");
        s.setCountryName("Poland");
        s.setIsHeadquarter(false);
        s.setBankName("Bank4");
        s.setAddress("Address4");

        webTestClient.post().uri("/v1/swift-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(s).exchange().expectStatus().isCreated()
                .expectBody().jsonPath("$.message").isEqualTo("Successfully added new swift code");

        Boolean exists = repository.existsById(s.getSwiftCode());

        assertTrue(exists,"Swift code should be saved in the database");

    }

    @Test
    void shouldReturnErrorWhenWrongJsonParameters() throws Exception {
        SwiftData s1 = new SwiftData();

        s1.setAddress("nowakowa 43");
        s1.setBankName("banm9");
        s1.setCountryISO2("FR");
        s1.setCountryName("France");
        s1.setIsHeadquarter(true);

        webTestClient.post().uri("/v1/swift-codes").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(s1).exchange().expectStatus().isBadRequest()
                .expectBody().jsonPath("$.message").isEqualTo("parameter is required and cannot be blank: 'swiftCode' : string");

        SwiftData s2 = new SwiftData();

        s2.setAddress("nowakowa 43");
        s2.setBankName("bank 80");
        s2.setCountryName("Germany");
        s2.setIsHeadquarter(false);
        s2.setSwiftCode("AFV3HFKMXXX");

        webTestClient.post().uri("/v1/swift-codes").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(s2).exchange().expectStatus().isBadRequest()
                .expectBody().jsonPath("$.message").isEqualTo("parameter is required and cannot be blank: 'countryISO2' : string");

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

        repository.save(sd);

        webTestClient.post().uri("/v1/swift-codes").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(sd).exchange().expectStatus().isBadRequest()
                .expectBody().jsonPath("$.message").isEqualTo("Swift code already exists");

    }

    @Test
    void shouldDeleteSwiftCode() throws Exception{
        String swiftCodeFromDatabase = "ALBPPLP1BMW";

        webTestClient.delete().uri("/v1/swift-codes/"+swiftCodeFromDatabase).exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.message").isEqualTo("Successfully deleted swift code '"+swiftCodeFromDatabase+"'");

        Boolean exists = repository.existsById(swiftCodeFromDatabase);

        assertFalse(exists,"should delete item from database");


    }
    @Test
    void shouldReturnErrorWhenDeleteSwiftCode() throws Exception{
        String randomSwiftCodeThatDoesntExist = "HJSBCX73Z";

        webTestClient.delete().uri("/v1/swift-codes/"+randomSwiftCodeThatDoesntExist)
                .exchange().expectStatus().isNotFound().expectBody().jsonPath("$.message")
                .isEqualTo("Error while deleting swift code - item does not exists");
    }


}
