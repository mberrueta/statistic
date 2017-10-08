package com.uade.matt.statistic.models;

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
        BinomialDistributionCalc result =
                new BinomialDistributionCalc()
                        .n(20)
                        .r(19)
                        .p(0.9)
                        .calculatePx();
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

        List<BinomialDistributionCalc.Dto> list = result.generateSuccessIndex();

        assertThat(list.get(0).id, is(12));
        assertThat(list.get(0).value, CoreMatchers.is(BigDecimal.valueOf(0.00035578)));
        assertThat(list.get(1).value, CoreMatchers.is(BigDecimal.valueOf(0.00197046)));
        assertThat(list.get(2).value, CoreMatchers.is(BigDecimal.valueOf(0.00886705)));
        assertThat(list.get(3).value, CoreMatchers.is(BigDecimal.valueOf(0.03192137)));
        assertThat(list.get(4).value, CoreMatchers.is(BigDecimal.valueOf(0.08977883)));
        assertThat(list.get(5).value, CoreMatchers.is(BigDecimal.valueOf(0.19011988)));
        assertThat(list.get(6).value, CoreMatchers.is(BigDecimal.valueOf(0.28517981)));
        assertThat(list.get(7).value, CoreMatchers.is(BigDecimal.valueOf(0.27017035)));
        assertThat(list.get(8).id, is(20));
        assertThat(list.get(8).value, CoreMatchers.is(BigDecimal.valueOf(0.12157666)));
    }


    @Test
    public void calculateInvPx() throws Exception {
        BinomialDistributionCalc result = new BinomialDistributionCalc()
                .n(20)
                .f(0.87842)
                .p(0.9)
                .calculatePx();

//        new BinomialDistributionCalc(20, 0.9, 0.87842).calculatePx();
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

        List<BinomialDistributionCalc.Dto> list = result.generateSuccessIndex();

        assertThat(list.get(0).id, is(12));
        assertThat(list.get(0).value, CoreMatchers.is(BigDecimal.valueOf(0.00035578)));
        assertThat(list.get(1).value, CoreMatchers.is(BigDecimal.valueOf(0.00197046)));
        assertThat(list.get(2).value, CoreMatchers.is(BigDecimal.valueOf(0.00886705)));
        assertThat(list.get(3).value, CoreMatchers.is(BigDecimal.valueOf(0.03192137)));
        assertThat(list.get(4).value, CoreMatchers.is(BigDecimal.valueOf(0.08977883)));
        assertThat(list.get(5).value, CoreMatchers.is(BigDecimal.valueOf(0.19011988)));
        assertThat(list.get(6).value, CoreMatchers.is(BigDecimal.valueOf(0.28517981)));
        assertThat(list.get(7).value, CoreMatchers.is(BigDecimal.valueOf(0.27017035)));
        assertThat(list.get(8).id, is(20));
        assertThat(list.get(8).value, CoreMatchers.is(BigDecimal.valueOf(0.12157666)));

    }

}