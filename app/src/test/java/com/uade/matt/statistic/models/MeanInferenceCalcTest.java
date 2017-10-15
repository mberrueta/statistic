package com.uade.matt.statistic.models;

import com.uade.matt.statistic.utils.Helper;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MeanInferenceCalcTest {
    @Test
    public void calculatePx() throws Exception {
        MeanInferenceCalc result =
                new MeanInferenceCalc()
                        .alpha(0.1)
                        .standardDeviation(15.0)
                        .sampleSize(10)
                        .sampleMean(246.0)
                        .calc();
        assertNotNull(result);
        assertThat(result.limitInf(), is(238.19777418186663));
        assertThat(result.limitSup(), is(253.80222581813337));
        assertThat(result.sampleError(), is(8));
    }


    @Test
    public void calculateInvPx() throws Exception {
        MeanInferenceCalc result =
                new MeanInferenceCalc()
                        .alpha(0.1)
                        .standardDeviation(15.0)
                        .sampleMean(246.0)
                        .sampleError(5)
                        .calc();
        assertNotNull(result);
        assertThat(result.sampleSize(), is(25));
    }
}