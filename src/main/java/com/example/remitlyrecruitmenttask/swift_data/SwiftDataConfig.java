package com.example.remitlyrecruitmenttask.swift_data;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Configuration
public class SwiftDataConfig {

    @Bean
    CommandLineRunner commandLineRunner(SwiftDataRepository repository){
        return args -> {

            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("swift_data.csv");

            try{
                CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
                reader.readNext();
                List<String[]> rows = reader.readAll();

                for(String[] row : rows){
                    if(!repository.existsById(row[1])){
                        Boolean isHeadQuarter;
                        if(row[1].endsWith("XXX")){
                            isHeadQuarter = true;
                        }
                        else{
                            isHeadQuarter = false;
                        }

                        SwiftData newRow = new SwiftData(
                                row[1],row[3],row[4],row[5],row[6],row[7],row[2],row[0],isHeadQuarter
                        );
                        repository.save(newRow);
                    }

                }
            }
            catch(IOException e){
                throw new RuntimeException(e);
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }



        };


    }


}
