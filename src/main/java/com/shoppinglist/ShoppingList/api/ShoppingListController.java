package com.shoppinglist.ShoppingList.api;

import com.shoppinglist.ShoppingList.dto.CreateShoppingListRequest;
import com.shoppinglist.ShoppingList.dto.ShoppingListResponse;
import com.shoppinglist.ShoppingList.service.ShoppingListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ShoppingListController {

    private final ShoppingListService service;

    public ShoppingListController(ShoppingListService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListResponse create(@Valid @RequestBody CreateShoppingListRequest request){
        return service.create(request);
    }

    @GetMapping
    public List<ShoppingListResponse> listAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ShoppingListResponse getOne(@PathVariable Long id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.deleteList(id);
    }

}