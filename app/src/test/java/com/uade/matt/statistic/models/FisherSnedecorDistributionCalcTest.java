package com.uade.matt.statistic.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class FisherSnedecorDistributionCalcTest {
  @Test
  public void calculateP() throws Exception {
    FisherSnedecorDistributionCalc result = new FisherSnedecorDistributionCalc().numeratorDegreesOfFreedom(20.0)
        .denominatorDegreesOfFreedom(40.0).x(1.24).calculatePx();
    assertNotNull(result);
    assertThat(result.g(), is(0.2742668317233188));
    assertThat(result.f(), is(0.7257331682766812));
    assertThat(result.mean(), is(1.0526315789473684));
    assertThat(result.variance(), is(0.17851646660510925));
    assertThat(result.standardDeviation(), is(0.4225120904839402));
  }

  @Test
  public void calculateP2() throws Exception {
    FisherSnedecorDistributionCalc result = new FisherSnedecorDistributionCalc().numeratorDegreesOfFreedom(54.0)
        .denominatorDegreesOfFreedom(6.0).f(0.95).calculatePx();
    assertNotNull(result);
    assertThat(result.x(), is(3.747516));
    assertThat(result.mean(), is(1.50));
    assertThat(result.variance(), is(2.4166666666666665));
    assertThat(result.standardDeviation(), is(1.5545631755148024));
  }

  @Test
  public void calculateX() throws Exception {
    FisherSnedecorDistributionCalc result = new FisherSnedecorDistributionCalc().numeratorDegreesOfFreedom(20.0)
        .denominatorDegreesOfFreedom(40.0).f(0.7257331682766812).calculatePx();
    assertNotNull(result);
    assertThat(result.g(), is(0.2742668317233188));
    assertThat(result.x(), is(1.240001));
    assertThat(result.mean(), is(1.0526315789473684));
    assertThat(result.variance(), is(0.17851646660510925));
    assertThat(result.standardDeviation(), is(0.4225120904839402));
  }

  @Test
  public void calculateX2() throws Exception {
    FisherSnedecorDistributionCalc result = new FisherSnedecorDistributionCalc().numeratorDegreesOfFreedom(20.0)
        .denominatorDegreesOfFreedom(40.0).g(0.2742668317233188).calculatePx();
    assertNotNull(result);
    assertThat(result.f(), is(0.7257331682766812));
    assertThat(result.x(), is(1.240001));
    assertThat(result.mean(), is(1.0526315789473684));
    assertThat(result.variance(), is(0.17851646660510925));
    assertThat(result.standardDeviation(), is(0.4225120904839402));
  }
}