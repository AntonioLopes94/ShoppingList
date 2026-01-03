package com.shoppinglist.ShoppingList.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateItemRequest(
        @Size(min = 1, max = 120)
        String name,

        @Min(1)
        Integer quantity,

        Boolean bought
) {
}
