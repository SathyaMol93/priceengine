package com.sathyamolagoda.assessment.priceengine.services.impl;

import com.sathyamolagoda.assessment.priceengine.dtos.CalculatedItemDTO;
import com.sathyamolagoda.assessment.priceengine.dtos.ItemDTO;
import com.sathyamolagoda.assessment.priceengine.models.Item;
import com.sathyamolagoda.assessment.priceengine.models.Rate;
import com.sathyamolagoda.assessment.priceengine.repositories.ItemRepository;
import com.sathyamolagoda.assessment.priceengine.repositories.RateRepository;
import com.sathyamolagoda.assessment.priceengine.services.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final RateRepository rateRepository;
    private final ModelMapper modelMapper;

    private static final Long SINGLE_UNIT_RATE_ID = 2L;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, RateRepository rateRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.rateRepository = rateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<String> addItem(ItemDTO itemDTO) {
        ResponseEntity<String> response = null;
        Item item = modelMapper.map(itemDTO, Item.class);
        try {
            this.itemRepository.save(item);
            response = new ResponseEntity<String>("Successfully save!", HttpStatus.OK);
        } catch (PersistenceException e) {
            response = new ResponseEntity<String>("Operation failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<Object> getCalculatedItem() {
        List<Item> itmes = this.itemRepository.findAll();
        List<CalculatedItemDTO> calculatedItems = itmes.stream().map(item -> new CalculatedItemDTO(item.getId(), item.getItemName(), item.getItemsPerCarton(), item.getCartonPrice(), item.getRareProduct(), this.calculateActualPrice(item.getCartonPrice(), item.getItemsPerCarton()), "1-50")).collect(Collectors.toList());
        return new ResponseEntity<Object>(calculatedItems, HttpStatus.OK);
    }

    private Double calculateActualPrice(Double cartonPrice, Integer itemsPerCarton) {
        Optional<Rate> rate = this.rateRepository.findById(SINGLE_UNIT_RATE_ID);
        Double actualPrice = 0.00;
        if (rate.isPresent()) {
            double price = (cartonPrice / itemsPerCarton) * (1 + rate.get().getRateValue());
            BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
            actualPrice = Double.valueOf(bd.doubleValue());
        }
        return actualPrice;
    }

    @Override
    public ResponseEntity<Object> getAllItems(){
        return new ResponseEntity<Object>(this.itemRepository.findAll(),HttpStatus.OK);
    }
}
