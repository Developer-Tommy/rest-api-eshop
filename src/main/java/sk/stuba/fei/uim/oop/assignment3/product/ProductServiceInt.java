package sk.stuba.fei.uim.oop.assignment3.product;

import java.util.List;

public interface ProductServiceInt {

    List<Product> getAll();
    Product createNew(ProductRequest request);
    Product getById(Long id);
    Product updateProduct(ProductEditRequest editRequest, Long id);
    void deleteProduct(Long id);
    Product addAmount(Long id, ProductAmountRequest amountRequest);
}
