package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import org.apache.commons.math3.distribution.TDistribution;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;
import static com.uade.matt.statistic.utils.Helper.round;

@Accessors(chain = true, fluent = true)
public class TDistributionCalc extends DistributionCalc {
  private TDistribution dist;

  // v.a.
  @Getter
  @Setter
  private Double degreesOfFreedom, x;

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

  public TDistributionCalc calculatePx() {
    Log.i(TDistributionCalc.class.toString(), "Pre: " + this.toFullString());

    dist = new TDistribution(degreesOfFreedom);

    if (isNullorZero(x)) {
      if (isNullorZero(f))
        f = 1 - g;
      x = dist.inverseCumulativeProbability(f);
    } else {
      f = dist.cumulativeProbability(x);
    }

    mean = mode = median = 0.0;
    if (degreesOfFreedom > 2)
      variance = degreesOfFreedom / (degreesOfFreedom - 2);
    else
      variance = 0.0;

    standardDeviation = Math.sqrt(variance);

    skewness = 0.0;
    if (degreesOfFreedom > 4)
      kurtosis = 6 / (degreesOfFreedom - 4);
    else
      kurtosis = 0.0;

    coefficientVariation = 0.0;

    //        f = dist.cumulativeProbability(r);
    g = 1 - f;
    Log.i(TDistributionCalc.class.toString(), "Post: " + this.toString());

    return this;
  }

  public String toFullString() {
    return "";
  }

  @Override
  public String toString() {
    return "𝜎² = " + variance + "\n" + "As = " + skewness + "\n" + "Kurtosis = " + kurtosis + "\n" + "CV = "
        + coefficientVariation + "\n";
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
