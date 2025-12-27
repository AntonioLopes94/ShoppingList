package com.shoppinglist.ShoppingList.repository;

import com.shoppinglist.ShoppingList.domain.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList,Long> {

    boolean existsByNameIgnoreCase(String name);
}
