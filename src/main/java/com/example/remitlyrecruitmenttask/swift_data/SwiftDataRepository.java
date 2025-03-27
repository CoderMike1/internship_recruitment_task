package com.example.remitlyrecruitmenttask.swift_data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SwiftDataRepository extends JpaRepository<SwiftData, String> {

    @Query("SELECT s FROM SwiftData s WHERE s.swiftCode = :swift_code")
    Optional<SwiftData> findItemBySwiftCode(String swift_code);

    @Query("SELECT s FROM SwiftData s WHERE s.headQuarterCode = :head_quarter_code and s.swiftCode != :swift_code")
    List<SwiftData> findBranches(String head_quarter_code,String swift_code);

    @Query("SELECT s FROM SwiftData s WHERE s.countryISO2 = :countryISO2code")
    List<SwiftData> findSwiftCodesByCountry(String countryISO2code);

}
