package com.shoppinglist.ShoppingList.repository;

import com.shoppinglist.ShoppingList.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findByShoppingListIdOrderByCreatedAtAsc(Long listId);
    Optional<Item> findByIdAndShoppingListId(Long id, Long shoppingListId);
}
