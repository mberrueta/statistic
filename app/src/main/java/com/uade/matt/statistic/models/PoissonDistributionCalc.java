package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.round;

@Accessors(chain = true, fluent = true)
public class PoissonDistributionCalc extends DistributionCalc {
    private PoissonDistribution dist;
    // events
    @Getter
    @Setter
    private Integer r;
    @Getter
    @Setter
    private Double f, p, g, frequency, t, n;
    // media, mediana, moda, varianza, desv standard, asimetria
    @Getter
    @Setter
    private Double mean, median, mode, variance, standardDeviation, skewness, kurtosis, coefficientVariation;

    @Getter
    @Setter
    private String resultMessage;

    public PoissonDistributionCalc calculatePx() {
        Log.i(PoissonDistributionCalc.class.toString(), "Pre: " + this.toString());

        mean = variance = n = round(frequency * t);
        dist = new PoissonDistribution(n);

        p = round(dist.probability(r));
        f = round(dist.cumulativeProbability(r));
        g = round(p + (1 - f));

        standardDeviation = round(Math.sqrt(variance));
        skewness = round(1 / standardDeviation);
        kurtosis = round(3 + 1 / variance);
        coefficientVariation = round(variance / mean);

        Log.i(PoissonDistributionCalc.class.toString(), "Post: " + this.toString());
        return this;
    }


    @Override
    public String toString() {
        return  "𝜎² = " + variance + "\n" +
                "As = " + skewness + "\n" +
                "Kurtosis = " + kurtosis + "\n" +
                "CV = " + coefficientVariation + "\n";
    }

    public List<Helper.Dto> generateSuccessIndex() {
        List<Helper.Dto> temp = new ArrayList<>();
        double value = 9999;
        int i = 0;
        while (i < n || value > 0.0000001){
            value = round(dist.probability(i));
            if (value > 0.0001) {
                temp.add(new Helper.Dto(round(i), value, false));
            }
            i++;
        }
        return temp;
    }
}
