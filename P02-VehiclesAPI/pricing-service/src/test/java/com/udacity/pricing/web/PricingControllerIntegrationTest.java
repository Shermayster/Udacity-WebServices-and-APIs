package com.udacity.pricing.web;

import com.udacity.pricing.domain.price.Price;

import org.springframework.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPriceSuccess() {
        ResponseEntity<Price> response = this.restTemplate
        .getForEntity("http://localhost:" + port + "/services/price?vehicleId=1", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Price resBody = response.getBody();
        assertEquals((long)1,resBody.getVehicleId());
        assertEquals("USD", resBody.getCurrency());
    }

    @Test
    public void getPriceBadRequest() {
        ResponseEntity<Price> response = this.restTemplate
        .getForEntity("http://localhost:" + port + "/services/price", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getPriceExceptionNotFound() {
        ResponseEntity<Price> response = this.restTemplate
        .getForEntity("http://localhost:" + port + "/services/price?vehicleId=20", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

}
