package com.interview.edgelab.hospital.pojo;

import java.util.List;

/**
 * This object will be evaluated by the service.
 */
public class StateMachine {

  private final String patients;
  private final String medicines;
  private final List<Rule> rules;
  private final int spaghettiProbability;

  private static final int FLYING_SPAGHETTI_MONSTER_SHOWS_POWER = 1000000;

  public StateMachine(String patients, String medicines, List<Rule> rules,
      int spaghettiProbability) {
    this.patients = patients;
    this.medicines = medicines;
    this.rules = rules;
    this.spaghettiProbability = spaghettiProbability;
  }

  public StateMachine(String patients, String medicines, List<Rule> rules) {
    this(patients, medicines, rules, FLYING_SPAGHETTI_MONSTER_SHOWS_POWER);
  }

  public String getPatients() {
    return patients;
  }

  public String getMedicines() {
    return medicines;
  }

  public List<Rule> getRules() {
    return rules;
  }

  public int getSpaghettiProbability() {
    return spaghettiProbability;
  }
}
