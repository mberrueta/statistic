package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SizeBinomialEstimationCalcTest {
  @Test
  public void calculateVarianceLimits() {
    SizeBinomialEstimationCalc result = new SizeBinomialEstimationCalc().p0(0.12).alpha(0.05).p1(0.14).beta(0.5).calc();
    assertNotNull(result);
    System.out.print(result.resultMessage());
    assertThat(result.size() < 770, is(true));
    assertThat(result.size() > 700, is(true));
    assertThat(result.sampleSize() < 120, is(true));
    assertThat(result.sampleSize() > 100, is(true));
  }

  @Test
  public void calculateVarianceLimits2() {
    SizeBinomialEstimationCalc result = new SizeBinomialEstimationCalc().p0(0.11).alpha(0.05).p1(0.16).beta(0.01)
        .calc();
    assertNotNull(result);
    System.out.print(result.resultMessage());
    assertThat(result.size() < 750, is(true));
    assertThat(result.size() > 700, is(true));
    assertThat(result.sampleSize() < 100, is(true));
    assertThat(result.sampleSize() > 90, is(true));
  }
}