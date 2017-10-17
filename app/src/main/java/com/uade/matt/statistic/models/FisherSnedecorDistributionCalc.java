package com.uade.matt.statistic.models;

import android.util.Log;

import org.apache.commons.math3.distribution.FDistribution;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;
import static com.uade.matt.statistic.utils.Helper.round;

@Accessors(chain = true, fluent = true)
public class FisherSnedecorDistributionCalc extends DistributionCalc {
  private FDistribution dist;

  // params
  @Getter
  @Setter
  private Double numeratorDegreesOfFreedom, denominatorDegreesOfFreedom, x;

  @Getter
  @Setter
  private Double f, p, g;

  @Getter
  @Setter
  private Double mean, variance, standardDeviation;

  @Getter
  @Setter
  private String resultMessage;

  public FisherSnedecorDistributionCalc calculatePx() {
    Log.i(FisherSnedecorDistributionCalc.class.toString(), "Pre: " + this.toString());

    dist = new FDistribution(numeratorDegreesOfFreedom, denominatorDegreesOfFreedom);

    if (isNullorZero(x)) {
      if (isNullorZero(f))
        f = 1 - g;
      x = round(dist.inverseCumulativeProbability(f));
    } else {
      f = dist.cumulativeProbability(x);
    }
    g = 1 - f;
    mean = dist.getNumericalMean();
    variance = dist.getNumericalVariance();
    standardDeviation = Math.sqrt(variance);

    Log.i(FisherSnedecorDistributionCalc.class.toString(), "Post: " + this.toString());
    return this;
  }

  @Override
  public String toString() {
    return "variance=" + variance;
  }
}
