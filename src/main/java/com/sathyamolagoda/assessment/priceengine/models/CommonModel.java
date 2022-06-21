package com.sathyamolagoda.assessment.priceengine.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonModel {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    @LastModifiedDate
    private Date updatedDate;

    @Basic
    @Column(name = "status")
    private Integer status;
}
