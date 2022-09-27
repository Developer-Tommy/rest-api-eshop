package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService implements ProductServiceInt{

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Product createNew(ProductRequest request) {
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setDescription(request.getDescription());
        newProduct.setAmount(request.getAmount());
        newProduct.setUnit(request.getUnit());
        newProduct.setPrice(request.getPrice());
        return this.repository.save(newProduct);
    }

    @Override
    public Product getById(Long id) {
        if (this.repository.findById(id).isPresent())
            return this.repository.findById(id).get();

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public Product updateProduct(ProductEditRequest editRequest, Long id) {
        if (this.repository.findById(id).isPresent()){
            Product updatedProduct = this.repository.findById(id).get();
            if (editRequest.getName() != null){
                updatedProduct.setName(editRequest.getName());
            }
            if (editRequest.getDescription() != null){
                updatedProduct.setDescription(editRequest.getDescription());
            }
            return this.repository.save(updatedProduct);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteProduct(Long id) {
        if (this.repository.findById(id).isPresent()){
            Product deletedProduct = this.repository.findById(id).get();
            this.repository.delete(deletedProduct);
        }

        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public Product addAmount(Long id, ProductAmountRequest amountRequest) {
        if (this.repository.findById(id).isPresent()){
            Product addAmountToProduct = this.repository.findById(id).get();
            int storage = addAmountToProduct.getAmount();
            addAmountToProduct.setAmount(storage + amountRequest.getAmount());
            return this.repository.save(addAmountToProduct);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
