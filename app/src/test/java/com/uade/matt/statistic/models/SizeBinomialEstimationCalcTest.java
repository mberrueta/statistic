package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SizeBinomialEstimationCalcTest {
  @Test
  public void calculateNRLimitsCase1() {
    SizeBinomialEstimationCalc result = new SizeBinomialEstimationCalc()
      .p0(0.12).alpha(0.05).p1(0.14).beta(0.5).lessThan(true).calc();
    assertNotNull(result);
    System.out.print(result.resultMessage());
    assertThat(result.size() < 770, is(true));
    assertThat(result.size() > 700, is(true));
    assertThat(result.sampleSize() < 120, is(true));
    assertThat(result.sampleSize() > 100, is(true));
  }

  @Test
  public void calculateNRLimitsCase1b() {
    SizeBinomialEstimationCalc result = new SizeBinomialEstimationCalc().p0(0.11).alpha(0.05).p1(0.16).beta(0.01).lessThan(true)
        .calc();
    assertNotNull(result);
    System.out.print(result.resultMessage());
    assertThat(result.size() < 750, is(true));
    assertThat(result.size() > 700, is(true));
    assertThat(result.sampleSize() < 100, is(true));
    assertThat(result.sampleSize() > 90, is(true));
  }

  @Test
  public void calculateNRLimitsCase2() {
    SizeBinomialEstimationCalc result = new SizeBinomialEstimationCalc().p0(0.95).alpha(0.4205).p1(0.9).beta(0.002).lessThan(false)
      .calc();
    assertNotNull(result);
    System.out.print(result.resultMessage());
    // aprox 450
    assertThat(result.size() < 470, is(true));
    assertThat(result.size() > 430, is(true));
    //aprox 430
    assertThat(result.sampleSize() < 440, is(true));
    assertThat(result.sampleSize() > 420, is(true));
  }
}