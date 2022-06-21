package com.sathyamolagoda.assessment.priceengine;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Sathya Molagoda on 3/6/2022
 */

@SpringBootApplication
@EnableJpaAuditing
public class PriceEngineApplication {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(PriceEngineApplication.class, args);
	}

}
