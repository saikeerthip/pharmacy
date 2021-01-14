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
public class DrugController {

	List<String> drugs = Arrays.asList("mRNA", "DTaP", "Haemophilus");

	@GetMapping("drug")
	public ResponseEntity<Boolean> getDrug(@RequestParam String drug) {

		return new ResponseEntity<>(drugs.stream().anyMatch(e -> StringUtils.equalsIgnoreCase(e, drug)), HttpStatus.OK);
	}
}
