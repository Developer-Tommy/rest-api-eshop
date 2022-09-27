package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceInt service;

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        return this.service.getAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addNewProduct(@RequestBody ProductRequest request) {
        return new ProductResponse(this.service.createNew(request));
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Long id) {
        return new ProductResponse(this.service.getById(id));
    }

    @PutMapping("/{id}")
    public ProductResponse updateProductById(@PathVariable("id") Long id, @RequestBody ProductEditRequest editRequest){
        return new ProductResponse(this.service.updateProduct(editRequest, id));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") Long id) {
        this.service.deleteProduct(id);
    }

    @GetMapping("/{id}/amount")
    public ProductAmount amountOfProductById(@PathVariable("id") Long id){
        return new ProductAmount(this.service.getById(id));
    }

    @PostMapping("/{id}/amount")
    public ProductAmount addAmountOfProductById(@PathVariable("id") Long id, @RequestBody ProductAmountRequest amountRequest){
        return new ProductAmount(this.service.addAmount(id, amountRequest));
    }
}
