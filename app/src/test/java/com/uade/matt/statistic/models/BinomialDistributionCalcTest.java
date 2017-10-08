package com.uade.matt.statistic.models;

import android.util.Log;
import android.widget.EditText;

import com.uade.matt.statistic.R;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static android.R.id.list;
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

        BinomialDistributionCalc.Dto[] list = result.generateSuccessIndex();

        assertThat(list[0].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[1].value, CoreMatchers.is(BigDecimal.valueOf(0.0d)));
        assertThat(list[2].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[3].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[4].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[5].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[6].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[7].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[8].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[9].value, CoreMatchers.is(BigDecimal.valueOf(0.000001)));
        assertThat(list[10].value, CoreMatchers.is(BigDecimal.valueOf(0.0000070)));
        assertThat(list[11].value, CoreMatchers.is(BigDecimal.valueOf(0.000053)));
        assertThat(list[12].value, CoreMatchers.is(BigDecimal.valueOf(0.000356)));
        assertThat(list[13].value, CoreMatchers.is(BigDecimal.valueOf(0.001971)));
        assertThat(list[14].value, CoreMatchers.is(BigDecimal.valueOf(0.008868)));
        assertThat(list[15].value, CoreMatchers.is(BigDecimal.valueOf(0.031922)));
        assertThat(list[16].value, CoreMatchers.is(BigDecimal.valueOf(0.089779)));
        assertThat(list[17].value, CoreMatchers.is(BigDecimal.valueOf(0.19012)));
        assertThat(list[18].value, CoreMatchers.is(BigDecimal.valueOf(0.28518)));
        assertThat(list[19].value, CoreMatchers.is(BigDecimal.valueOf(0.270171)));
        assertThat(list[20].value, CoreMatchers.is(BigDecimal.valueOf(0.121577)));

    }


    @Test
    public void calculateInvPx() throws Exception {
        BinomialDistributionCalc result = new BinomialDistributionCalc(20, 0.9, 0.87842).calculatePx();
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

        List<BinomialDistributionCalc.Dto> list = result.generateSuccessIndex();

        assertThat(list[0].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[1].value, CoreMatchers.is(BigDecimal.valueOf(0.0d)));
        assertThat(list[2].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[3].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[4].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[5].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[6].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[7].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[8].value, CoreMatchers.is(BigDecimal.valueOf(0.0)));
        assertThat(list[9].value, CoreMatchers.is(BigDecimal.valueOf(0.000001)));
        assertThat(list[10].value, CoreMatchers.is(BigDecimal.valueOf(0.0000070)));
        assertThat(list[11].value, CoreMatchers.is(BigDecimal.valueOf(0.000053)));
        assertThat(list[12].value, CoreMatchers.is(BigDecimal.valueOf(0.000356)));
        assertThat(list[13].value, CoreMatchers.is(BigDecimal.valueOf(0.001971)));
        assertThat(list[14].value, CoreMatchers.is(BigDecimal.valueOf(0.008868)));
        assertThat(list[15].value, CoreMatchers.is(BigDecimal.valueOf(0.031922)));
        assertThat(list[16].value, CoreMatchers.is(BigDecimal.valueOf(0.089779)));
        assertThat(list[17].value, CoreMatchers.is(BigDecimal.valueOf(0.19012)));
        assertThat(list[18].value, CoreMatchers.is(BigDecimal.valueOf(0.28518)));
        assertThat(list[19].value, CoreMatchers.is(BigDecimal.valueOf(0.270171)));
        assertThat(list[20].value, CoreMatchers.is(BigDecimal.valueOf(0.121577)));

    }

}