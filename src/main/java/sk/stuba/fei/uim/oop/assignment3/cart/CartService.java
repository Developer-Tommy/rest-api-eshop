package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sk.stuba.fei.uim.oop.assignment3.items.ItemInShoppingList;
import sk.stuba.fei.uim.oop.assignment3.items.ItemRepository;
import sk.stuba.fei.uim.oop.assignment3.product.Product;
import sk.stuba.fei.uim.oop.assignment3.product.ProductRepository;

@Service
public class CartService implements CartServiceInt{


    @Autowired
    private ItemRepository itemRepository;
    private final CartRepository repository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cart getById(Long id) {
        if (this.repository.findById(id).isPresent())
            return this.repository.findById(id).get();

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public Cart createNew() {
        Cart cart = new Cart();
        return this.repository.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        if (this.repository.findById(id).isPresent()){
            Cart deletedCart = this.repository.findById(id).get();
            this.repository.delete(deletedCart);
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public Cart addProductToCart(Long id, CartAddRequest request) {
        if (this.productRepository.findById(request.getProductId()).isPresent() && this.repository.findById(id).isPresent()){
            Cart cart = this.repository.findById(id).get();
            Product product = this.productRepository.findById(request.getProductId()).get();

            if (cart.isPayed() || (request.getAmount() > product.getAmount())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            ItemInShoppingList item = new ItemInShoppingList();
            item.setProductId(request.getProductId());

            for (ItemInShoppingList shoppingList : cart.getShoppingList()){
                if (shoppingList.getProductId().equals(request.getProductId())){
                    shoppingList.setAmount(shoppingList.getAmount() + request.getAmount());
                    product.setAmount(product.getAmount() - request.getAmount());
                    this.itemRepository.save(shoppingList);
                    this.productRepository.save(product);
                    return this.repository.save(cart);
                }
            }

            product.setAmount(product.getAmount() - request.getAmount());
            item.setAmount(request.getAmount());
            this.itemRepository.save(item);
            cart.getShoppingList().add(item);
            this.productRepository.save(product);
            return this.repository.save(cart);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public String payForCart(Long id) {
        int sum = 0;
        if (this.repository.findById(id).isPresent()){
            Cart cart = this.repository.findById(id).get();
            if (cart.isPayed()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            for (ItemInShoppingList shoppingList : cart.getShoppingList()){
                sum += shoppingList.getAmount() * this.productRepository.findById(shoppingList.getProductId()).get().getPrice();
            }
            cart.setPayed(true);
            this.repository.save(cart);
            return String.format("%d", sum);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
