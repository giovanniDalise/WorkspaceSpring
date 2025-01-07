package com.giovanniDalise.product_service.service;

import com.giovanniDalise.product_service.dto.ProductRequest;
import com.giovanniDalise.product_service.dto.ProductResponse;
import com.giovanniDalise.product_service.model.Product;
import com.giovanniDalise.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/*
    //Con questa annotation sto creando un costruttore con l'unica variabile definita nella classe:
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
 */
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        // qui utilizzo @Slf4j con il placeholder {} che ha una serie di vantaggi al posto di una stringa
        // concatenata per il logging
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
