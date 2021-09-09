package com.interview.edgelab.hospital.test;

import com.interview.edgelab.hospital.pojo.Drug;
import com.interview.edgelab.hospital.pojo.Rule;
import com.interview.edgelab.hospital.pojo.StateMachine;
import com.interview.edgelab.hospital.pojo.Status;
import com.interview.edgelab.hospital.service.StateMachineService;
import com.interview.edgelab.hospital.util.RuleInitializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class ApplicationTest {

  private static List<Rule> rules;

  private static StateMachineService stateMachineService;

  @BeforeAll
  public static void initRules() {
    rules = RuleInitializer.initRules();
    stateMachineService = new StateMachineService();
  }

  @Test
  public void testInvalidRule() {
    Rule invalidRule = new Rule.Builder()
        .from(Status.DIABETES)
        .to(Status.FEVER)
        .drugs(Drug.ANTIBIOTIC)
        .build();

    StateMachine stateMachine = new StateMachine(null, "As",
        Collections.singletonList(invalidRule));

    Assertions.assertThrows(RuntimeException.class,
        () -> stateMachineService.evaluate(stateMachine));
  }

  @Test
  public void testNullPatientsList() {
    StateMachine stateMachine = new StateMachine(null, "As", rules);

    Assertions.assertThrows(RuntimeException.class,
        () -> stateMachineService.evaluate(stateMachine));
  }

  @Test
  public void testOnlyFirstInput() {
    StateMachine stateMachine = new StateMachine("D,D", null, rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:0,D:0,T:0,X:2");
  }

  @Test
  public void testHealthyWithInvalidSecondArg() {
    StateMachine stateMachine = new StateMachine("F", "Invalid medicine", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:1,H:0,D:0,T:0,X:0");
  }

  @Test
  public void testWithEmptyFirstInput() {
    StateMachine stateMachine = new StateMachine("", "", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:0,D:0,T:0,X:0");
  }

  @Test
  public void testFeverWithAspirin() {
    StateMachine stateMachine = new StateMachine("F,F,F,F", "As", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:4,D:0,T:0,X:0");
  }

  @Test
  public void testTuberculosisWithAntibiotic() {
    StateMachine stateMachine = new StateMachine("T,T", "An", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:2,D:0,T:0,X:0");
  }

  @Test
  public void testDiabetesWithInsulin() {
    StateMachine stateMachine = new StateMachine("D,D", "I", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:0,D:2,T:0,X:0");
  }

  @Test
  public void testDiabetesWithEmptySecondArg() {
    StateMachine stateMachine = new StateMachine("D,D", "", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:0,D:0,T:0,X:2");
  }

  @Test
  public void testHealthyWithInsulinAndAntibiotic() {
    StateMachine stateMachine = new StateMachine("H,H,H", "An,I", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:3,H:0,D:0,T:0,X:0");
  }

  @Test
  public void testFeverWithParacetamol() {
    StateMachine stateMachine = new StateMachine("F", "P", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:1,D:0,T:0,X:0");
  }

  @Test
  public void testHealthyWithParacetamolAndAspirin() {
    StateMachine stateMachine = new StateMachine("H", "P,As", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:0,D:0,T:0,X:1");
  }

  @Test
  public void spaghettiMonsterBringBackToLiveA() {
    StateMachine stateMachine = new StateMachine("X", "P,As", rules, 1);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:1,D:0,T:0,X:0");
  }

  @Test
  public void spaghettiMonsterBringBackToLiveB() {
    StateMachine stateMachine = new StateMachine("X,X", "P,As", rules, 1);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:1,D:0,T:0,X:1");
  }

  @Test
  public void testHealthyAndDiabetesWithInsulinAndAntibiotic() {
    StateMachine stateMachine = new StateMachine("H,D,H", "An,I", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:2,H:0,D:1,T:0,X:0");
  }

  // First state change Tuberculosis to Healthy, second is Healthy to Fever
  @Test
  public void testTuberculosisAndDiabetesWithInsulinAndAntibiotic() {
    StateMachine stateMachine = new StateMachine("D,D,T,T", "An,I", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:2,H:0,D:2,T:0,X:0");
  }

  // All medicines served total death.
  @Test
  public void testWithAllMedicines() {
    StateMachine stateMachine = new StateMachine("D,D,T,T", "P,An,I,As", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:0,D:0,T:0,X:4");
  }

  @Test
  public void testExtra() {
    StateMachine stateMachine = new StateMachine("D,D,T,T", "P,An,I", rules);
    Assertions.assertEquals(stateMachineService.evaluate(stateMachine), "F:0,H:2,D:2,T:0,X:0");
  }
}
