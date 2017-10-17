package com.uade.matt.statistic.models;

import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;

@Accessors(chain = true, fluent = true)
public class VarianceInferenceCalc extends InferenceCalc {
  @Getter
  @Setter
  private Integer sampleSize, degreesOfFreedom; //n , n-1
  @Getter
  @Setter
  private Double sampleStandardDeviation; //S

  //limits
  @Getter
  @Setter
  private Double limitInfVariance, limitSupVariance, limitRelationshipVariance,
    limitInfStandardDeviation, limitSupStandardDeviation, limitRelationshipStandardDeviation,
    alpha;
  //calcs
  @Getter
  @Setter
  private Integer sampleErrorVariance, sampleErrorStandardDeviation; //R
  @Getter
  @Setter
  private Double garciaA;
  @Getter
  @Setter
  private String resultMessage;

  public VarianceInferenceCalc calc() {
    Log.i(VarianceInferenceCalc.class.toString(), "Pre: " + this.toString());

    if (isNullorZero(sampleSize)) {
      garciaA = aForGarciaEcuation();
      degreesOfFreedom = Helper.garciaEcuation(garciaA);
      sampleSize = degreesOfFreedom + 1;
    }

    degreesOfFreedom = sampleSize - 1;
    Double chiInfVariance = new ChiSquaredDistributionCalc()
      .f(1 - (alpha / 2))
      .degreesOfFreedom((double) sampleSize - 1)
      .calculatePx()
      .x();

    Double chiSupVariance = new ChiSquaredDistributionCalc()
      .f(alpha / 2)
      .degreesOfFreedom((double) sampleSize - 1)
      .calculatePx()
      .x();

    Double numerator = (sampleSize - 1) * Math.pow(sampleStandardDeviation, 2);

    limitInfVariance = numerator / chiInfVariance;
    limitSupVariance = numerator / chiSupVariance;

    limitInfStandardDeviation = Math.sqrt(limitInfVariance);
    limitSupStandardDeviation = Math.sqrt(limitSupVariance);

    sampleErrorVariance = new Double(
      Math.ceil(
        (limitSupVariance - limitInfVariance) / 2)).intValue();
    limitRelationshipVariance = limitSupVariance / limitInfVariance;

    sampleErrorStandardDeviation = new Double(
      Math.ceil(
        (limitSupStandardDeviation - limitInfStandardDeviation) / 2)).intValue();
    limitRelationshipStandardDeviation = limitSupStandardDeviation / limitInfStandardDeviation;

    Log.i(VarianceInferenceCalc.class.toString(), "Post: " + this.toString());
    return this;
  }

  private Double aForGarciaEcuation() {

    if (isNullorZero(limitRelationshipVariance))
      limitRelationshipVariance = Math.pow(limitRelationshipStandardDeviation, 2); // R = R'^2

    Double z = new NormalDistributionCalc().f(1 - alpha / 2).calculatePx().x();
    double temp = Math.pow(limitRelationshipVariance, (double) 1 / 3); // raiz cub R

    return z * (temp + 1) / (2 * (temp - 1));
  }

  @Override
  public String toString() {
    return "sampleErrorVariance=" + sampleErrorVariance + "\n" +
      "sampleErrorStandardDeviation=" + sampleErrorStandardDeviation + "\n" +
      "garciaA=" + garciaA + "\n";
  }
}
