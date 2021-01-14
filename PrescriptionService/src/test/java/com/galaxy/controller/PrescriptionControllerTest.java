package com.galaxy.controller;


import com.galaxy.connector.DrugServiceConnector;
import com.galaxy.connector.PatientServiceConnector;
import com.galaxy.model.Prescription;
import com.galaxy.model.PrescriptionResponse;
import com.galaxy.service.PrescriptionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
//@NoArgsConstructor
public class PrescriptionControllerTest {

    @Mock
    PrescriptionService service;

    MockMvc mockMvc;

    @InjectMocks
    PrescriptionController controller;

    @Mock
    PatientServiceConnector patientServiceConnector;
    @Mock
    DrugServiceConnector drugServiceConnector;


    PrescriptionResponse getResponse() {
        PrescriptionResponse response = new PrescriptionResponse();

        Prescription prescription = new Prescription();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        map.computeIfAbsent("valid", k -> new ArrayList<>()).add("mRNA");
        prescription.setDrug(map);
        prescription.setPatientId("k123");
        prescription.setPatientName("kee");
        prescription.setPrescriptionDate(new Date().toString());
        response.setMessage("resp");
        response.setPrescription(prescription);
        response.setStatus("avail");
        return response;
    }

    @Test
    public void getPrescription() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        Mockito.when(patientServiceConnector.isPatientValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(drugServiceConnector.isDrugValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(service.getPrescription(Mockito.any())).thenReturn(getResponse());
        MvcResult resp = mockMvc.perform(post("http://localhost:8441/api/v1/prescription").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content("{\n" +
                        "\t\"patientId\":\"k123\",\n" +
                        "\t\"patientName\":\"keerthi\",\n" +
                        "\t\"drug\":[\"mRNA\",\"cov\"]\n" +
                        "}")).andExpect(status().isOk()).andReturn();
        assertThat(resp.getResponse().getContentAsString()).isNotNull();
        Assert.assertTrue(resp.getResponse().getContentAsString().contains("k123"));
    }

    @Test
    public void getPrescriptionFailedTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        Mockito.when(patientServiceConnector.isPatientValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(drugServiceConnector.isDrugValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(service.getPrescription(Mockito.any())).thenReturn(getResponse());
        Object resp = mockMvc.perform(post("http://localhost:8441/api/v1/prescription").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content("{\n" +
                        "\t\"patientId\":\"k123\",\n" +
                        "\t\"patientName\":\"\",\n" +
                        "\t\"drug\":[\"mRNA\",\"cov\"]\n" +
                        "}")).andExpect(status().isBadRequest());
    }
}