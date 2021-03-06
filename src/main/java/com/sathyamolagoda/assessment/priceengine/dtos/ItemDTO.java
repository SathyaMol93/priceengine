package com.sathyamolagoda.assessment.priceengine.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Data
public class ItemDTO {

    private Long id;

    @NotNull
    @NotEmpty
    private String itemName;

    @NotNull
    @Min(value = 1)
    private Integer itemsPerCarton;

    @NotNull
    private Double cartonPrice;

    private Boolean rareProduct;

    public ItemDTO(Long id, String itemName, Integer itemsPerCarton, Double cartonPrice, Boolean rareProduct) {
        this.id = id;
        this.itemName = itemName;
        this.itemsPerCarton = itemsPerCarton;
        this.cartonPrice = cartonPrice;
        this.rareProduct = rareProduct;
    }
}
