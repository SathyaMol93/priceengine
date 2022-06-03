package com.sathyamolagoda.assessment.priceengine.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "rates")
public class Rate extends CommonModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rate_id")
    private Long id;

    @Basic
    @Column(name = "rate_name")
    private String rateName;

    @Basic
    @Column(name = "rate")
    private Double rate;

    @Basic
    @Column(name = "rate_logic")
    private String rateLogic;
}
