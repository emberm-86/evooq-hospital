package com.interview.edgelab.hospital;

import com.interview.edgelab.hospital.pojo.StateMachine;
import com.interview.edgelab.hospital.service.StateMachineService;
import com.interview.edgelab.hospital.util.RuleInitializer;

public class Application {

  public static void main(String[] args) {
    if (args.length != 2) {
      throw new RuntimeException("Wrong number of input parameters!");
    }

    StateMachine stateMachine = new StateMachine(args[0], args[1], RuleInitializer.initRules());
    StateMachineService stateMachineService = new StateMachineService();
    System.out.println(stateMachineService.evaluate(stateMachine));
  }
}
