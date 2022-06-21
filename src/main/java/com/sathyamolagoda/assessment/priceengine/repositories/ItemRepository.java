package com.sathyamolagoda.assessment.priceengine.repositories;

import com.sathyamolagoda.assessment.priceengine.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sathya Molagoda on 4/6/2022
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
