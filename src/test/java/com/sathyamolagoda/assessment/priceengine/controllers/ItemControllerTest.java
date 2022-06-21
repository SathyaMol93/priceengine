package com.sathyamolagoda.assessment.priceengine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sathyamolagoda.assessment.priceengine.dtos.ItemDTO;
import com.sathyamolagoda.assessment.priceengine.dtos.RateDTO;
import com.sathyamolagoda.assessment.priceengine.models.Item;
import com.sathyamolagoda.assessment.priceengine.models.Rate;
import com.sathyamolagoda.assessment.priceengine.repositories.ItemRepository;
import com.sathyamolagoda.assessment.priceengine.repositories.RateRepository;
import com.sathyamolagoda.assessment.priceengine.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.PersistenceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private ItemServiceImpl itemService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private RateRepository rateRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    void saveItem() throws Exception {
        String content = this.objectWriter.writeValueAsString(new ItemDTO(1l,"testItem", 45,4.50,false));

        when(itemRepository.save(any())).thenReturn(any());

        this.mockMvc.perform(
                        post("/items")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void saveItemWithException() throws Exception {
        String content = this.objectWriter.writeValueAsString(new ItemDTO(1l,"testItem", 45,4.50,false));

        when(itemRepository.save(any())).thenThrow(PersistenceException.class);

        this.mockMvc.perform(
                        post("/items")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getCalculatedItems() throws Exception {

        Item item = modelMapper.map(new ItemDTO(1l,"testItem", 45,10.00,false), Item.class);
        Rate rate = modelMapper.map(new RateDTO(1l, "Discount rate", 0.30,null, 1l), Rate.class);
        when(itemRepository.findAll()).thenReturn( List.of(item));
        when(rateRepository.findById(anyLong())).thenReturn(Optional.of(rate));

        BigDecimal bd = new BigDecimal((item.getCartonPrice()/item.getItemsPerCarton())*(1+rate.getRateValue())).setScale(2, RoundingMode.HALF_UP);
        double expectedPrice = bd.doubleValue();

        this.mockMvc.perform(
                        get("/items/calculated")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.[0].actualPrice").value(expectedPrice))
                .andExpect(status().isOk());
    }

    @Test
    void getCalculatedItemsWithoutRate() throws Exception {

        Item item = modelMapper.map(new ItemDTO(1l,"testItem", 45,10.00,false), Item.class);
        Rate rate = null;
        when(itemRepository.findAll()).thenReturn( List.of(item));
        when(rateRepository.findById(anyLong())).thenReturn(Optional.empty());

        double expectedPrice = 0.00;

        this.mockMvc.perform(
                        get("/items/calculated")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.[0].actualPrice").value(expectedPrice))
                .andExpect(status().isOk());
    }

    @Test
    void getItems() throws Exception {
        Item item = modelMapper.map(new ItemDTO(1l,"testItem", 45,10.00,false), Item.class);
        when(itemRepository.findAll()).thenReturn( List.of(item));
        this.mockMvc.perform(
                        get("/items/calculated")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.[0].itemName").value("testItem"))
                .andExpect(status().isOk());

    }
}