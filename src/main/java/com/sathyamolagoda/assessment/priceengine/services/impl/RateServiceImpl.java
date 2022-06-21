package com.sathyamolagoda.assessment.priceengine.services.impl;

import com.sathyamolagoda.assessment.priceengine.dtos.RateDTO;
import com.sathyamolagoda.assessment.priceengine.models.Rate;
import com.sathyamolagoda.assessment.priceengine.repositories.RateRepository;
import com.sathyamolagoda.assessment.priceengine.services.RateService;
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
public class RateServiceImpl implements RateService {


    private final RateRepository rateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RateServiceImpl(RateRepository rateRepository, ModelMapper modelMapper) {
        this.rateRepository = rateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<String> addRate(RateDTO rateDTO){
        ResponseEntity<String> response = null;
        Rate rate = modelMapper.map(rateDTO, Rate.class);
        try{
            this.rateRepository.save(rate);
            response = new ResponseEntity<String>("Successfully save!", HttpStatus.OK);
        } catch (PersistenceException e){
            response = new ResponseEntity<String>("Operation failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
