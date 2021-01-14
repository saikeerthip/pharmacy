package com.galaxy.service;

import com.galaxy.connector.DrugServiceConnector;
import com.galaxy.connector.PatientServiceConnector;
import com.galaxy.exception.PrescriptionException;
import com.galaxy.model.PrescriptionRequest;
import com.galaxy.model.PrescriptionResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;


@RunWith(MockitoJUnitRunner.class)
public class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionService prescriptionService;
    @Mock
    private PatientServiceConnector patientServiceConnector;
    @Mock
    private DrugServiceConnector drugServiceConnector;

    PrescriptionRequest getRequest() {
        PrescriptionRequest request = new PrescriptionRequest();
        request.setPatientId("k123");
        request.setPatientName("kee");
        request.setDrug(Arrays.asList("cov", "mRNA"));
        return request;
    }


    @Test
    public void getPrescription() {
        Mockito.when(patientServiceConnector.isPatientValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(drugServiceConnector.isDrugValid(Mockito.anyString())).thenReturn(true);
        PrescriptionResponse response = prescriptionService.getPrescription(getRequest());
        Assert.assertNotNull(response.getPrescription());
    }

    @Test(expected = PrescriptionException.class)
    public void patientNotFoundTest() {
        Mockito.when(patientServiceConnector.isPatientValid(Mockito.anyString())).thenReturn(false);
        PrescriptionResponse response = prescriptionService.getPrescription(getRequest());
    }

    @Test
    public void patientNotFoundResponseCodeTest() {
        Mockito.when(patientServiceConnector.isPatientValid(Mockito.anyString())).thenReturn(false);
        try {
            PrescriptionResponse response = prescriptionService.getPrescription(getRequest());
        } catch (PrescriptionException ex) {
            Assert.assertEquals(ex.getMessage(), "Patient Not Found in the Database");
            Assert.assertEquals(ex.getErrorCode(), "0001");
        }
    }

    @Test(expected = PrescriptionException.class)
    public void drugNotFoundTest() {
        Mockito.when(patientServiceConnector.isPatientValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(drugServiceConnector.isDrugValid(Mockito.anyString())).thenReturn(false);
        PrescriptionResponse response = prescriptionService.getPrescription(getRequest());
    }

    @Test
    public void drugNotFoundResponseCodeTest() {
        Mockito.when(patientServiceConnector.isPatientValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(drugServiceConnector.isDrugValid(Mockito.anyString())).thenReturn(false);
        try {
            PrescriptionResponse response = prescriptionService.getPrescription(getRequest());
        } catch (PrescriptionException ex) {
            Assert.assertEquals(ex.getMessage(), "Drugs are Not Valid/ Doesn't exist");
            Assert.assertEquals(ex.getErrorCode(), "0002");
        }
    }
}