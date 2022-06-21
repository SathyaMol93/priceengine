package com.sathyamolagoda.assessment.priceengine.services.impl;

import com.sathyamolagoda.assessment.priceengine.dtos.ExpressionDTO;
import com.sathyamolagoda.assessment.priceengine.models.Expression;
import com.sathyamolagoda.assessment.priceengine.repositories.ExpressionRepository;
import com.sathyamolagoda.assessment.priceengine.services.ExpressionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Service
public class ExpressionServiceImpl implements ExpressionService {


    private final ExpressionRepository expressionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ExpressionServiceImpl(ExpressionRepository expressionRepository, ModelMapper modelMapper) {
        this.expressionRepository = expressionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<String> addExpression(ExpressionDTO expressionDTO){
        ResponseEntity<String> response = null;
        Expression expression = modelMapper.map(expressionDTO, Expression.class);
        try{
            this.expressionRepository.save(expression);
            response = new ResponseEntity<String>("Successfully save!", HttpStatus.OK);
        } catch (PersistenceException e){
            response = new ResponseEntity<String>("Operation failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
