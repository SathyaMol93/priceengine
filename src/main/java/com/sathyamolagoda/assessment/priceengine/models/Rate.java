package com.sathyamolagoda.assessment.priceengine.models;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Data
@Entity(name = "rates")
public class Rate extends CommonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RATE_G1")
    @SequenceGenerator(name = "RATE_G1", sequenceName = "rate_seq")
    @Column(name = "rate_id")
    private Long id;

    @Basic
    @Column(name = "rate_name")
    private String rateName;

    @Basic
    @Column(name = "rate_value")
    private Double rateValue;

    @Basic
    @Column(name = "exp_id")
    private Long expressionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exp_id", referencedColumnName = "exp_id", insertable = false, updatable = false)
    public Expression expression;
}
