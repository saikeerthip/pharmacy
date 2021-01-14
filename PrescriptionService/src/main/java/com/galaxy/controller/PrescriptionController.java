package com.galaxy.controller;

import com.galaxy.model.PrescriptionRequest;
import com.galaxy.model.PrescriptionResponse;
import com.galaxy.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/")
public class PrescriptionController {

	@Autowired
	private PrescriptionService prescriptionService;

	@PostMapping(path = "prescription", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PrescriptionResponse> getPrescription(@Valid @RequestBody PrescriptionRequest prescriptionRequest) {
		PrescriptionResponse response = prescriptionService.getPrescription(prescriptionRequest);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
