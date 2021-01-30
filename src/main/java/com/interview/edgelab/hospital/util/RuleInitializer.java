package com.interview.edgelab.hospital.util;

import com.interview.edgelab.hospital.pojo.Drug;
import com.interview.edgelab.hospital.pojo.Rule;
import com.interview.edgelab.hospital.pojo.Status;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Initializing the rules by the task description.
 */
public class RuleInitializer {

    private static final List<Rule> RULES = new CopyOnWriteArrayList<>();

    public static List<Rule> initRules() {

        RULES.add(new Rule.Builder()
                .from(Status.FEVER)
                .to(Status.HEALTHY)
                .drugs(Drug.ASPIRIN)
                .build());

        RULES.add(new Rule.Builder()
                .from(Status.TUBERCULOSIS)
                .to(Status.HEALTHY)
                .drugs(Drug.ANTIBIOTIC)
                .build());

        RULES.add(new Rule.Builder()
                .from(Status.DIABETES)
                .to(Status.DIABETES)
                .drugs(Drug.INSULIN)
                .strict(true)
                .build());

        RULES.add(new Rule.Builder()
                .from(Status.HEALTHY)
                .to(Status.FEVER)
                .drugs(Drug.INSULIN, Drug.ANTIBIOTIC)
                .build());

        RULES.add(new Rule.Builder()
                .from(Status.FEVER)
                .to(Status.HEALTHY)
                .drugs(Drug.PARACETAMOL)
                .build());

        Arrays.stream(Status.values()).forEach(x ->
                RULES.add(new Rule.Builder()
                        .from(x)
                        .to(Status.DEAD)
                        .drugs(Drug.PARACETAMOL, Drug.ASPIRIN)
                        .build()));
        return RULES;
    }
}
