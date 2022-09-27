package sk.stuba.fei.uim.oop.assignment3.items;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<ItemInShoppingList, Long> {
}
