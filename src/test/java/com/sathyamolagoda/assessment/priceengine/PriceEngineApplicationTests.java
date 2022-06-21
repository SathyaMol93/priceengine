package com.sathyamolagoda.assessment.priceengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = PriceEngineApplicationTests.class)
class PriceEngineApplicationTests {

	@Test
	void contextLoads() {
	}

}
