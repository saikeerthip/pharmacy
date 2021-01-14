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
public class PatientServiceConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(value = {ResourceAccessException.class, IOException.class, SocketTimeoutException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public Boolean isPatientValid(String patientName) {
        Boolean response;
        try {
            response = restTemplate.getForObject("http://localhost:8442/api/v1/patient?patientName=" + patientName, Boolean.class);
        } catch (Exception ex) {
            log.error("Error Accessing Patient Service");
            throw new PrescriptionException("Patient Service Error", ex.getMessage(), ex);
        }
        return response;
    }
}
