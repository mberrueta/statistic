package com.uade.matt.statistic.models;

import com.uade.matt.statistic.utils.Helper;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class BinomialDistributionCalcTest {
  @Test
  public void calculatePx() throws Exception {
    BinomialDistributionCalc result = new BinomialDistributionCalc().n(20).r(19).p(0.9).calculatePx();
    assertNotNull(result);
    assertThat(result.n(), is(20));
    assertThat(result.r(), is(19));
    assertThat(result.p(), is(0.9));
    assertThat(result.f(), is(0.8784233454094307));
    assertThat(result.g(), is(0.3917469981251689));
    assertThat(result.pbin(), is(0.27017034353459846));
    assertThat(result.mean(), is(18.0000));
    assertThat(result.variance(), is(1.80000));
    assertThat(result.standardDeviation(), is(1.3416407864998738));

    List<Helper.Dto> list = result.generateSuccessIndex();

    assertThat(list.get(0).id, is(12.0));
    assertThat(list.get(0).value, CoreMatchers.is(BigDecimal.valueOf(0.000356).doubleValue()));
    assertThat(list.get(1).value, CoreMatchers.is(BigDecimal.valueOf(0.001971).doubleValue()));
    assertThat(list.get(2).value, CoreMatchers.is(BigDecimal.valueOf(0.008868).doubleValue()));
    assertThat(list.get(3).value, CoreMatchers.is(BigDecimal.valueOf(0.031922).doubleValue()));
    assertThat(list.get(4).value, CoreMatchers.is(BigDecimal.valueOf(0.089779).doubleValue()));
    assertThat(list.get(5).value, CoreMatchers.is(BigDecimal.valueOf(0.190120).doubleValue()));
    assertThat(list.get(6).value, CoreMatchers.is(BigDecimal.valueOf(0.285180).doubleValue()));
    assertThat(list.get(7).value, CoreMatchers.is(BigDecimal.valueOf(0.270171).doubleValue()));
    assertThat(list.get(8).id, is(20.0));
    assertThat(list.get(8).value, CoreMatchers.is(BigDecimal.valueOf(0.121577).doubleValue()));
  }

  @Test
  public void calculateInvPx() throws Exception {
    BinomialDistributionCalc result = new BinomialDistributionCalc().n(20).f(0.87842).p(0.9).calculatePx();

    assertNotNull(result);
    assertThat(result.resultMessage(), is("Range values: \nP(r<18) = 0.608253 \nP(r<19) = 0.878423"));
  }

  @Test
  public void calculateP() throws Exception {
    BinomialDistributionCalc result = new BinomialDistributionCalc().n(30).f(0.05).r(3).calculatePx();

    assertNotNull(result);
    assertThat(result.p(), is(0.23859791924039372));
  }

  @Test
  public void calculateP2() throws Exception {
    BinomialDistributionCalc result = new BinomialDistributionCalc().n(30).f(0.95).r(3).calculatePx();

    assertNotNull(result);
    assertThat(result.p(), is(0.046854917762642306));
  }

  @Test
  public void calculateP3() throws Exception {
    BinomialDistributionCalc result = new BinomialDistributionCalc().n(30).f(0.95).r(2).calculatePx();

    assertNotNull(result);
    assertThat(result.p(), is(0.027815584949474188));
  }
}