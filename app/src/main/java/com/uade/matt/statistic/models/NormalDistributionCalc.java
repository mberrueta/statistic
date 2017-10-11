package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;
import static com.uade.matt.statistic.utils.Helper.round;

@Accessors(chain = true, fluent = true)
public class NormalDistributionCalc extends DistributionCalc {
    private NormalDistribution dist;
    // trials
    @Getter
    @Setter
    private Integer n;
    // observed
    @Getter
    @Setter
    private Integer r;
    // probability of success
    @Getter
    @Setter
    private Double p;

    @Getter
    @Setter
    private Integer failureR, possibleCombinations;
    @Getter
    @Setter
    private Double f, g, pbin, failureP;
    // media, mediana, moda, varianza, desv standard, asimetria
    @Getter
    @Setter
    private Double mean, median, mode, variance, standardDeviation, skewness, kurtosis, coefficientVariation;

    @Getter
    @Setter
    private Double supportLowerBound, supportUpperBound;

    @Getter
    @Setter
    private String resultMessage;

    public NormalDistributionCalc calculatePx() {
        Log.i(NormalDistributionCalc.class.toString(), "Pre: " + this.toFullString());



        return this;
    }

    public String toFullString() {
        return "";
    }


    @Override
    public String toString() {
        return  "";
    }

    public List<Helper.Dto> generateSuccessIndex() {
        List<Helper.Dto> temp = new ArrayList<>();

        return temp;
    }
}
