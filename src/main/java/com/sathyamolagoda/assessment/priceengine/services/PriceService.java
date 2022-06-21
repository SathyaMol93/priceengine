package com.sathyamolagoda.assessment.priceengine.services;

import com.sathyamolagoda.assessment.priceengine.dtos.PriceItemDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

public interface PriceService {
    ResponseEntity<Object> calculatePrice(PriceItemDTO priceItemDTO);

    ResponseEntity<Object> calculatePrices(List<PriceItemDTO> priceItemDTOs);
}
