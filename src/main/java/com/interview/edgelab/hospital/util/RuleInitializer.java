package com.interview.edgelab.hospital.util;

import com.interview.edgelab.hospital.pojo.Drug;
import com.interview.edgelab.hospital.pojo.Rule;
import com.interview.edgelab.hospital.pojo.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Initializing the rules by the task description.
 */
public class RuleInitializer {

  public static List<Rule> initRules() {
    List<Rule> rules = new ArrayList<>();

    rules.add(new Rule.Builder()
        .from(Status.FEVER)
        .to(Status.HEALTHY)
        .drugs(Drug.ASPIRIN)
        .build());

    rules.add(new Rule.Builder()
        .from(Status.TUBERCULOSIS)
        .to(Status.HEALTHY)
        .drugs(Drug.ANTIBIOTIC)
        .build());

    rules.add(new Rule.Builder()
        .from(Status.DIABETES)
        .to(Status.DIABETES)
        .drugs(Drug.INSULIN)
        .strict(true)
        .build());

    rules.add(new Rule.Builder()
        .from(Status.HEALTHY)
        .to(Status.FEVER)
        .drugs(Drug.INSULIN, Drug.ANTIBIOTIC)
        .build());

    rules.add(new Rule.Builder()
        .from(Status.FEVER)
        .to(Status.HEALTHY)
        .drugs(Drug.PARACETAMOL)
        .build());

    Arrays.stream(Status.values()).forEach(x ->
        rules.add(new Rule.Builder()
            .from(x)
            .to(Status.DEAD)
            .drugs(Drug.PARACETAMOL, Drug.ASPIRIN)
            .build()));
    
    return rules;
  }
}
