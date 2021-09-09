package com.interview.edgelab.hospital.pojo;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Possible statuses of a patient.
 */
public enum Status {

  FEVER("F"),
  HEALTHY("H"),
  DIABETES("D"),
  TUBERCULOSIS("T"),
  DEAD("X");

  private final String id;

  Status(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public static Status fromId(String id) {
    return STATUS_LOOKUP_MAP.get(id);
  }

  public static final Map<String, Status>
      STATUS_LOOKUP_MAP = Arrays.stream(values())
      .collect(Collectors.toMap(Status::getId, Function.identity()));
}
