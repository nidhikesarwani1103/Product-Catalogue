package dev.nidhi.productservice.repositories;

import dev.nidhi.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // works for save and update both
    // if the product has an id, it will
    // update the existing product, otherwise it will create a new product

    @Override
    Product save(Product product);

    @Override
    void delete(Product product);


}
