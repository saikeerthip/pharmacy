package com.galaxy.service;

import com.galaxy.connector.DrugServiceConnector;
import com.galaxy.connector.PatientServiceConnector;
import com.galaxy.exception.PrescriptionException;
import com.galaxy.model.Prescription;
import com.galaxy.model.PrescriptionRequest;
import com.galaxy.model.PrescriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class PrescriptionService {

    @Autowired
    private PatientServiceConnector patientServiceConnector;
    @Autowired
    private DrugServiceConnector drugServiceConnector;

    public PrescriptionResponse getPrescription(PrescriptionRequest request) {
        PrescriptionResponse prescriptionResponse = new PrescriptionResponse();

        if (!patientServiceConnector.isPatientValid(request.getPatientName())) {
            throw new PrescriptionException("0001", "Patient Not Found in the Database");
        }

        Map<String, List<String>> availability = new HashMap<>();
        List<String> valid = new ArrayList<>();
        List<String> inValid = new ArrayList<>();

        request.getDrug().forEach(drug -> {
            if (!drugServiceConnector.isDrugValid(drug)) {
                inValid.add(drug);
            } else {
                valid.add(drug);
            }
        });
        availability.put("Available", valid);
        availability.put("NotAvailable", inValid);

        if (isEmpty(valid)) {
            throw new PrescriptionException("0002", "Drugs are Not Valid/ Doesn't exist");
        }
        ;

        Prescription prescription = new Prescription();
        prescription.setPatientId(request.getPatientId());
        prescription.setPatientName(request.getPatientName());
        prescription.setDrug(availability);
        prescription.setPrescriptionDate(new Date().toString());
        prescription.setPrescriptionReferenceNumber(UUID.randomUUID().toString());

        prescriptionResponse.setPrescription(prescription);

        return prescriptionResponse;
    }
}
