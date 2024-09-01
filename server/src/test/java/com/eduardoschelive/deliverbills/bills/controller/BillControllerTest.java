package com.eduardoschelive.deliverbills.bills.controller;

import com.eduardoschelive.deliverbills.bills.dto.BillDTO;
import com.eduardoschelive.deliverbills.bills.dto.UpsertBillDTO;
import com.eduardoschelive.deliverbills.bills.service.BillService;
import com.eduardoschelive.deliverbills.bills.utils.Endpoint;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureMockMvc
@SpringBootTest
class BillControllerTest {

    @MockBean
    private BillService billService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void it_should_return_201_when_create_a_bill() throws Exception {
        var billDTO = new BillDTO(
                UUID.randomUUID(),
                "Test",
                BigDecimal.ONE,
                BigDecimal.TWO,
                9, LocalDate.of(2021, 10, 1),
                LocalDate.of(2021, 10, 10)
        );

        when(billService.create(any(UpsertBillDTO.class))).thenReturn(billDTO);

        mockMvc.perform(post(Endpoint.Bills.BASE_PATH).content(
                        """
                                    {
                                      "name": "Test",
                                      "originalValue": 300,
                                      "dueDate": "2021-10-01",
                                      "paymentDate": "2021-10-10"
                                    }
                                """
                ).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    void list_all_bills() throws Exception {
        when(billService.list(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get(Endpoint.Bills.BASE_PATH))
                .andExpect(status().isOk());
    }

    @Test
    void it_should_return_204_when_delete_a_bill() throws Exception {
        mockMvc.perform(delete(Endpoint.Bills.BASE_PATH + "/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isNoContent());
    }

    @Test
    void it_should_return_204_when_update_a_bill() throws Exception {
        mockMvc.perform(put(Endpoint.Bills.BASE_PATH + "/123e4567-e89b-12d3-a456-426614174000").content(
                        """
                                    {
                                      "name": "Test",
                                      "originalValue": 300,
                                      "dueDate": "2021-10-01",
                                      "paymentDate": "2021-10-10"
                                    }
                                """
                ).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

}