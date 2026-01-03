package com.shoppinglist.ShoppingList.api;

import com.shoppinglist.ShoppingList.dto.CreateItemRequest;
import com.shoppinglist.ShoppingList.dto.ItemResponse;
import com.shoppinglist.ShoppingList.dto.UpdateItemRequest;
import com.shoppinglist.ShoppingList.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lists/{listId}/items")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse addItem(@PathVariable Long listId, @Valid @RequestBody CreateItemRequest request){
        return service.addItem(listId, request);
    }

    @PatchMapping("/{itemId}")
    public ItemResponse updateItem(@PathVariable Long listId, @PathVariable Long itemId, @Valid @RequestBody UpdateItemRequest request){
        return service.updateItem(listId, itemId, request);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long listId, @PathVariable Long itemId){
        service.deleteItem(listId, itemId);
    }

    @PatchMapping("/{itemId}/toggle-bought")
    public ItemResponse toggleBought(@PathVariable Long listId, @PathVariable Long itemId){
        return service.toggleBought(listId, itemId);
    }
}
