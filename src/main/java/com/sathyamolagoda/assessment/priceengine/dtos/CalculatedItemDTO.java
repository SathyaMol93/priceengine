package com.sathyamolagoda.assessment.priceengine.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Data
public class CalculatedItemDTO {

    private Long id;

    private String itemName;

    private Integer itemsPerCarton;

    private Double cartonPrice;

    private Boolean rareProduct;

    private Double actualPrice;

    private String units;

    public CalculatedItemDTO(Long id, String itemName, Integer itemsPerCarton, Double cartonPrice, Boolean rareProduct, Double actualPrice, String units) {
        this.id = id;
        this.itemName = itemName;
        this.itemsPerCarton = itemsPerCarton;
        this.cartonPrice = cartonPrice;
        this.rareProduct = rareProduct;
        this.actualPrice = actualPrice;
        this.units = units;
    }
}
