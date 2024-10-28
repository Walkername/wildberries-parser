package ru.wildberries.analytics.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.wildberries.analytics.dto.CatalogDTO;
import ru.wildberries.analytics.dto.ProductDTO;
import ru.wildberries.analytics.dto.ProductSizeDTO;
import ru.wildberries.analytics.models.PriceState;
import ru.wildberries.analytics.models.Product;
import ru.wildberries.analytics.models.ProductSize;
import ru.wildberries.analytics.repositories.ProductsRepository;
import ru.wildberries.analytics.util.UnknowPageException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class ProductsService {

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

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Optional<Product> findByWbId(int id) {
        Optional<Product> product = productsRepository.findByWbId(id);
        return product;
    }

    @Transactional
    public void parse(String jsonUrl) {
        ObjectMapper mapper = new ObjectMapper();

        BindingResult bindingResult;

        try {
            Map<String, String> urlMap = mapper.readValue(jsonUrl, Map.class);
            String url = urlMap.get("url"); // &limit=100


            int page = 1;
            while (true) {
                try {
                    parsePage(url, page);
                } catch (UnknowPageException e) {
                    break;
                }

                page++;
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void parsePage(String url, int page) throws HttpClientErrorException, UnknowPageException {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();

        String newUrl = url + "&page=" + page;
        //System.out.println(newUrl);
        System.out.println(url);

        try {
            URI uri = new URI(newUrl);

            String response = restTemplate.getForObject(uri, String.class);

            CatalogDTO catalog = mapper.readValue(response, CatalogDTO.class);
            System.out.println(catalog.getData().getProducts().size());
            if (catalog.getData().getProducts().isEmpty()) {
                throw new UnknowPageException();
            }

            for (ProductDTO productDTO : catalog.getData().getProducts()) {
                if (findByWbId(productDTO.getId()).isPresent()) {
                    System.out.println("This product is already in database");
                    continue;
                }

                List<PriceState> priceHistory = getPriceHistory(productDTO.getId());

                Product product = convertToProduct(productDTO);
                product.setWbId(productDTO.getId());
                List<ProductSize> sizes = new ArrayList<>();
                int lastPrice = 0;
                for (ProductSizeDTO productSizeDTO : productDTO.getSizes()) {
                    ProductSize productSize = convertToProductSize(productSizeDTO);
                    productSize.setBasicPrice(productSizeDTO.getPrice().getBasic());
                    productSize.setDiscountPrice(productSizeDTO.getPrice().getProduct());
                    sizes.add(productSize);

                    lastPrice = productSizeDTO.getPrice().getProduct();
                }
                product.setSizes(sizes);


                PriceState currentPriceState = new PriceState();
                currentPriceState.setTime(new Date().toString());
                currentPriceState.setPrice(lastPrice);
                priceHistory.add(currentPriceState);
                product.setPriceHistory(priceHistory);

                // Save product in MongoDB
                productsRepository.save(product);
            }
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    /*
     * Using of bruteforce to get price history is a very slow process
     * 20-30 sec for 100 products
     * But right now it is the only way
     */
    private List<PriceState> getPriceHistory(int id) {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        List<PriceState> result = new ArrayList<>();
        String response;
        JsonNode rootArray;

        String halfPartUrl = "vol" + id / (int) Math.pow(10, 5)
                + "/part" + id / (int) Math.pow(10, 3) + "/" + id + "/info/price-history.json";

        for (String url : URL_WB_PRICE_HISTORY_APIs) {
            url += halfPartUrl;

            try {
                response = restTemplate.getForObject(url, String.class);
                rootArray = mapper.readTree(response);
            } catch (Exception e) {
                continue;
            }

            if (response != null) {
                for (JsonNode node : rootArray) {
                    PriceState priceState = new PriceState();
                    priceState.setTime(node.get("dt").toString());
                    priceState.setPrice(node.get("price").get("RUB").asInt());
                    result.add(priceState);
                }
                break;
            }
        }

        return result;
    }

    private Product convertToProduct(ProductDTO productDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(productDTO, Product.class);
    }

    private ProductSize convertToProductSize(ProductSizeDTO productSizeDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(productSizeDTO, ProductSize.class);
    }
}
