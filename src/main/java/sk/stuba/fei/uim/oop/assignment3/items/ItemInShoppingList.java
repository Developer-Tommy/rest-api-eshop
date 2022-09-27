package sk.stuba.fei.uim.oop.assignment3.items;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ItemInShoppingList {

    @Id
    private Long productId;
    private int amount;

}
