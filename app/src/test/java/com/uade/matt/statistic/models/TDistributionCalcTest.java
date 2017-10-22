package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class TDistributionCalcTest {
  @Test
  public void calculatePx() throws Exception {
    TDistributionCalc result = new TDistributionCalc().degreesOfFreedom(55.8).x(1.99).calculatePx();
    assertNotNull(result);
    assertThat(result.g(), is(0.02574966994838479));
    assertThat(result.f(), is(0.9742503300516152));

  }

  @Test
  public void calculateInvPx() throws Exception {
    TDistributionCalc result = new TDistributionCalc().degreesOfFreedom(55.8).f(0.97).calculatePx();

    assertNotNull(result);
    assertThat(result.x(), is(1.9198083715624092));
  }
}