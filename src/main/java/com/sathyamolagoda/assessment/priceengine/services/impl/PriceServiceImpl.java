package com.sathyamolagoda.assessment.priceengine.services.impl;

import com.sathyamolagoda.assessment.priceengine.dtos.PriceDTO;
import com.sathyamolagoda.assessment.priceengine.dtos.PriceItemDTO;
import com.sathyamolagoda.assessment.priceengine.models.Item;
import com.sathyamolagoda.assessment.priceengine.models.Rate;
import com.sathyamolagoda.assessment.priceengine.repositories.ItemRepository;
import com.sathyamolagoda.assessment.priceengine.repositories.RateRepository;
import com.sathyamolagoda.assessment.priceengine.services.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Service
public class PriceServiceImpl implements PriceService {

    private final ItemRepository itemRepository;
    private final RateRepository rateRepository;

    private static final Long SINGLE_UNIT_RATE_ID = 2L;
    private static final Long DISCOUNT_RATE_ID = 1L;

    public PriceServiceImpl(ItemRepository itemRepository, RateRepository rateRepository) {
        this.itemRepository = itemRepository;
        this.rateRepository = rateRepository;
    }

    @Override
    public ResponseEntity<Object> calculatePrice(PriceItemDTO priceItemDTO) {
        return new ResponseEntity<>(getPriceDTO(priceItemDTO.getItemId(),priceItemDTO.getQuantity()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> calculatePrices(List<PriceItemDTO> priceItemDTOs) {
        List<PriceDTO> priceDTOS = priceItemDTOs.stream().map(priceItemDTO -> getPriceDTO(priceItemDTO.getItemId(),priceItemDTO.getQuantity())).collect(Collectors.toList());
        return new ResponseEntity<>(priceDTOS, HttpStatus.OK);
    }

    private PriceDTO getPriceDTO(Long itemId, Integer quantity) {
        Double total = 0.00;
        int cartoons = 0;
        int remainder = 0;
        String itemName = "";

        Optional<Item> item = this.itemRepository.findById(itemId);
        if (item.isPresent()) {
            total = 0.00;
            cartoons = quantity / item.get().getItemsPerCarton();
            remainder = quantity % item.get().getItemsPerCarton();
            Double cartonPrice = item.get().getCartonPrice();

            if (cartoons >= 3) {
                Optional<Rate> rateDiscount = this.rateRepository.findById(DISCOUNT_RATE_ID);
                double price = item.get().getCartonPrice() * (1 - rateDiscount.get().getRateValue());
                BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
                cartonPrice = Double.valueOf(bd.doubleValue());
            }

            if (remainder > 0) {
                Optional<Rate> unitAddition = this.rateRepository.findById(SINGLE_UNIT_RATE_ID);
                double price = (item.get().getCartonPrice() / item.get().getItemsPerCarton()) * (1 + unitAddition.get().getRateValue());
                double totalPrice = price * remainder;
                BigDecimal bd = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
                total += Double.valueOf(bd.doubleValue());
            }

            total += cartonPrice * cartoons;

            itemName = item.get().getItemName();

        }
        return new PriceDTO(total, cartoons, remainder,itemName);
    }
}
