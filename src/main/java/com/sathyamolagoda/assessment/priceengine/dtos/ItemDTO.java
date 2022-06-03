package com.sathyamolagoda.assessment.priceengine.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;

@Getter
@Setter
public class ItemDTO {

    private String itemName;

    private Integer itemsPerCarton;

    private String qauntity;

    private Double actualUnitPrice;

    private Double cartonPrice;

}
