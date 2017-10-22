package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class PBernulliInferenceCalcTest {
  @Test
  public void calculateP() throws Exception {
    PBernulliInferenceCalc result = new PBernulliInferenceCalc().alpha(0.1).size(30).sampleSize(3).calc();
    assertNotNull(result);
    assertThat(result.limitInf(), is(0.027815584949474188));
    assertThat(result.limitSup(), is(0.23859791924039372));
    assertThat(result.sampleP(), is(0.1));
    assertThat(result.error(), is(0.10539116714545976));
  }
}