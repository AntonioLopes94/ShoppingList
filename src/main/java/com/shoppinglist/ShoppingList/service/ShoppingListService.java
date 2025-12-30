package com.shoppinglist.ShoppingList.service;

import com.shoppinglist.ShoppingList.domain.ShoppingList;
import com.shoppinglist.ShoppingList.dto.CreateShoppingListRequest;
import com.shoppinglist.ShoppingList.dto.ShoppingListResponse;
import com.shoppinglist.ShoppingList.repository.ShoppingListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shoppinglist.ShoppingList.api.error.BusinessRuleException;
import com.shoppinglist.ShoppingList.api.error.ResourceNotFoundException;

import java.util.List;

@Service
public class ShoppingListService {
    private final ShoppingListRepository repository;

    public ShoppingListService(ShoppingListRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ShoppingListResponse create(CreateShoppingListRequest request){
        if (repository.existsByNameIgnoreCase(request.name())){
            throw new BusinessRuleException("List name already exists");
        }
        ShoppingList list = new ShoppingList(request.name());
        ShoppingList saved = repository.save(list);

        return toResponse(saved);
    }

    @Transactional
    public List<ShoppingListResponse> findAll(){
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

    }

    @Transactional(readOnly = true)
    public ShoppingListResponse findById(Long id){
        ShoppingList list = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping list not found " + id));

        return toResponse(list);
    }

    private ShoppingListResponse toResponse(ShoppingList list) {
        return new ShoppingListResponse(list.getId(), list.getName(), list.getCreatedAt());
    }



}
