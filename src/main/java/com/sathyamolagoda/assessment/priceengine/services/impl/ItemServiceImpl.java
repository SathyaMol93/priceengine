package com.sathyamolagoda.assessment.priceengine.services.impl;

import com.sathyamolagoda.assessment.priceengine.repositories.ItemRepository;
import com.sathyamolagoda.assessment.priceengine.repositories.RateRepository;
import com.sathyamolagoda.assessment.priceengine.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final RateRepository rateRepository;

    private static final String SINGLE_UNIT_RATE = " single_unit_rate";

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, RateRepository rateRepository) {
        this.itemRepository = itemRepository;
        this.rateRepository = rateRepository;
    }
}
