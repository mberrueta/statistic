package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MeanInferenceCalcTest {

  //sigma, X, N, y alpha -> A,B
  @Test
  public void calculateMeanKnowedSD() throws Exception {
    MeanInferenceCalc result = new MeanInferenceCalc().alpha(0.1).standardDeviation(15.0).sampleSize(10)
        .sampleMean(246.0).calc();
    assertNotNull(result);
    assertThat(result.limitInf(), is(238.19777418186663));
    assertThat(result.limitSup(), is(253.80222581813337));
    assertThat(result.sampleError(), is(8));
  }

  //    @Test
  //    public void calculateInfPopMeanKnowedSD() throws Exception {
  //        MeanInferenceCalc result =
  //                new MeanInferenceCalc()
  //                        .alpha(0.1)
  //                        .standardDeviation(15.0)
  //                        .sampleSize(10)
  //                        .size(900)
  //                        .sampleMean(246.0)
  //                        .calc();
  //        assertNotNull(result);
  //        assertThat(result.limitInf(), is(238.19777418186663));
  //        assertThat(result.limitSup(), is(253.80222581813337));
  //        assertThat(result.sampleError(), is(8));
  //    }


  // sigma, X, error , alpha -> n
  @Test
  public void calculateSampleSizeKnowedSD() throws Exception {
    MeanInferenceCalc result = new MeanInferenceCalc().alpha(0.1).standardDeviation(15.0).sampleMean(246.0)
        .sampleError(5).calc();
    assertNotNull(result);
    assertThat(result.sampleSize(), is(25));
  }

  // poblacion finita
  @Test
  public void calculateFinitePopulationSampleSizeKnowedSD() throws Exception {
    MeanInferenceCalc result = new MeanInferenceCalc().alpha(0.1).standardDeviation(15.0).sampleMean(246.0)
        .sampleError(5).size(900).calc();
    assertNotNull(result);
    assertThat(result.sampleSize(), is(25));
  }

  @Test
  public void calculateMeanUnknowedSD() throws Exception {
    MeanInferenceCalc result = new MeanInferenceCalc().alpha(0.05).sampleStandardDeviation(1.7935).sampleSize(4)
        .sampleMean(17.35).calc();
    assertNotNull(result);
    assertThat(result.limitInf(), is(14.49614127611906));
    assertThat(result.limitSup(), is(20.203858723880945));
  }

  @Test
  public void calculateSampleSizeUnknowedSD() throws Exception {
    MeanInferenceCalc result = new MeanInferenceCalc()
      .alpha(0.05).sampleStandardDeviation(1.7935).sampleMean(17.35)
        .sampleError(1).calc();
    assertNotNull(result);
    assertThat(result.sampleSize(), is(15));
  }

  @Test
  public void calculateLimUnknowedSD() throws Exception {
    MeanInferenceCalc result = new MeanInferenceCalc()
      .alpha(0.05).sampleStandardDeviation(1.7935).sampleMean(17.35)
      .sampleSize(15).calc();
    assertNotNull(result);
    assertThat(result.sampleError(), is(1));
  }

}