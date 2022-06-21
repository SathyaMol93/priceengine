package com.sathyamolagoda.assessment.priceengine.services;

import com.sathyamolagoda.assessment.priceengine.dtos.ItemDTO;
import com.sathyamolagoda.assessment.priceengine.models.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

public interface ItemService {
    ResponseEntity<String> addItem(ItemDTO itemDTO);

    ResponseEntity<Object> getCalculatedItem();

    ResponseEntity<Object> getAllItems();
}
