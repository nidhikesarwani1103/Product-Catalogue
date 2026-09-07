package dev.nidhi.productservice.controllers;

import dev.nidhi.productservice.dtos.ProductDTO;
import dev.nidhi.productservice.models.Product;
import dev.nidhi.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO.toProduct());
        return ProductDTO.fromProduct(product);
    }

    @GetMapping("")
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = products
                .stream()
                .map(product -> ProductDTO.fromProduct(product))
                .toList();

        return productDTOS;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PatchMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable("id") Long id,
                                    @RequestBody ProductDTO productDTO) {
        Product product = productService.updateProduct(id, productDTO.toProduct());
        return ProductDTO.fromProduct(product);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable("id") Long id){
        Product product = productService.getProductById(id);
        return ProductDTO.fromProduct(product);
    }

}
