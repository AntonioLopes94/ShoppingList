package com.shoppinglist.ShoppingList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingListFlowIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void createList_addItem_listItems_deleteList() {
        //Create list
        ResponseEntity<Map> createdList = rest.postForEntity(
                "/api/lists",
                json(Map.of("name", "Groceries")),
                Map.class
        );
        assertThat(createdList.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Integer listId = (Integer) createdList.getBody().get("id");
        assertThat(listId).isNotNull();

        //Add item
        ResponseEntity<Map> createdItem = rest.postForEntity(
                "/api/lists/" + listId + "/items",
                json(Map.of("name", "Milk", "quantity", 2)),
                Map.class
        );
        assertThat(createdItem.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createdItem.getBody().get("name")).isEqualTo("Milk");

        //List item
        ResponseEntity<List> items = rest.getForEntity(
                "/api/lists/" + listId + "/items",
                List.class
        );
        assertThat(items.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(items.getBody()).hasSize(1);

        //Delete list
        ResponseEntity<Void> deleteResp = rest.exchange(
                "/api/lists/" + listId,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        );
        assertThat(deleteResp.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        //Verify list is gone (GET /api/lists/{id})
        ResponseEntity<Map> getDeleted = rest.getForEntity(
                "/api/lists/" + listId,
                Map.class
        );
        assertThat(getDeleted.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private HttpEntity<Map<String, Object>> json(Map<String,Object> body){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }


}


