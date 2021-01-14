package com.galaxy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Drug {

    Map<String, List<String>> availability;
}
