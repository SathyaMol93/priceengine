package com.sathyamolagoda.assessment.priceengine.repositories;

import com.sathyamolagoda.assessment.priceengine.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
}
