package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class VarianceInferenceCalcTest {
  @Test
  public void calculateVarianceLimits() throws Exception {
    VarianceInferenceCalc result =
      new VarianceInferenceCalc()
        .alpha(0.1)
        .sampleStandardDeviation(120.0)
        .sampleSize(15)
        .calc();
    assertNotNull(result);
    assertThat(result.limitInfVariance(), is(8511.791026072764));
    assertThat(result.limitSupVariance(), is(30681.98005914804));
    assertThat(result.limitInfStandardDeviation(), is(92.2593682293173));
    assertThat(result.limitSupStandardDeviation(), is(175.16272451394457));
    assertThat(result.limitRelationshipStandardDeviation(), is(1.8985901147573978));
  }

  @Test
  public void calculateSampleSize() throws Exception {
    VarianceInferenceCalc result =
      new VarianceInferenceCalc()
        .alpha(0.1)
        .sampleStandardDeviation(120.0)
        .limitRelationshipStandardDeviation(2.0)
        .calc();
    assertNotNull(result);
    assertThat(result.sampleSize(), is(14));
    assertThat(result.degreesOfFreedom(), is(13));
  }

  @Test
  public void calculateSampleSize2() throws Exception {
    VarianceInferenceCalc result =
      new VarianceInferenceCalc()
        .alpha(0.1)
        .sampleStandardDeviation(120.0)
        .limitRelationshipVariance(4.0)
        .calc();
    assertNotNull(result);
    assertThat(result.sampleSize(), is(14));
    assertThat(result.degreesOfFreedom(), is(13));
  }

  @Test
  public void calculateVarianceLimits2() throws Exception {
    VarianceInferenceCalc result =
      new VarianceInferenceCalc()
        .alpha(0.1)
        .sampleStandardDeviation(0.1924)
        .sampleSize(5)
        .calc();
    assertNotNull(result);
    assertThat(result.limitInfVariance(), is(0.01560658239642148));
    assertThat(result.limitSupVariance(), is(0.20833831416977613));
  }
}