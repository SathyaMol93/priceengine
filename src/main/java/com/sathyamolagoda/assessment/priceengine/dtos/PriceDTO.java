package com.sathyamolagoda.assessment.priceengine.dtos;

import lombok.Data;

@Data
public class PriceDTO {

    private Double totalPrice;

    private Integer totalCartons;

    private Integer totalItems;

    private String itemName;

    public PriceDTO(Double totalPrice, Integer totalCartons, Integer totalItems, String itemName) {
        this.totalPrice = totalPrice;
        this.totalCartons = totalCartons;
        this.totalItems = totalItems;
        this.itemName = itemName;
    }
}
