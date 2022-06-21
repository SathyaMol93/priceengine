package com.sathyamolagoda.assessment.priceengine.services;

import com.sathyamolagoda.assessment.priceengine.dtos.RateDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

public interface RateService {
    ResponseEntity<String> addRate(RateDTO rateDTO);
}
