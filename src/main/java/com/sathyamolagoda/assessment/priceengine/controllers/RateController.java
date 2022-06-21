package com.sathyamolagoda.assessment.priceengine.controllers;

import com.sathyamolagoda.assessment.priceengine.dtos.RateDTO;
import com.sathyamolagoda.assessment.priceengine.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Validated
@RestController
@RequestMapping("/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    @PostMapping
    public ResponseEntity<String> saveRate(@RequestBody
                                           @NotNull(message = "Request body should not be null")
                                                   RateDTO rateDTO) {
        return this.rateService.addRate(rateDTO);
    }
}
