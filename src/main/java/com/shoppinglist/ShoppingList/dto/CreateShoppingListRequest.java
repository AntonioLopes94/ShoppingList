package com.shoppinglist.ShoppingList.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateShoppingListRequest(
        @NotBlank
        @Size(min = 2, max = 120)
        String name
) {
}
