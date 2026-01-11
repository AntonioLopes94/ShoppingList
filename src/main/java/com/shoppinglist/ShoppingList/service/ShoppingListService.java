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
    private final ShoppingListRepository shoppingRepository;

    public ShoppingListService(ShoppingListRepository shoppingRepository) {
        this.shoppingRepository = shoppingRepository;
    }

    @Transactional
    public ShoppingListResponse create(CreateShoppingListRequest request){
        if (shoppingRepository.existsByNameIgnoreCase(request.name())){
            throw new BusinessRuleException("List name already exists");
        }
        ShoppingList shoppingList = new ShoppingList(request.name());
        ShoppingList saved = shoppingRepository.save(shoppingList);

        return toResponse(saved);
    }

    @Transactional
    public List<ShoppingListResponse> findAll(){
        return shoppingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

    }

    @Transactional(readOnly = true)
    public ShoppingListResponse findById(Long shoppingListId){
        ShoppingList shoppingList = shoppingRepository.findById(shoppingListId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping list not found " + shoppingListId));

        return toResponse(shoppingList);
    }

    @Transactional
    public void deleteList(Long shoppingListId){
        if(!shoppingRepository.existsById(shoppingListId)){
            throw new ResourceNotFoundException("Shopping list not found: " + shoppingListId);
        }
        shoppingRepository.deleteById(shoppingListId);
    }

    private ShoppingListResponse toResponse(ShoppingList shoppingList) {
        return new ShoppingListResponse(shoppingList.getId(), shoppingList.getName(), shoppingList.getCreatedAt());
    }



}
