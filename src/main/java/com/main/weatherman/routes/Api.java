package com.main.weatherman.routes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.main.weatherman.model.AverageTemp;
import com.main.weatherman.model.City;
import com.main.weatherman.services.CityService;
import com.main.weatherman.services.CountryService;
import com.main.weatherman.services.CsvExportService;
import com.main.weatherman.services.MeasurementsService;
import com.main.weatherman.services.Measurer;

@RestController
@RequestMapping("/api")
public class Api {
    private final CountryService countryService;
    private final CityService cityService;
    private final Measurer measurer;
    private final MeasurementsService measurementsService;
    private final CsvExportService csvService;
    private static final Logger logger = LoggerFactory.getLogger(Api.class);

    @Autowired
    public Api(CountryService c1, CityService c2, MeasurementsService m1, Measurer m2, CsvExportService csv){
        this.countryService = c1;
        this.cityService = c2;
        this.measurementsService = m1;
        this.measurer = m2;
        this.csvService = csv;
    }

    @GetMapping("/country/all")
    public Object getAllCountries(){
        logger.info("/country/all called...");
        return this.countryService.getAllCountries();
    }

    @GetMapping("/city/all")
    public Object getAllCities(){
        logger.info("/city/all called...");
        return this.cityService.getAllCities();
    }

    @GetMapping("measure/all")
    public Object measureAll(){
        logger.info("/measure/all called...");
        try {
            return this.measurer.measureAll(true);
        } catch (JsonMappingException e) {
            logger.error("JsonMapping Exception!", e);
            return e;
        } catch (JsonProcessingException e) {
            logger.error("Json Processing Exception!", e);
            return e;
        }
    }

    @GetMapping("measure/last/{cityId}")
    public Object measureLast(@PathVariable int cityId){
        logger.info("/measure/last/" + cityId + " called...");
        City city = this.cityService.getCityById(cityId);
        this.measure(city.getName());
        return this.measurementsService.getLastMeasurement(cityId);
    }

    @GetMapping("last/{cityId}")
    public Object last(@PathVariable int cityId){
        logger.info("/last/" + cityId + " called...");
        return this.measurementsService.getLastMeasurement(cityId);
    }

    @GetMapping("/measure/{cityname}")
    public Object measure(@PathVariable String cityname){
        logger.info("/measure/" + cityname + " called...");
        try {
            return this.measurer.measureNew(cityname, true);
        } catch (FileNotFoundException e) {
            logger.error("File not found!", e);
            return e;
        } catch (IOException e) {
            logger.error("IOException!", e);
            return e;
        }
    }

    @GetMapping("/city/add/{cityname}")
    public Object addCity(@PathVariable String cityname){
        logger.info("/city/add/" + cityname + " called...");
        try {
            return this.measurer.addCity(cityname);
        } catch (FileNotFoundException e) {
            logger.error("File not found!", e);
            return e;
        } catch (IOException e) {
            logger.error("IOException!", e);
            return e;
        }
    }

    @GetMapping("/city/remove/{cityname}")
    public String removeCity(@PathVariable String cityname){
        logger.info("/city/remove/" + cityname + " called...");
        this.cityService.removeCity(cityname);
        return cityname + " was deleted.";
    }

    @GetMapping("/avgs")
    public List<AverageTemp> getAvgsCities(){
        logger.info("/avgs called...");
        List<City> cities = this.cityService.getAllCities();
        List<AverageTemp> avgs = new ArrayList<AverageTemp>();
        for (City city : cities) {
            double avg1 = this.measurementsService.findAvgTempForCity(city.getId(), 1);
            double avg7 = this.measurementsService.findAvgTempForCity(city.getId(), 7);
            double avg14 = this.measurementsService.findAvgTempForCity(city.getId(), 14);

            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            avg1 = Double.parseDouble(decimalFormat.format(avg1));
            avg7 = Double.parseDouble(decimalFormat.format(avg7));
            avg14 = Double.parseDouble(decimalFormat.format(avg14));

            AverageTemp avg = new AverageTemp(city.getId(), avg1, avg7, avg14);
            avgs.add(avg);
        }

        return avgs;
    }

    @GetMapping("/csv")
    public ResponseEntity<ByteArrayResource> csv() throws IOException {
        logger.info("/csv called...");
        String path = "export.csv";
        this.csvService.writeToCsv(path);

        File file = new File(path);

        // Convert the file to a byte array
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Create a ByteArrayResource from the file content
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/repeat/{txt}")
    public String repeat(@PathVariable String txt){
        return txt;
    }
    
}
