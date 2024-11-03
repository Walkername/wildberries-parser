package ru.wildberries.analytics.parser.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.wildberries.analytics.parser.util.UnknownPageException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class ProductsService {

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

    // You need change url in application.properties
    // if you will use docker containers
    @Value("${processor.service.url}")
    private static String URL_PROCESSOR_API;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void createProductIndex() {
        Index index = new Index().on("wbId", Sort.Direction.ASC).unique();
        mongoTemplate.indexOps("raw_products").ensureIndex(index);
    }

    public boolean existsByProductWbId(int wbId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("wbId").is(wbId));

        boolean result = mongoTemplate.exists(query, "raw_products");
        System.out.println("wbId = " + wbId + ", " + result);
        return result;
    }

    @Transactional
    public void parse(String jsonUrl) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, String> urlMap = mapper.readValue(jsonUrl, Map.class);
            String url = urlMap.get("url"); // &limit=100

            int page = 1;
            while (true) {
                try {
                    parsePage(url, page);
                } catch (UnknownPageException e) {
                    break;
                }

                page++;
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void parsePage(String url, int page) throws HttpClientErrorException, UnknownPageException {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();

        String newUrl = url + "&page=" + page;
        //System.out.println(newUrl);

        try {
            URI uri = new URI(newUrl);

            String response = restTemplate.getForObject(uri, String.class);
            JsonNode rootNode = mapper.readTree(response);

            if (rootNode.isEmpty()) {
                throw new UnknownPageException();
            }

            JsonNode jsonCatalog = rootNode.get("data").get("products");

            for (JsonNode node : jsonCatalog) {
                if (existsByProductWbId(node.get("id").asInt())) {
                    System.out.println("This product is already in database");
                    continue;
                }

                JsonNode jsonWithWbId = renameField(node, "id", "wbId");
                JsonNode priceHistory = getPriceHistory(jsonWithWbId.get("wbId").asInt());
                JsonNode finalNode = ((ObjectNode) jsonWithWbId).set("priceHistory", priceHistory);

                // First: send json to processor service
                response = restTemplate.postForObject(URL_PROCESSOR_API, finalNode, String.class);

                // Second: save json to DB
                // If json wasn't send to processor, then it will not be saved to DB
                Document doc = Document.parse(finalNode.toString());
                mongoTemplate.save(doc, "raw_products");
            }
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode renameField(JsonNode jsonNode, String oldName, String newName) {
        if (jsonNode.has(oldName) && jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            JsonNode value = objectNode.get(oldName);

            objectNode.remove(oldName);
            objectNode.set(newName, value);
        }

        return jsonNode;
    }

    /*
     * Using of bruteforce to get price history is a very slow process
     * 20-30 sec for 100 products
     * But right now it is the only way
     */
    private JsonNode getPriceHistory(int id) {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        String response;
        JsonNode rootArray = null;

        String halfPartUrl = "vol" + id / (int) Math.pow(10, 5)
                + "/part" + id / (int) Math.pow(10, 3) + "/" + id + "/info/price-history.json";

        for (String url : URL_WB_PRICE_HISTORY_APIs) {
            url += halfPartUrl;

            try {
                response = restTemplate.getForObject(url, String.class);

                if (response != null) {
                    rootArray = mapper.readTree(response);
                    break;
                }
            } catch (Exception ignored) {
            }

        }

        if (rootArray == null || rootArray.isEmpty()) {
            rootArray = mapper.valueToTree(new ArrayList<>());
        }
        return rootArray;
    }
}
