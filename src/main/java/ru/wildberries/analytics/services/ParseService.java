package ru.wildberries.analytics.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.wildberries.analytics.models.CatalogDTO;
import ru.wildberries.analytics.models.ProductDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Service
public class ParseService {

    private static final String URL_WILDBERRIES_API = ""; // TODO
    private static final String URL_PREPROCESSOR_API = ""; // TODO

    public void parse(String jsonUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, String> urlMap = mapper.readValue(jsonUrl, Map.class);
            String url = urlMap.get("url");
            URI uri = new URI(url);

            String response = restTemplate.getForObject(uri, String.class);

            CatalogDTO catalog = mapper.readValue(response, CatalogDTO.class);

            for (ProductDTO productDTO : catalog.getData().getProducts()) {
                System.out.println(productDTO);
            }

        } catch (JsonProcessingException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
