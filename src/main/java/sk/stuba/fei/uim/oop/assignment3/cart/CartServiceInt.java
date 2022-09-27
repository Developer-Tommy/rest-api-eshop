package sk.stuba.fei.uim.oop.assignment3.cart;

public interface CartServiceInt {

    Cart getById(Long id);
    Cart createNew();
    void deleteCart(Long id);
    Cart addProductToCart(Long id, CartAddRequest request);
    String payForCart(Long id);
}
