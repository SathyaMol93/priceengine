package com.sathyamolagoda.assessment.priceengine.dtos;

import com.sathyamolagoda.assessment.priceengine.models.Expression;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Data
public class RateDTO {

    private Long id;

    @NotNull
    @NotEmpty
    private String rateName;

    @NotNull
    private Double rateValue;

    public Expression expression;

    @NotNull
    private Long expressionId;

    public RateDTO(Long id, String rateName, Double rateValue, Expression expression, Long expressionId) {
        this.id = id;
        this.rateName = rateName;
        this.rateValue = rateValue;
        this.expression = expression;
        this.expressionId = expressionId;
    }
}
