package com.udacity.pricing.web;

import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.service.PricingService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    PricingService pricingService;

    @Test
    public void getPriceSuccess() throws Exception {
        Long vehicleId = (long) 1;
        mockMvc.perform(get("/services/price?vehicleId=1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(pricingService, times(1)).getPrice(vehicleId);
    }

    @Test
    public void getPriceExceptionNotFound() throws Exception {
        mockMvc.perform(get("/services/price?vehicleId=21"))
        .andExpect(status().isNotFound());
    }

    @Test
    public void getPriceExceptionBadRequest() throws Exception {
        mockMvc.perform(get("/services/price?"))
        .andExpect(status().isBadRequest());
    }
}
