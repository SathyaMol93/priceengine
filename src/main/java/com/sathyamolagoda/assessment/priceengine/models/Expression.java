package com.sathyamolagoda.assessment.priceengine.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Data
@Entity(name = "expressions")
public class Expression extends CommonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXP_G1")
    @SequenceGenerator(name = "EXP_G1", sequenceName = "exp_seq")
    @Column(name = "exp_id")
    private Long id;

    @NotNull
    @Column(name = "exp")
    private String exp;

    @Basic
    @Column(name = "reference")
    private ArrayList<String> reference;
}
