package com.example.remitlyrecruitmenttask.restAPI;


import com.example.remitlyrecruitmenttask.swift_data.SwiftData;
import com.example.remitlyrecruitmenttask.swift_data.SwiftDataRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="v1/swift-codes")
public class Controller {

    private final SwiftDataRepository repository;

    @Autowired
    public Controller(SwiftDataRepository repository){
        this.repository = repository;
    }


    @GetMapping("/{swift-code}")
    public ResponseEntity<Endpoint1ResponseFormat> getSwiftCode(@PathVariable("swift-code") String swiftCode){
        Optional<SwiftData> swiftDataOptional = repository.findItemBySwiftCode(swiftCode);
        if (!swiftDataOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Endpoint1ResponseFormat("Swift code not found"));
        }

        SwiftData data = swiftDataOptional.get();
        List<Endpoint1ResponseFormat> branches = null;
        if(data.getIsHeadquarter()){
            List<SwiftData> branchesOptional = repository.findBranches(data.getHeadQuarterCode(),data.getSwiftCode());
            if(!branchesOptional.isEmpty()){
                branches = branchesOptional.stream()
                        .map(s -> new Endpoint1ResponseFormat(
                                s.getAddress(),
                                s.getBankName(),
                                s.getCountryISO2(),
                                s.getIsHeadquarter(),
                                s.getSwiftCode()
                        )).toList();
            }
        }
        Endpoint1ResponseFormat resp = new Endpoint1ResponseFormat(
                data.getAddress(),
                data.getBankName(),
                data.getCountryISO2(),
                data.getCountryName(),
                data.getIsHeadquarter(),
                data.getSwiftCode(),
                branches
        );

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }


    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<Endpoint2ResponseFormat> getSwiftCodesByCountry(@PathVariable String countryISO2code){
        List<SwiftData> codes = repository.findSwiftCodesByCountry(countryISO2code.toUpperCase());
        if(codes.isEmpty()){
            Endpoint2ResponseFormat resp = new Endpoint2ResponseFormat("Swift codes not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        String countryName = codes.getFirst().getCountryName();
        List<SwiftCodeJSONFormat> swiftCodes = new ArrayList<>();
        for(SwiftData s: codes){
            swiftCodes.add(
                    new SwiftCodeJSONFormat(
                            s.getAddress(),
                            s.getBankName(),
                            s.getCountryISO2().toUpperCase(),
                            s.getIsHeadquarter(),
                            s.getSwiftCode()
                    )
            );
        }
        Endpoint2ResponseFormat resp = new Endpoint2ResponseFormat(
                countryISO2code.toUpperCase(),
                countryName,
                swiftCodes
        );

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }


    @PostMapping()
    public ResponseEntity<MessageResponse> addNewSwiftCode(@Valid @RequestBody SwiftData swiftData){
        Optional<SwiftData> optionalSwiftData = repository.findItemBySwiftCode(swiftData.getSwiftCode());
        if(optionalSwiftData.isPresent()){
            MessageResponse resp = new MessageResponse("Swift code already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        repository.save(swiftData);

        MessageResponse resp = new MessageResponse("Successfully added new swift code");

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);

    }


    @DeleteMapping("/{swift-code}")
    public ResponseEntity<MessageResponse> deleteSwiftCode(@PathVariable("swift-code") String swiftCode){
        Optional<SwiftData> swiftDataOptional = repository.findById(swiftCode);
        MessageResponse response;

        if(swiftDataOptional.isPresent()){
            repository.deleteById(swiftCode);
            response = new MessageResponse("Successfully deleted swift code '"+swiftCode+"'");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }
        else{
            response = new MessageResponse("Error while deleting swift code - item does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

}
