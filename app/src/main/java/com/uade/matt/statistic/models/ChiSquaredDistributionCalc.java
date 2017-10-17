package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;
import static com.uade.matt.statistic.utils.Helper.round;

@Accessors(chain = true, fluent = true)
public class ChiSquaredDistributionCalc extends DistributionCalc {
  private ChiSquaredDistribution dist;

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

  public ChiSquaredDistributionCalc calculatePx() {
    Log.i(ChiSquaredDistributionCalc.class.toString(), "Pre: " + this.toFullString());


    dist = new ChiSquaredDistribution(degreesOfFreedom);

    if (isNullorZero(x)) {
      if (isNullorZero(f))
        f = 1 - g;
      x = round(dist.inverseCumulativeProbability(f));
    } else {
      f = round(dist.cumulativeProbability(x));
    }

    mean = degreesOfFreedom;
    variance = 2 * degreesOfFreedom;
    standardDeviation = Math.sqrt(variance);
//
//        skewness = 0.0;
//        if (degreesOfFreedom > 4)
//            kurtosis = 6 / (degreesOfFreedom - 4);
//        else
//            kurtosis = 0.0;
//
//        coefficientVariation = 0.0;

//        f = dist.cumulativeProbability(r);
    g = 1 - f;
    Log.i(ChiSquaredDistributionCalc.class.toString(), "Post: " + this.toString());


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


    for (double i = 0; i <= 3 * degreesOfFreedom; i++) {
      double value = round(dist.probability(i));
      if (value > 0.00001) {
        temp.add(new Helper.Dto(round(i), value, false));
      }
    }

    return temp;
  }
}
