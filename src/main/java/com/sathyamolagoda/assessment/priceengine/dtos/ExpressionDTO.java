package com.sathyamolagoda.assessment.priceengine.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Data
public class ExpressionDTO {

    private Long id;

    @NotNull
    @NotEmpty
    private String exp;

    @NotNull
    @NotEmpty
    private ArrayList<String> reference;

    public ExpressionDTO(Long id, String exp, ArrayList<String> reference) {
        this.id = id;
        this.exp = exp;
        this.reference = reference;
    }
}
