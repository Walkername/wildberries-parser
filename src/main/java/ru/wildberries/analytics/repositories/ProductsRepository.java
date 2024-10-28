package ru.wildberries.analytics.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.wildberries.analytics.models.Product;

import java.util.Optional;

@Repository
public interface ProductsRepository extends MongoRepository<Product, Integer> {

    Optional<Product> findByWbId(int wbId);
}
