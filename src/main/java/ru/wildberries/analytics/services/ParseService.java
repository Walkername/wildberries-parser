package ru.wildberries.analytics.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.wildberries.analytics.models.CatalogDTO;
import ru.wildberries.analytics.models.ProductDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ParseService {

    private static final String URL_WILDBERRIES_API = ""; // TODO

    private static final Set<String> URL_WB_PRICE_HISTORY_APIs = Set.of(
            "https://basket-01.wbbasket.ru/",
            "https://basket-02.wbbasket.ru/",
            "https://basket-03.wbbasket.ru/",
            "https://basket-04.wbbasket.ru/",
            "https://basket-05.wbbasket.ru/",
            "https://basket-06.wbbasket.ru/",
            "https://basket-07.wbbasket.ru/",
            "https://basket-08.wbbasket.ru/",
            "https://basket-09.wbbasket.ru/",
            "https://basket-10.wbbasket.ru/",
            "https://basket-11.wbbasket.ru/",
            "https://basket-12.wbbasket.ru/",
            "https://basket-13.wbbasket.ru/",
            "https://basket-14.wbbasket.ru/",
            "https://basket-15.wbbasket.ru/",
            "https://basket-16.wbbasket.ru/",
            "https://basket-17.wbbasket.ru/"
    );

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
                List<String> priceHistory = getPriceHistory(productDTO.getId());
                System.out.println(priceHistory);
            }

        } catch (JsonProcessingException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Using of bruteforce to get price history is a very slow process
     * 20-30 sec for 100 products
     * But right now it is the only way
     */
    private List<String> getPriceHistory(int id) {
        RestTemplate restTemplate = new RestTemplate();
        List<String> response = new ArrayList<>();

        String halfPartUrl = "vol" + id / (int) Math.pow(10, 5)
                + "/part" + id / (int) Math.pow(10, 3) + "/" + id + "/info/price-history.json";

        for (String url : URL_WB_PRICE_HISTORY_APIs) {
            url += halfPartUrl;

            try {
                response = restTemplate.getForObject(url, List.class);
            } catch (Exception e) {
                continue;
            }

            if (response != null) {
                break;
            }
        }

        return response;
    }
}
