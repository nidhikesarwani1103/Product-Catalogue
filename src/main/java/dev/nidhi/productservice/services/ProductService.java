package dev.nidhi.productservice.services;

import dev.nidhi.productservice.dtos.ProductDTO;
import dev.nidhi.productservice.exceptions.ProductNotFoundException;
import dev.nidhi.productservice.models.Category;
import dev.nidhi.productservice.models.Product;
import dev.nidhi.productservice.repositories.CategoryRepository;
import dev.nidhi.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.spi.CurrencyNameProvider;

@Service("ProductServiceDBImpl")
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(Product product) {
        Category categoryEntity = getCategoryEntity(product);
        product.setCategory(categoryEntity);
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        List<Product> products = productList
                .stream()
                .filter(product -> {
                    return product.getIsDeleted()==null
                    || product.getIsDeleted()==false;
                })
                .toList();
        return products;
    }

    public String deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return "Product with id " + id + " not found";
        }
        Product productEntity = product.get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
        return "Product with id " + id + " deleted successfully";
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty())
        {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        Product productEntity = optionalProduct.get();
        if(product.getTitle()!=null)
        {
            productEntity.setTitle(product.getTitle());
        }
        if(product.getDescription()!=null)
        {
            productEntity.setDescription(product.getDescription());
        }
        if(product.getPrice()!=null)
        {
            productEntity.setPrice(product.getPrice());
        }
        if(product.getImageUrl()!=null){
            productEntity.setImageUrl(product.getImageUrl());
        }
        if(product.getCategory()!=null){
            Category categoryEntity = getCategoryEntity(product);
            productEntity.setCategory(categoryEntity);
        }

        return productRepository.save(productEntity);
    }

    public Product getProductById(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return optionalProduct.get();
    }

    public Product replaceProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        product.setId(id);
        return createProduct(product);
    }

    public List<Product> getProductIDGreaterThan(Long id){
        return productRepository.productsGreaterThanID(id);
    }

    public List<Product> getProductWithCategoryNameEquals(String categoryName){
        return productRepository.productCategoryNameIsEqualTo(categoryName);
    }

    public List<Product> productAndCategoryJoinWhereCategoryName(String categoryName){
        return productRepository.productAndCategoryJoinWhereCategoryName(categoryName);
    }


    public Category getCategoryEntity(Product product){
        Optional<Category> category = categoryRepository.findByName(product.getCategory().getName());
        Category categoryEntity;
        if (category.isEmpty()) {
            categoryEntity = new Category();
            categoryEntity.setName(product.getCategory().getName());
            // this has to be done until we use cascadeType.Persist
            // but good practice is to write code manually and not use
            // cascade
            categoryEntity = categoryRepository.save(categoryEntity);
        }
        else {
            categoryEntity = category.get();
        }
        return categoryEntity;
    }
}
