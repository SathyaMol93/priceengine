package com.sathyamolagoda.assessment.priceengine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sathyamolagoda.assessment.priceengine.dtos.ItemDTO;
import com.sathyamolagoda.assessment.priceengine.dtos.PriceItemDTO;
import com.sathyamolagoda.assessment.priceengine.dtos.RateDTO;
import com.sathyamolagoda.assessment.priceengine.models.Item;
import com.sathyamolagoda.assessment.priceengine.models.Rate;
import com.sathyamolagoda.assessment.priceengine.repositories.ItemRepository;
import com.sathyamolagoda.assessment.priceengine.repositories.RateRepository;
import com.sathyamolagoda.assessment.priceengine.services.PriceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private PriceService priceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private RateRepository rateRepository;

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
    void getCalculatedPrice() throws Exception {
        PriceItemDTO priceItemDTO = new PriceItemDTO();
        priceItemDTO.setItemId(1l);
        priceItemDTO.setQuantity(50);
        String content = this.objectWriter.writeValueAsString(priceItemDTO);
        Item item = modelMapper.map(new ItemDTO(1l, "testItem", 45, 10.00, false), Item.class);
        when(this.itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        Rate discountRate = modelMapper.map(new RateDTO(1l, "Discount rate", 0.10, null, 1l), Rate.class);
        Rate unitRate = modelMapper.map(new RateDTO(2l, "Unit rate", 0.30, null, 1l), Rate.class);

        when(this.rateRepository.findById(ArgumentMatchers.eq(1l)))
                .thenReturn(Optional.of(discountRate));

        when(this.rateRepository.findById(ArgumentMatchers.eq(2l)))
                .thenReturn(Optional.of(unitRate));

        int cartons = 50 / 45;
        int remainder = 50 % 45;
        double cartonPrice = cartons * 10.00;
        double unitPrice = 10.00 / 45;
        double remainderPrice = remainder * unitPrice * 1.3;
        BigDecimal bd = new BigDecimal(cartonPrice + remainderPrice).setScale(2, RoundingMode.HALF_UP);
        double total = bd.doubleValue();
        System.out.println("total : " + total);

        this.mockMvc.perform(
                        post("/prices/item")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andDo(print())
                .andExpect(jsonPath("$.totalPrice").value(total))
                .andExpect(status().isOk());
    }

    @Test
    void getCalculatedPriceWithDiscount() throws Exception {
        PriceItemDTO priceItemDTO = new PriceItemDTO();
        priceItemDTO.setItemId(1l);
        priceItemDTO.setQuantity(150);
        String content = this.objectWriter.writeValueAsString(priceItemDTO);
        Item item = modelMapper.map(new ItemDTO(1l, "testItem", 45, 10.00, false), Item.class);
        when(this.itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        Rate discountRate = modelMapper.map(new RateDTO(1l, "Discount rate", 0.10, null, 1l), Rate.class);
        Rate unitRate = modelMapper.map(new RateDTO(2l, "Unit rate", 0.30, null, 1l), Rate.class);

        when(this.rateRepository.findById(ArgumentMatchers.eq(1l)))
                .thenReturn(Optional.of(discountRate));

        when(this.rateRepository.findById(ArgumentMatchers.eq(2l)))
                .thenReturn(Optional.of(unitRate));

        int cartons = 150 / 45;
        int remainder = 150 % 45;
        double cartonPrice = cartons * 10.00 * 0.9;
        double unitPrice = 10.00 / 45;
        double remainderPrice = remainder * unitPrice * 1.3;
        BigDecimal bd = new BigDecimal(cartonPrice + remainderPrice).setScale(2, RoundingMode.HALF_UP);
        double total = bd.doubleValue();
        System.out.println("total : " + total);

        this.mockMvc.perform(
                        post("/prices/item")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andDo(print())
                .andExpect(jsonPath("$.totalPrice").value(total))
                .andExpect(status().isOk());
    }

    @Test
    void getCalculatedPrices() throws Exception {
        PriceItemDTO priceItemDTO1 = new PriceItemDTO();
        priceItemDTO1.setItemId(1l);
        priceItemDTO1.setQuantity(50);
        PriceItemDTO priceItemDTO2 = new PriceItemDTO();
        priceItemDTO1.setItemId(2l);
        priceItemDTO1.setQuantity(163);
        String content = this.objectWriter.writeValueAsString(List.of(priceItemDTO1, priceItemDTO2));

        Item item1 = modelMapper.map(new ItemDTO(1l, "testItem", 45, 10.00, false), Item.class);
        Item item2 = modelMapper.map(new ItemDTO(2l, "testItem", 50, 20.00, false), Item.class);
        when(this.itemRepository.findById(ArgumentMatchers.eq(1l))).thenReturn(Optional.of(item1));
        when(this.itemRepository.findById(ArgumentMatchers.eq(2l))).thenReturn(Optional.of(item2));

        Rate discountRate = modelMapper.map(new RateDTO(1l, "Discount rate", 0.10, null, 1l), Rate.class);
        Rate unitRate = modelMapper.map(new RateDTO(2l, "Unit rate", 0.30, null, 1l), Rate.class);

        when(this.rateRepository.findById(ArgumentMatchers.eq(1l)))
                .thenReturn(Optional.of(discountRate));

        when(this.rateRepository.findById(ArgumentMatchers.eq(2l)))
                .thenReturn(Optional.of(unitRate));

        int cartons1 = 50 / 45;
        int remainder1 = 50 % 45;
        double cartonPrice1 = cartons1 * 10.00;
        double unitPrice1 = 10.00 / 45;
        double remainderPrice1 = remainder1 * unitPrice1 * 1.3;
        BigDecimal bd1 = new BigDecimal(cartonPrice1 + remainderPrice1).setScale(2, RoundingMode.HALF_UP);
        double total1 = bd1.doubleValue();
        System.out.println("total 1 : " + total1);

        int cartons2 = 163 / 50;
        int remainder2 = 163 % 50;
        double cartonPrice2 = cartons2 * 20.00;
        double unitPrice2 = 20.00 / 50;
        double remainderPrice2 = remainder2 * unitPrice2 * 1.3;
        BigDecimal bd2 = new BigDecimal(cartonPrice2 + remainderPrice2).setScale(2, RoundingMode.HALF_UP);
        double total2 = bd2.doubleValue();
        System.out.println("total 2 : " + total2);

        this.mockMvc.perform(
                        post("/prices/items")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andDo(print())
//                .andExpect(jsonPath("$.[0].totalPrice").value(total1))
//                .andExpect(jsonPath("$.[1].totalPrice").value(total2))
                .andExpect(status().isOk());
    }
}