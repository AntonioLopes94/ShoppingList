package com.shoppinglist.ShoppingList.service;

import com.shoppinglist.ShoppingList.api.error.ResourceNotFoundException;
import com.shoppinglist.ShoppingList.domain.Item;
import com.shoppinglist.ShoppingList.domain.ShoppingList;
import com.shoppinglist.ShoppingList.dto.CreateItemRequest;
import com.shoppinglist.ShoppingList.dto.ItemResponse;
import com.shoppinglist.ShoppingList.dto.UpdateItemRequest;
import com.shoppinglist.ShoppingList.repository.ItemRepository;
import com.shoppinglist.ShoppingList.repository.ShoppingListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private final ShoppingListRepository shoppingListRepository;
    private final ItemRepository itemRepository;

    public ItemService(ShoppingListRepository shoppingListRepository, ItemRepository itemRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemResponse addItem(Long listId, CreateItemRequest request){
        ShoppingList list = shoppingListRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping list not found " + listId));

        Item item = new Item(list, request.name().trim(), request.quantity());
        Item saved = itemRepository.save(item);

        return toResponse(saved);
    }

    @Transactional
    public void deleteItem(Long listId, Long itemId) {
        Item item = itemRepository.findByIdAndShoppingListId(itemId, listId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for lsit. itemId=" + itemId + " listId=" + listId));
        itemRepository.delete(item);
    }

    @Transactional
    public ItemResponse updateItem(Long listId, Long itemId, UpdateItemRequest request){
        Item item = itemRepository.findByIdAndShoppingListId(itemId, listId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for lsit. itemId=" + itemId + " listId=" + listId));

        if (request.name() != null){
            item.renameTo(request.name().trim());
        }
        if (request.quantity() != null){
            item.changeQuantityTo(request.quantity());
        }
        if (request.bought() != null){
            item.setBought(request.bought());
        }

        return toResponse(item);
    }

    @Transactional
    public List<ItemResponse> listItems(Long listId) {
        shoppingListRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping list not found: " + listId));

        return itemRepository.findByShoppingListIdOrderByCreatedAtAsc(listId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

//    @Transactional
//    public void deleteList(Long itemId){
//        if(!itemRepository.existsById(itemId)){
//            throw new ResourceNotFoundException("Shopping list not found: " + itemId);
//        }
//        itemRepository.deleteById(itemId);
//    }

    private ItemResponse toResponse(Item item){
        return new ItemResponse(
                item.getId(),
                item.getShoppingList().getId(),
                item.getName(),
                item.getQuantity(),
                item.isBought(),
                item.getCreatedAt()
        );
    }

    @Transactional
    public ItemResponse toggleBought(Long listId, Long itemId){
        Item item = itemRepository.findByIdAndShoppingListId(itemId, listId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for lsit. itemId=" + itemId + " listId=" + listId));

        item.setBought(!item.isBought());
        return toResponse(item);
    }
}
