package com.sathyamolagoda.assessment.priceengine.controllers;

import com.sathyamolagoda.assessment.priceengine.dtos.ExpressionDTO;
import com.sathyamolagoda.assessment.priceengine.services.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Validated
@RestController
@RequestMapping("/expressions")
public class ExpressionController {
    @Autowired
    private ExpressionService expressionService;

    @PostMapping
    public ResponseEntity<String> saveExpression(@RequestBody
                                                 @NotNull(message = "Request body should not be null")
                                                         ExpressionDTO expressionDTO) {
        return this.expressionService.addExpression(expressionDTO);
    }
}
