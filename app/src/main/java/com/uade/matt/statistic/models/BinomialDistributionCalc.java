package com.uade.matt.statistic.models;

import android.util.Log;

import org.apache.commons.math3.distribution.BinomialDistribution;

import java.text.DecimalFormat;

import lombok.Getter;
import lombok.Setter;

import static com.uade.matt.statistic.R.id.distribution;
import static com.uade.matt.statistic.R.id.p;

public class BinomialDistributionCalc {
    private BinomialDistribution dist;
    // trials
    @Getter @Setter private Integer n;
    // observed
    @Getter @Setter private Integer r;
    // probability of success
    @Getter @Setter private Double p;

    @Getter @Setter private Integer failureR, possibleCombinations;
    @Getter @Setter private Double f, g, pbin, failureP;
    // media, mediana, moda, varianza, desv standard, asimetria
    @Getter @Setter private Double mean, median, mode, variance, standardDeviation, skewness, kurtosis, coefficientVariation;

    public BinomialDistributionCalc(int n, int r, double p) {
        this.n = n;
        this.r = r;
        this.p = p;
    }

    public BinomialDistributionCalc calculatePx() {
        Log.i(BinomialDistributionCalc.class.toString(), "Pre: " + this.toString());


        if (n == 0) {
            return this;
        }


        BinomialDistribution calc = new BinomialDistribution(n, p);

        f = calc.cumulativeProbability(r);
        g = r > 0 ? calc.cumulativeProbability(r - 1, n) : 1.0;

        pbin = calc.probability(r);
        failureP = 1 - p;
        failureP = round(failureP);
        failureR = n - r;
        // mode (MAX PROB ?)
        mean = n * p;
        median = n * p;


        variance =  n * p * (1 - p);
        // needed to round the number
        variance = round(variance);
        standardDeviation = Math.sqrt(variance);
        skewness = (1 - 2 * p) / standardDeviation ;
        kurtosis = 3 + ((1 - 6 * p * ( 1 - p )) / standardDeviation) ;
        coefficientVariation = standardDeviation / mean;

        Log.i(BinomialDistributionCalc.class.toString(), "Post: " + this.toString());

        return this;
    }

    @Override
    public String toString() {
        return "Binomial:\n" +
                "failure r = " + failureR + "\n" +
                "failure p = " + failureP + "\n" +
                "𝜇 = " + mean + "\n" +
                "median = " + median + "\n" +
                "mode = " + mode + "\n" +
                "𝜎² = " + variance + "\n" +
                "𝜎 = " + standardDeviation + "\n" +
                "As = " + skewness + "\n" +
                "Kurtosis = " + kurtosis + "\n" +
                "CV = " + coefficientVariation + "\n";
    }

    public Double round(Double number) {
        return Double
                .parseDouble(
                        new DecimalFormat("#.###############")
                                .format(number));
    }

}
