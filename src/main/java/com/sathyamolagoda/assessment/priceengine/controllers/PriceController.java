package com.sathyamolagoda.assessment.priceengine.controllers;

import com.sathyamolagoda.assessment.priceengine.dtos.PriceItemDTO;
import com.sathyamolagoda.assessment.priceengine.services.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@RestController
@Slf4j
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PostMapping("/item")
    public ResponseEntity<Object> getCalculatedPrice(@RequestBody @NotNull PriceItemDTO priceItemDTO){
        return priceService.calculatePrice(priceItemDTO);
    }

    @PostMapping("/items")
    public ResponseEntity<Object> getCalculatedPrices(@RequestBody @NotNull List<PriceItemDTO> priceItemDTOs){
        return priceService.calculatePrices(priceItemDTOs);
    }
}
