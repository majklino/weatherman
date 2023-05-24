package com.main.weatherman.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


//@PropertySource("classpath:application.properties")
@Component
public class ApiClient {

    @Value("${openweathermap.key}")
    private String key;
    
    public ApiClient(){

    }

    public Object getCityInfoRequest(String cityname) throws JsonMappingException, JsonProcessingException{
        String limit = "1";

        String url = "http://api.openweathermap.org/geo/1.0/direct?q="+cityname+"&limit="+limit+"&appid="+this.key;
        
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.getForEntity(url, String.class);
        String responseBody = response.getBody();
        
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(responseBody, Object.class);
        return jsonObject;
    }

    public Object getWeatherInfo(double lat, double lon) throws JsonMappingException, JsonProcessingException{
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&appid="+this.key;
        
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.getForEntity(url, String.class);
        String responseBody = response.getBody();
        
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(responseBody, Object.class);
        return jsonObject;
    }
}
