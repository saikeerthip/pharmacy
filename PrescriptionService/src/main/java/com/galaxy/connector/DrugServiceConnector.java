package com.galaxy.connector;

import com.galaxy.exception.PrescriptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Component
@Slf4j
public class DrugServiceConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(value = {ResourceAccessException.class, IOException.class, SocketTimeoutException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public Boolean isDrugValid(String drugName) {
        Boolean response;
        try {
            response = restTemplate.getForObject("http://localhost:8443/api/v1/drug?drug=" + drugName, Boolean.class);
        } catch (Exception ex) {
            log.error("Error Accessing Drug Service");
            throw new PrescriptionException("Drug Service Error", ex.getMessage(), ex);
        }
        return response;
    }
}
