package com.shoppinglist.ShoppingList.repository;

import com.shoppinglist.ShoppingList.domain.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingList,Long> {
}
