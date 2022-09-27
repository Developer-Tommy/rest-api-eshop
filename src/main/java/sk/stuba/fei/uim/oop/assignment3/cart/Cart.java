package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.items.ItemInShoppingList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<ItemInShoppingList> shoppingList = new ArrayList<>();
    private boolean payed;
}
