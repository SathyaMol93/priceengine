package com.sathyamolagoda.assessment.priceengine.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class CommonModel {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    @Basic
    @Column(name = "status")
    private Integer status;
}
