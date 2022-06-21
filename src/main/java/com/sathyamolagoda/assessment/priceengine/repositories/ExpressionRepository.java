package com.sathyamolagoda.assessment.priceengine.repositories;

import com.sathyamolagoda.assessment.priceengine.models.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, Long> {
}
