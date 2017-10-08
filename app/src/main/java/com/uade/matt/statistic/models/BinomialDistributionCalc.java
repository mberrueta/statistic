package com.uade.matt.statistic.models;

import android.util.Log;

import org.apache.commons.math3.distribution.BinomialDistribution;

import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import static android.R.attr.src;
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

    @Getter @Setter private Double supportLowerBound, supportUpperBound;

    public BinomialDistributionCalc(int n, int r, double p) {
        this.n = n;
        this.r = r;
        this.p = p;
    }

    public BinomialDistributionCalc(int n, double p, double f) {
        this.n = n;
        this.f = f;
        this.p = p;
    }

    public BinomialDistributionCalc calculatePx() {
        Log.i(BinomialDistributionCalc.class.toString(), "Pre: " + this.toString());


        if (n == 0) {
            return this;
        }


        dist = new BinomialDistribution(n, p);


        if(r == null){
            r = dist.inverseCumulativeProbability(f);
        }


        pbin = dist.probability(r);

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

        f = dist.cumulativeProbability(r);
        g = r > 0 ? dist.cumulativeProbability(r - 1, n) : 1.0;
        Log.i(BinomialDistributionCalc.class.toString(), "Post: " + this.toString());

        return this;
    }

    @Override
    public String toString() {
        return "Binomial:\n" +

                "Failure r = " + failureR + "\n" +
                "Failure p = " + failureP + "\n" +
                "𝜇 = " + mean + "\n" +
                "Median = " + median + "\n" +
//                "Mode = " + mode + "\n" +
                "𝜎² = " + variance + "\n" +
                "𝜎 = " + standardDeviation + "\n" +
                "As = " + skewness + "\n" +
                "Kurtosis = " + kurtosis + "\n" +
                "CV = " + coefficientVariation + "\n";
    }

    public Double round(Double number) {
        DecimalFormat df = new DecimalFormat("#.######");
                df.setRoundingMode(RoundingMode.CEILING);
        return Double
                .parseDouble(df.format(number));
    }

    public List<Dto> generateSuccessIndex(){
//        Dto[] temp = new Dto[n+1];
        List<Dto> temp = new ArrayList<>();

        for (int i = 0; i <= n; i ++ ){
            double value = round(dist.probability(i));
            if (value > 0) {
                temp.add(new Dto(i, value, false));
            }
        }
        return temp;
    }

    public class Dto{
        public Integer id;
        public BigDecimal value;
        public Boolean isMax;

        public Dto(Integer id, Double value, Boolean isMax) {
            this.id = id;
            this.value = BigDecimal.valueOf(value);
            this.isMax = isMax;
        }
    }

}
