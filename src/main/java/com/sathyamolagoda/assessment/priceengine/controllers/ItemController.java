package com.sathyamolagoda.assessment.priceengine.controllers;

import com.sathyamolagoda.assessment.priceengine.dtos.ItemDTO;
import com.sathyamolagoda.assessment.priceengine.services.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Validated
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<String> saveItem(@RequestBody @NotNull ItemDTO itemDTO) {
        return itemService.addItem(itemDTO);
    }

    @GetMapping("/calculated")
    public ResponseEntity<Object> getCalculatedItems() {
        return itemService.getCalculatedItem();
    }

    @GetMapping
    public ResponseEntity<Object> getItems() {
        return this.itemService.getAllItems();
    }
}
