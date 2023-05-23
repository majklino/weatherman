package com.main.weatherman.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiClient {
    
    public Object getCityInfoRequest(String cityname) throws JsonMappingException, JsonProcessingException{
        String limit = "1";
        String key = "0808f1653374e9b866e1b1a712fac9a7";
        String url = "http://api.openweathermap.org/geo/1.0/direct?q="+cityname+"&limit="+limit+"&appid="+key;
        
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.getForEntity(url, String.class);
        String responseBody = response.getBody();
        
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(responseBody, Object.class);
        return jsonObject;
    }

    public Object getWeatherInfo(float lat, float lon) throws JsonMappingException, JsonProcessingException{
        String key = "0808f1653374e9b866e1b1a712fac9a7";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&appid="+key;
        
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.getForEntity(url, String.class);
        String responseBody = response.getBody();
        
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(responseBody, Object.class);
        return jsonObject;
    }
}
