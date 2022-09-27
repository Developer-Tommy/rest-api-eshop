package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceInt service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addNewCart() {
        return new CartResponse(this.service.createNew());
    }

    @GetMapping("/{id}")
    public CartResponse getCartById(@PathVariable("id") Long id) {
        return new CartResponse(this.service.getById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteCartById(@PathVariable("id") Long id) {
        this.service.deleteCart(id);
    }

    @PostMapping("/{id}/add")
    public CartResponse addProductToCart(@PathVariable("id") Long id, @RequestBody CartAddRequest request) {
        return new CartResponse(this.service.addProductToCart(id, request));
    }

    @GetMapping("/{id}/pay")
    public String payProductsInCart(@PathVariable("id") Long id){
        return this.service.payForCart(id);
    }

}
