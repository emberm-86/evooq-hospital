package com.interview.edgelab.hospital.service;

import com.interview.edgelab.hospital.pojo.Drug;
import com.interview.edgelab.hospital.pojo.StateMachine;
import com.interview.edgelab.hospital.pojo.Status;
import com.interview.edgelab.hospital.util.CollectionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class contains the business logic.
 */
public class StateMachineService {

  private static final String SPLITTER = "[,]";

  /**
   * Method for evaluating the input from the command line.
   */
  public String evaluate(StateMachine stateMachine) {
    Map<Status, Long> patients = getPatientsByStatus(stateMachine.getPatients());
    Set<Set<Drug>> medicinesPowerSet = getMedicines(stateMachine.getMedicines());

    // Processing by the rules.
    stateMachine.getRules().forEach(rule -> {
      Long fromValue = patients.get(rule.getFrom());

      if (fromValue == null || !patients.containsKey(rule.getTo())) {
        // This can happen if someone adds a rule with invalid status.
        throw new RuntimeException("Rule is not consistent with Status value set!");
      }

      // Evaluation of the actual rule.
      Set<Drug> medicineRuleSet = Arrays.stream(rule.getDrugs()).collect(Collectors.toSet());

      Status toStatus = medicinesPowerSet.stream().anyMatch(x -> x.containsAll(medicineRuleSet))
          ? rule.getTo() : (rule.isStrict() ? Status.DEAD : null);

      if (toStatus == null || toStatus.equals(rule.getFrom())) {
        return; // Rule is skipped.
      }

      /* Every patient will get medicine and moved to the status which is the result of
         the evaluation of the rule. */
      patients.computeIfPresent(toStatus, (k, v) -> v + fromValue);
      patients.put(rule.getFrom(), 0L);
    });

    checkIfSpaghettiMonsterComes(patients, stateMachine.getSpaghettiProbability());
    return formattedOutput(patients);
  }

  /**
   * Deserializing the medicine arg of the input.
   */
  private Set<Set<Drug>> getMedicines(String medicinesInp) {
    String[] medicinesArr = medicinesInp != null ? medicinesInp.split(SPLITTER) : new String[]{};

    List<Drug> medicines = Arrays.stream(medicinesArr).map(Drug::fromId)
        .filter(Objects::nonNull).distinct().collect(Collectors.toList());

    return CollectionUtil.powerSet(medicines);
  }

  /**
   * Deserializing the patient arg of the input.
   */
  private Map<Status, Long> getPatientsByStatus(String patientsInp) {
    if (patientsInp == null) {
      throw new RuntimeException("Patients cannot be null!");
    }

    Map<Status, Long> patients = Arrays.stream(patientsInp.split(SPLITTER)).map(Status::fromId)
        .filter(Objects::nonNull)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    Arrays.stream(Status.values()).forEach(k -> patients.putIfAbsent(k, 0L));
    return patients;
  }

  /**
   * Updating patient states if Spaghetti monster comes.
   */
  private void checkIfSpaghettiMonsterComes(Map<Status, Long> patients, int spaghettiProbability) {
    if (!flyingSpaghettiMonsterShowsPower(spaghettiProbability) || patients.get(Status.DEAD) <= 0) {
      return;
    }

    patients.computeIfPresent(Status.DEAD, (k, v) -> v - 1);
    patients.computeIfPresent(Status.HEALTHY, (k, v) -> v + 1);
  }

  /**
   * Providing readable formatted result.
   */
  private String formattedOutput(Map<Status, Long> patients) {
    return patients.keySet().stream().sorted(Status::compareTo)
        .map(status -> status.getId() + ":" + patients.get(status))
        .collect(Collectors.joining(","));
  }

  /**
   * Spaghetti monster possibility calculation.
   */
  private boolean flyingSpaghettiMonsterShowsPower(int spaghettiProbability) {
    return 0 == new Random().nextInt(spaghettiProbability);
  }
}
