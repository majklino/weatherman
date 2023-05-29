package com.main.weatherman.routes;

import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.main.weatherman.model.City;
import com.main.weatherman.model.Country;
import com.main.weatherman.model.Measurement;
import com.main.weatherman.services.CityService;
import com.main.weatherman.services.CountryService;
import com.main.weatherman.services.MeasurementsService;
import com.main.weatherman.services.Measurer;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class ApiTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @MockBean
    private CityService cityService;

    @MockBean 
    private MeasurementsService measurementsService;

    @MockBean
    private Measurer measurer;

    @BeforeAll
    public void setUp() throws FileNotFoundException, IOException {
        Country country1 = new Country();
        country1.setCode("AA");
        country1.setName("TestCountryAA");
        List<Country> mockCountries = Arrays.asList(country1);

        City city = new City();
        city.setName("TestCity");
        city.setBelongsTo(country1);
        List<City> mockCities = Arrays.asList(city);

        Measurement m = new Measurement(1, 123456789, 25, "manual");
        List<Measurement> measurements = Arrays.asList(m);

        when(this.countryService.getAllCountries()).thenReturn(mockCountries);
        
        when(this.cityService.getAllCities()).thenReturn(mockCities);
        when(this.cityService.getCityById(1)).thenReturn(city);
        Mockito.doNothing().when(Mockito.mock(CityService.class)).removeCity("TestCity");

        when(this.measurer.measureNew("TestCity", true)).thenReturn(m);
        when(this.measurer.measure(city, false)).thenReturn(m);
        when(this.measurer.measureAll(true)).thenReturn(measurements);
        when(this.measurer.addCity("TestCity")).thenReturn(city);
        
    }

    @Test
    public void testRepeat() throws Exception {
        String input = "Hello!";
        String expectedOutput = input;

        mockMvc.perform(get("/api/repeat/" + input))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedOutput));

    }

    @Test
    public void testGetAllCountries() throws Exception{
        String expectedOutput = "TestCountryAA";

        mockMvc.perform(get("/api/country/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(expectedOutput));
    }

    @Test
    public void testGetAllCities() throws Exception{
        String expectedOutput = "TestCity";

        mockMvc.perform(get("/api/city/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(expectedOutput));
    }

    @Test
    public void testMeasureAll() throws Exception{
        mockMvc.perform(get("/api/measure/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].temp").value(25));
    }

    @Test
    public void testRemoveCity() throws Exception{
        String expectedOutput = "TestCity was deleted.";

        mockMvc.perform(get("/api/city/remove/TestCity"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedOutput));
    }

    @Test
    public void testAddCity() throws Exception{
        String expectedOutput = "TestCity";

        mockMvc.perform(get("/api/city/add/TestCity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedOutput));
    }

}
