package com.sathyamolagoda.assessment.priceengine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sathyamolagoda.assessment.priceengine.dtos.RateDTO;
import com.sathyamolagoda.assessment.priceengine.repositories.RateRepository;
import com.sathyamolagoda.assessment.priceengine.services.RateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.PersistenceException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RateControllerTest {
    @Autowired
    private RateService rateService;

    @MockBean
    private RateRepository rateRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectWriter objectWriter;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        this.objectWriter = objectMapper.writer();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveRate() throws Exception {
        String content = this.objectWriter.writeValueAsString(new RateDTO(1l, "Discount rate", 0.30,null, 1l));

        when(rateRepository.save(any())).thenReturn(any());

        this.mockMvc.perform(
                        post("/rates")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void saveRateWithException() throws Exception {
        String content = this.objectWriter.writeValueAsString(new RateDTO(1l, "Discount rate", 0.30,null, 1l));

        when(rateRepository.save(any())).thenThrow(PersistenceException.class);;

        this.mockMvc.perform(
                        post("/rates")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}