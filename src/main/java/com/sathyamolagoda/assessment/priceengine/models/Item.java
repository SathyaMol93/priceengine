package com.sathyamolagoda.assessment.priceengine.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "items")
public class Item extends CommonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
