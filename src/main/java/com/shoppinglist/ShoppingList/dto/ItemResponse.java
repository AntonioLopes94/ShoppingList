package com.shoppinglist.ShoppingList.dto;

import java.time.Instant;

public record ItemResponse(
        Long id,
        Long listId,
        String name,
        int quantity,
        boolean bought,
        Instant createdAt
) {
}
