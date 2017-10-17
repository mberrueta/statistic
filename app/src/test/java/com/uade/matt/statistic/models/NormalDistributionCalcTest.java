package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class NormalDistributionCalcTest {
  @Test
  public void calculatePx() throws Exception {
    NormalDistributionCalc result =
      new NormalDistributionCalc()
        .f(0.95)
        .calculatePx();
    assertNotNull(result);
    assertThat(result.x(), is(1.6448536269514724));

  }


  @Test
  public void calculateInvPx() throws Exception {
    NormalDistributionCalc result = new NormalDistributionCalc()
      .x(1.64485)
      .calculatePx();

    assertNotNull(result);
    assertThat(result.f(), is(0.9499996259309216));
  }
}