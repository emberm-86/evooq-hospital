package com.interview.edgelab.hospital.pojo;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Following drugs could be served to the patients.
 */
public enum Drug {

  ASPIRIN("As"),
  ANTIBIOTIC("An"),
  INSULIN("I"),
  PARACETAMOL("P");

  private final String id;

  Drug(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public static Drug fromId(String id) {
    return STATUS_LOOKUP_MAP.get(id);
  }

  private static final Map<String, Drug>
      STATUS_LOOKUP_MAP = Arrays.stream(values()).collect(
      Collectors.toMap(Drug::getId, Function.identity()));
}