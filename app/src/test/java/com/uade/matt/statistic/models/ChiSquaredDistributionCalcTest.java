package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ChiSquaredDistributionCalcTest {
  @Test
  public void calculatePx() throws Exception {
    ChiSquaredDistributionCalc result =
      new ChiSquaredDistributionCalc()
        .degreesOfFreedom(2.0)
        .x(1.0)
        .calculatePx();
    assertNotNull(result);
    assertThat(result.f(), is(0.39347));
    assertThat(result.g(), is(0.60653));

  }


  @Test
  public void calculateInvPx() throws Exception {
    ChiSquaredDistributionCalc result = new ChiSquaredDistributionCalc()
      .degreesOfFreedom(2.0)
      .f(0.39347)
      .calculatePx();

    assertNotNull(result);
    assertThat(result.g(), is(0.60653));
    assertThat(result.x(), is(1.000003));
  }
}