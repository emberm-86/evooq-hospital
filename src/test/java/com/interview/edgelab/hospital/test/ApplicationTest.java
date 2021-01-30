package com.interview.edgelab.hospital.test;

import com.interview.edgelab.hospital.pojo.Drug;
import com.interview.edgelab.hospital.pojo.Rule;
import com.interview.edgelab.hospital.pojo.StateMachine;
import com.interview.edgelab.hospital.pojo.Status;
import com.interview.edgelab.hospital.service.StateMachineService;
import com.interview.edgelab.hospital.util.RuleInitializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class ApplicationTest {

    private List<Rule> rules;

    private StateMachineService stateMachineService;

    @Before
    public void initRules() {
        rules = RuleInitializer.initRules();
        stateMachineService = new StateMachineService();
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidRule() {

        Rule invalidRule = new Rule.Builder()
                .from(Status.DIABETES)
                .to(Status.FEVER)
                .drugs(Drug.ANTIBIOTIC)
                .build();

        StateMachine stateMachine = new StateMachine(null, "As",
                Collections.singletonList(invalidRule));

        stateMachineService.evaluate(stateMachine);
    }

    @Test(expected = RuntimeException.class)
    public void testNullPatientsList() {
        StateMachine stateMachine = new StateMachine(null, "As", rules);

        stateMachineService.evaluate(stateMachine);
    }

    @Test
    public void testOnlyFirstInput() {
        StateMachine stateMachine = new StateMachine("D,D", null, rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:0,D:0,T:0,X:2");
    }

    @Test
    public void testHealthyWithInvalidSecondArg() {
        StateMachine stateMachine = new StateMachine("F", "Invalid medicine",
                rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:1,H:0,D:0,T:0,X:0");
    }

    @Test
    public void testWithEmptyFirstInput() {
        StateMachine stateMachine = new StateMachine("", "", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:0,D:0,T:0,X:0");
    }

    @Test
    public void testFeverWithAspirin() {
        StateMachine stateMachine = new StateMachine("F,F,F,F", "As", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:4,D:0,T:0,X:0");
    }

    @Test
    public void testTuberculosisWithAntibiotic() {
        StateMachine stateMachine = new StateMachine("T,T", "An", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:2,D:0,T:0,X:0");
    }

    @Test
    public void testDiabetesWithInsulin() {
        StateMachine stateMachine = new StateMachine("D,D", "I", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:0,D:2,T:0,X:0");
    }

    @Test
    public void testDiabetesWithEmptySecondArg() {
        StateMachine stateMachine = new StateMachine("D,D", "", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:0,D:0,T:0,X:2");
    }

    @Test
    public void testHealthyWithInsulinAndAntibiotic() {
        StateMachine stateMachine = new StateMachine("H,H,H", "An,I", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:3,H:0,D:0,T:0,X:0");
    }

    @Test
    public void testFeverWithParacetamol() {
        StateMachine stateMachine = new StateMachine("F", "P", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:1,D:0,T:0,X:0");
    }

    @Test
    public void testHealthyWithParacetamolAndAspirin() {
        StateMachine stateMachine = new StateMachine("H", "P,As", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:0,D:0,T:0,X:1");
    }

    @Test
    public void spaghettiMonsterBringBackToLiveA() {
        StateMachine stateMachine = new StateMachine("X", "P,As", rules, 1);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:1,D:0,T:0,X:0");
    }

    @Test
    public void spaghettiMonsterBringBackToLiveB() {
        StateMachine stateMachine = new StateMachine("X,X", "P,As", rules, 1);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:1,D:0,T:0,X:1");
    }

    @Test
    public void testHealthyAndDiabetesWithInsulinAndAntibiotic() {
        StateMachine stateMachine = new StateMachine("H,D,H", "An,I", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:2,H:0,D:1,T:0,X:0");
    }

    // First state change Tuberculosis to Healthy, second is Healthy to Fever
    @Test
    public void testTuberculosisAndDiabetesWithInsulinAndAntibiotic() {
        StateMachine stateMachine = new StateMachine("D,D,T,T", "An,I", rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:2,H:0,D:2,T:0,X:0");
    }

    // All medicines served total death.
    @Test
    public void testWithAllMedicines() {
        StateMachine stateMachine = new StateMachine("D,D,T,T", "P,An,I,As",
                rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:0,D:0,T:0,X:4");
    }

    @Test
    public void testExtra() {
        StateMachine stateMachine = new StateMachine("D,D,T,T", "P,An,I",
                rules);

        String result = stateMachineService.evaluate(stateMachine);

        Assert.assertEquals(result, "F:0,H:2,D:2,T:0,X:0");
    }
}
