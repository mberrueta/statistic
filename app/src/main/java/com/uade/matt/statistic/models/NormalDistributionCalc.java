package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

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

  // v.a.
  @Getter
  @Setter
  private Double x;

  @Getter
  @Setter
  private Double f, g;
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

    if (isNullorZero(mean))
      mean = 0.0;
    if (isNullorZero(standardDeviation))
      standardDeviation = 1.0;

    dist = new NormalDistribution(mean, standardDeviation);

    if (isNullorZero(x)) {
      if (isNullorZero(f))
        f = 1 - g;
      x = dist.inverseCumulativeProbability(f);
    } else {
      f = dist.cumulativeProbability(x);
    }


    variance = Math.sqrt(standardDeviation);
    skewness = 0.0;
    kurtosis = 3.0;
    coefficientVariation = 0.0;

//        f = dist.cumulativeProbability(r);
    g = 1 - f;
    Log.i(BinomialDistributionCalc.class.toString(), "Post: " + this.toString());


    return this;
  }

  public String toFullString() {
    return "";
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


    double firstValue = -5 * standardDeviation;

    for (double i = firstValue; i <= -1 * firstValue; i += 0.05) {
      double value = round(dist.cumulativeProbability(i));
      if (i > mean)
        value = 1 - value;

      if (value > 0.001) {
        temp.add(new Helper.Dto(round(i), value, false));
      }
    }

    return temp;
  }
}
