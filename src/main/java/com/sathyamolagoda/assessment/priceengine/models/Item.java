package com.sathyamolagoda.assessment.priceengine.models;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Data
@Entity(name = "items")
public class Item extends CommonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_G1")
    @SequenceGenerator(name = "ITEM_G1", sequenceName = "item_seq")
    @Column(name = "item_id")
    private Long id;

    @Basic
    @Column(name = "item_name")
    private String itemName;

    @Basic
    @Column(name = "items_per_carton")
    private Integer itemsPerCarton;

    @Basic
    @Column(name = "carton_price")
    private Double cartonPrice;

    @Basic
    @Column(name = "rare_product")
    private Boolean rareProduct;
}
