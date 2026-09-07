package dev.nidhi.productservice.repositories;

import dev.nidhi.productservice.models.Category;
import dev.nidhi.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // works for save and update both
    // if the product has an id, it will
    // update the existing product, otherwise it will create a new product

    @Override
    Product save(Product product);

    @Override
    void delete(Product product);

    @Override
    Optional<Product> findById(Long id);

    // Query by JPA method name
    List<Product> findAllByCategory_NameEquals(String categoryName);

    // JPA query by using simple java syntax and class name
    @Query("select p from Product p where p.id > :idGreaterThan")
    List<Product> productsGreaterThanID(@Param("idGreaterThan") Long id);

    @Query("select p from Product p where p.category.name = :categoryName")
    List<Product> productCategoryNameIsEqualTo(@Param("categoryName") String category);

    @Query(
            value = "SELECT * from product p " +
                    "JOIN category c ON p.category_id = c.id " +
                    "WHERE c.name = :categoryName",
            nativeQuery = true
    )
    List<Product> productAndCategoryJoinWhereCategoryName(@Param("categoryName") String categoryName);

    @Query(
            value = CustomQueries.PRODUCT_CATEGORY_RIGHT_JOIN,
            nativeQuery = true
    )
    List<Product> productAndCategoryJoin();
}
