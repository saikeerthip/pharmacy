package com.galaxy.connector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PatientServiceConnectorTest {

    @Autowired
    private PatientServiceConnector patientServiceConnector;

    @Test
    public void testRetry() {
        try {
            patientServiceConnector.isPatientValid("keerthi");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isPatientValid() {


    }
}