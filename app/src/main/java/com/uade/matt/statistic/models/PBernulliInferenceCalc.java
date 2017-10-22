package com.uade.matt.statistic.models;

import android.util.Log;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class PBernulliInferenceCalc extends InferenceCalc {
  @Getter
  @Setter
  private Integer size, sampleSize;
  @Getter
  @Setter
  private Double sampleP;
  //limits
  @Getter
  @Setter
  private Double limitInf, limitSup, error, alpha;

  @Getter
  @Setter
  private String resultMessage;

  public PBernulliInferenceCalc calc() {
    Log.i(PBernulliInferenceCalc.class.toString(), "Pre: " + this.toString());

    sampleP = (double) sampleSize / size;
    // Gb ( r / n ; A ) == 1 - Fb ( r -1 / n ; A )
    limitInf = new BinomialDistributionCalc().f(1 - alpha / 2).r(sampleSize - 1).n(size).calculatePx().p();
    limitSup = new BinomialDistributionCalc().f(alpha / 2).r(sampleSize).n(size).calculatePx().p();
    error = (limitSup - limitInf) / 2;
    Log.i(PBernulliInferenceCalc.class.toString(), "Post: " + this.toString());
    return this;
  }

  // NONO esto sirve para comparar H0 con H1 de alpha y beta, y calcular el requerido
  //        if(!isNullorZero(error))
  //        {
  //            Pair<Integer, Integer> temp = calcRequiredSize(1000, 500);//first number doesn't matter
  //            size = temp.first;
  //            sampleSize = temp.second;
  //        }
  //PD ya estoy quemado =(
  //    private Pair<Integer, Integer> calcRequiredSize(Integer n, Integer r) {
  //        Double A, B, tempError;
  //
  //        A = new BinomialDistributionCalc().f(1 - alpha/2).r(r - 1).n(n).calculatePx().p();
  //        B = new BinomialDistributionCalc().f(alpha/2).r(r).n(n).calculatePx().p();
  //        tempError = (B - A) / 2 ;
  //
  //        // Acepted difference
  //        if( (error - tempError) < 0.00001 )
  //            return new Pair<>(n, r);
  //        else{
  //            if (error > tempError) {
  //                return calcRequiredSize(
  //                  new Double(n * 0.5).intValue(),
  //                  new Double(r * 0.5).intValue()
  //                );
  //            }
  //            else
  //            {
  //                return calcRequiredSize(
  //                  new Double(n * 1.5).intValue(),
  //                  new Double(r * 1.5).intValue()
  //                );
  //            }
  //        }
  //
  //    }

  @Override
  public String toString() {
    return resultMessage;
  }
}
