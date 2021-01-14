package com.galaxy.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class PatientController {

    List<String> patients = Arrays.asList("keerthi", "karthik", "ram");

    @GetMapping("patient")
    public ResponseEntity<Boolean> getPatient(@RequestParam String patientName) {

        return new ResponseEntity<>(patients.stream().anyMatch(e -> StringUtils.equalsIgnoreCase(e, patientName)), HttpStatus.OK);
    }
}
