package com.main.weatherman.clients;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvClient {
    
    public String findCountryByCode(String code) throws FileNotFoundException, IOException{
        String csvFileName = "countries.csv";
        
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
            for (List<String> list : records) {
                if (list.get(1).equals(code)){
                    return list.get(0);
                }
            }
            return "NO_COUNTRY";
        }
    }
}
