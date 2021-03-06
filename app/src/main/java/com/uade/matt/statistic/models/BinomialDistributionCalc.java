package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import org.apache.commons.math3.distribution.BinomialDistribution;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;
import static com.uade.matt.statistic.utils.Helper.round;

@Accessors(chain = true, fluent = true)
public class BinomialDistributionCalc extends DistributionCalc {
  private BinomialDistribution dist;
  // trials
  @Getter
  @Setter
  private Integer n, r;
  // probability of success
  @Getter
  @Setter
  private Double p;

  @Getter
  @Setter
  private Integer possibleCombinations;
  @Getter
  @Setter
  private Double f, g, pbin;
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

  public BinomialDistributionCalc calculatePx() {
    Log.i(BinomialDistributionCalc.class.toString(), "Pre: " + this.toFullString());

    if (isNullorZero(p)) {
      Double fisherX = new FisherSnedecorDistributionCalc().numeratorDegreesOfFreedom((double) 2 * r + 2)
          .denominatorDegreesOfFreedom((double) 2 * n - 2 * r).f(1 - f).calculatePx().x();

      p = 1 / (1 + (((double) (n - r) / ((r + 1) * fisherX))));
    }

    if (isNullorZero(n)) {
      if (!isNullorZero(mean))
        n = (int) (mean / p);
      else
        n = (int) (standardDeviation / (p * (1 - p)));
    }

    dist = new BinomialDistribution(n, p);

    if (isNullorZero(r)) {
      if (!isNullorZero(f)) {
        Integer r2 = dist.inverseCumulativeProbability(f);
        r = r2 - 1;
        f = dist.cumulativeProbability(r);
        Double f2 = dist.cumulativeProbability(r2);
        resultMessage = String.format("Range values: %n" + "P(r<%d) = %f %n" + "P(r<%d) = %f", r, f, r2, f2);
        return this;
      } else {
        r = dist.inverseCumulativeProbability(1 - g);
        Integer r2 = r + 1;
        g = dist.cumulativeProbability(r - 1, n);
        Double g2 = dist.cumulativeProbability(r2 - 1, n);
        resultMessage = String.format("Range values: %n" + "P(r>%d) = %f %n" + "P(r>%d) = %f", r, g, r2, g2);
        return this;
      }
    }

    pbin = dist.probability(r);

    // mode (MAX PROB ?)
    mean = n * p;
    median = n * p;

    variance = n * p * (1 - p);
    variance = round(variance);
    standardDeviation = Math.sqrt(variance);
    skewness = (1 - 2 * p) / variance;
    kurtosis = 3 + ((1 - 6 * p * (1 - p)) / variance);
    coefficientVariation = round(variance / mean);

    f = dist.cumulativeProbability(r);
    g = r > 0 ? dist.cumulativeProbability(r - 1, n) : 1.0;
    Log.i(BinomialDistributionCalc.class.toString(), "Post: " + this.toString());

    return this;
  }

  public String toFullString() {
    return "BinomialDistributionCalc{" + "dist=" + dist + ", n=" + n + ", r=" + r + ", p=" + p
        + ", possibleCombinations=" + possibleCombinations + ", f=" + f + ", g=" + g + ", pbin=" + pbin + ", mean="
        + mean + ", median=" + median + ", mode=" + mode + ", variance=" + variance + ", standardDeviation="
        + standardDeviation + ", skewness=" + skewness + ", kurtosis=" + kurtosis + ", coefficientVariation="
        + coefficientVariation + ", supportLowerBound=" + supportLowerBound + ", supportUpperBound=" + supportUpperBound
        + '}';
  }

  @Override
  public String toString() {
    return "Median = " + median + "\n" + "𝜎² = " + variance + "\n" + "As = " + skewness + "\n" + "Kurtosis = "
        + kurtosis + "\n" + "CV = " + coefficientVariation + "\n";
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
