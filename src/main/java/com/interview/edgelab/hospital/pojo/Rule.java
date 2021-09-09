package com.interview.edgelab.hospital.pojo;

/**
 * Class representation of a rule.
 */
public class Rule {

  private final Status from;
  private final Status to;
  private final Drug[] drugs;
  private final boolean strict;
  // Strict flag check is necessary unless the patient could die.

  private Rule(Builder builder) {
    this.from = builder.from;
    this.to = builder.to;
    this.drugs = builder.drugs;
    this.strict = builder.strict;
  }

  public static class Builder {

    private Status from;
    private Status to;
    private Drug[] drugs;
    private boolean strict = false;

    public Builder from(Status from) {
      this.from = from;
      return this;
    }

    public Builder to(Status to) {
      this.to = to;
      return this;
    }

    public Builder drugs(Drug... drugs) {
      this.drugs = drugs;
      return this;
    }

    public Builder strict(boolean strict) {
      this.strict = strict;
      return this;
    }

    public Rule build() {
      return new Rule(this);
    }
  }

  public Status getFrom() {
    return from;
  }

  public Status getTo() {
    return to;
  }

  public Drug[] getDrugs() {
    return drugs;
  }

  public boolean isStrict() {
    return strict;
  }
}
