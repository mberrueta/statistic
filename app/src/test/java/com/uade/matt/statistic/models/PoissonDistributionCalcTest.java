package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class PoissonDistributionCalcTest {
  @Test
  public void calculatePx() throws Exception {
    PoissonDistributionCalc result =
      new PoissonDistributionCalc()
        .frequency(245.0)
        .r(280)
        .calculatePx();
    assertNotNull(result);
    assertThat(result.p(), is(0.002187));
    assertThat(result.f(), is(0.987017));
    assertThat(result.g(), is(0.01517));

  }


  @Test
  public void calculateInvPx() throws Exception {
    PoissonDistributionCalc result =
      new PoissonDistributionCalc()
        .frequency(245.0)
        .g(0.01517)
        .calculatePx();
    assertNotNull(result);
    assertThat(result.resultMessage(), is("Range values: \nP(r>279) = 0.017669 \nP(r>280) = 0.015170"));

  }


  @Test
  public void calculateInvPxf() throws Exception {
    PoissonDistributionCalc result =
      new PoissonDistributionCalc()
        .frequency(245.0)
        .f(0.987017)
        .calculatePx();
    assertNotNull(result);
    assertThat(result.resultMessage(), is("Range values: \nP(r<280) = 0.987017 \nP(r<281) = 0.988923"));

  }
}