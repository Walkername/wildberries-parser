package ru.wildberries.analytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class ParserWB {

    public String sendRequestTo(String url) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> result = null;
//
//        try {
//            result = mapper.readValue(response, Map.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(result);

        return response;
    }

    // TODO: parse and convert catalog json to products
    // convertToProductList();

    // TODO: parse and convert json to product to load it into the DB
    // convertToProduct();
}