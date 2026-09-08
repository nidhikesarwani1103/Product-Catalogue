package dev.nidhi.productservice.controllers;

import dev.nidhi.productservice.dtos.ProductDTO;
import dev.nidhi.productservice.models.Product;
import dev.nidhi.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO.toProduct());
        ProductDTO responseProductDTO = ProductDTO.fromProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(responseProductDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = products
                .stream()
                .map(product -> ProductDTO.fromProduct(product))
                .toList();

        return ResponseEntity.ok(productDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id,
                                    @RequestBody ProductDTO productDTO) {
        Product product = productService.updateProduct(id, productDTO.toProduct());
        return ResponseEntity.ok(ProductDTO.fromProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductDTO.fromProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> replaceProduct(@RequestBody ProductDTO productDTO, @PathVariable("id") Long id){
        Product product = productService.replaceProduct(id, productDTO.toProduct());
        return ResponseEntity.ok(ProductDTO.fromProduct(product));
    }

    @GetMapping("/greater-than/{id}")
    public ResponseEntity<List<ProductDTO>> getProductIDGreaterThan(@PathVariable("id") Long id){
        List<ProductDTO> responseList = productService
                                            .getProductIDGreaterThan(id)
                                            .stream()
                                            .map(product -> ProductDTO.fromProduct(product))
                                            .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/category-equals-to/{categoryName}")
    public ResponseEntity<List<ProductDTO>> productCategoryNameIsEqualTo(@PathVariable("categoryName") String categoryName){
        List<ProductDTO> responseList = productService
                                            .getProductWithCategoryNameEquals(categoryName)
                                            .stream()
                                            .map(product -> ProductDTO.fromProduct(product))
                                            .toList();
        return ResponseEntity.ok(responseList);
    }
}
