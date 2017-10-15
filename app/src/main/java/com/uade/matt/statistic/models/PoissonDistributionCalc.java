package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;
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

        if(isNullorZero(t))
            t = 1.0;

        mean = variance = n = round(frequency * t);
        dist = new PoissonDistribution(n);

        if (isNullorZero(r)) {
            if (!isNullorZero(f)) {
                Integer r2 = dist.inverseCumulativeProbability(f);
                Integer r1 = r2 - 1;
                Double f1 = dist.cumulativeProbability(r1);
                Double f2 = dist.cumulativeProbability(r2);
                resultMessage = String.format("Range values: %n" +
                        "P(r<%d) = %f %n" +
                        "P(r<%d) = %f", r1, f1, r2, f2);
                return this;
            } else {
                Integer r1 = dist.inverseCumulativeProbability(1 - g);
                Integer r2 = r1 + 1;
                Double g1 = round(dist.probability(r1) + (1 - dist.cumulativeProbability(r1)));
                Double g2 = round(dist.probability(r2) + (1 - dist.cumulativeProbability(r2)));
                resultMessage = String.format("Range values: %n" +
                        "P(r>%d) = %f %n" +
                        "P(r>%d) = %f", r1, g1, r2, g2);
                return this;
            }
        }


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
        return "𝜎² = " + variance + "\n" +
                "As = " + skewness + "\n" +
                "Kurtosis = " + kurtosis + "\n" +
                "CV = " + coefficientVariation + "\n";
    }

    public List<Helper.Dto> generateSuccessIndex() {
        List<Helper.Dto> temp = new ArrayList<>();
        double value = 9999;
        int i = 0;
        while (i < n || value > 0.0000001) {
            value = round(dist.probability(i));
            if (value > 0.0001) {
                temp.add(new Helper.Dto(round(i), value, false));
            }
            i++;
        }
        return temp;
    }
}
