package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.R.id.n;
import static com.uade.matt.statistic.R.id.t;
import static com.uade.matt.statistic.utils.Helper.round;

@Accessors(chain = true, fluent = true)
public class PoissonDistributionCalc extends DistributionCalc {
    private PoissonDistribution dist;
//
//    Distribución de probabilidad discreta que expresa, a partir de una frecuencia de ocurrencia media, la probabilidad de que ocurra un determinado número de eventos durante cierto período de tiempo. Concretamente, se especializa en la probabilidad de ocurrencia de sucesos con probabilidades muy pequeñas, o sucesos "raros".
//
//    <b>Variable aleatoria:</b>\n
//        `r` <i>(numero de acontecimientos)</i> \n
//    Dominio : ℕ .\n
//    <b>Parámetros del Modelo:</b> \n
//             `𝜆` <i> (`lambda` tasa de falla)</i>\n
//    Dominio : ℝ/ 0 ≤ 𝜆 ≤ ∞ .\n
//             `t` <i>(periodo de tiempo de la frecuencia)</i>\n
//    Dominio : ℝ/ 0 ≤ t ≤ ∞ .\n

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


        mean = variance = n = frequency * t;
        dist = new PoissonDistribution(n);


//        if (isNullorZero(r)) {
//            if(!isNullorZero(f))
//            {
//                Integer r2 = dist.inverseCumulativeProbability(f);
//                Integer r1 = r2 - 1;
//                Double f1 = dist.cumulativeProbability(r1);
//                Double f2 = dist.cumulativeProbability(r2);
//                resultMessage = String.format("Range values: %n" +
//                        "P(r<%d) = %f %n" +
//                        "P(r<%d) = %f", r1, f1, r2, f2);
//                return this;
//            }
//            else
//            {
//                Integer r1 = dist.inverseCumulativeProbability(1 - g);
//                Integer r2 = r1 + 1;
//                Double g1 = dist.cumulativeProbability(r1 - 1, n);
//                Double g2 = dist.cumulativeProbability(r2 - 1, n);
//                resultMessage = String.format("Range values: %n" +
//                        "P(r>%d) = %f %n" +
//                        "P(r>%d) = %f", r1, g1, r2, g2);
//                return this;
//            }
//        }


        p = dist.probability(r);



        standardDeviation = variance * variance;
        standardDeviation = round(standardDeviation);
        skewness = 1 / standardDeviation;
        kurtosis = 3 + 1 / variance;

        coefficientVariation = round(variance / mean);

        f = dist.cumulativeProbability(r);
        g = r > 0 ? dist.cumulativeProbability(r - 1) : 1.0;
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

        for (int i = 0; i <= n; i++) {
            double value = round(dist.probability(i));
            if (value > 0.0001) {
                temp.add(new Helper.Dto(round(i), value, false));
            }
        }
        return temp;
    }
}
