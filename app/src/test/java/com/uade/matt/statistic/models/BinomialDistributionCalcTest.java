package com.uade.matt.statistic.models;

import android.util.Log;
import android.widget.EditText;

import com.uade.matt.statistic.R;

import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class BinomialDistributionCalcTest {
    @Test
    public void calculatePx() throws Exception {
        BinomialDistributionCalc result = new BinomialDistributionCalc(20, 19, 0.9).calculatePx();
        assertNotNull(result);
        assertThat(result.getN(), is(20));
        assertThat(result.getR(), is(19));
        assertThat(result.getP(), is(0.9));
        assertThat(result.getF(), is(0.8784233454094307));
        assertThat(result.getG(), is(0.3917469981251689));
        assertThat(result.getPbin(), is(0.27017034353459846));
        assertThat(result.getMean(), is(18.0000));
        assertThat(result.getVariance(), is(1.80000));
        assertThat(result.getStandardDeviation(), is(1.3416407864998738));
    }

}