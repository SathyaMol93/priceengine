package com.sathyamolagoda.assessment.priceengine.repositories;

import com.sathyamolagoda.assessment.priceengine.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
}
