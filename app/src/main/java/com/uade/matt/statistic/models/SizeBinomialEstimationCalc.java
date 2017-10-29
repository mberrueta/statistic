package com.uade.matt.statistic.models;

import android.support.v4.util.Pair;
import android.util.Log;

import com.uade.matt.statistic.utils.Helper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;
import static com.uade.matt.statistic.utils.Helper.round;

@Accessors(chain = true, fluent = true)
public class SizeBinomialEstimationCalc extends InferenceCalc {
  @Getter
  @Setter
  private Integer size, sampleSize; //n y r
  @Getter
  @Setter
  private Double p0, p1, alpha, beta;
  @Getter
  @Setter
  private String resultMessage = "";

  @Getter
  @Setter
  private Boolean lessThan;

  private Integer max_tries = 1200, tries = 0;
  private Integer bestN, bestR;
  private Double A, B, bestErrorA = 999.0, bestErrorB = 999.0;

  public SizeBinomialEstimationCalc calc() {
    Log.i(SizeBinomialEstimationCalc.class.toString(), "Pre: " + this.toString());

    Pair<Integer, Integer> temp = null;//first number doesn't matter
    try {
      temp = calcRequiredSize(1000, 500);
    } catch (Exception e) {
      resultMessage = e.toString();
      return this;
    }
    size = temp.first;
    sampleSize = temp.second;
    resultMessage = toString();

    Log.i(SizeBinomialEstimationCalc.class.toString(), "Post: " + this.toString());
    return this;
  }

  @Override
  public String toString() {
    return " tries=" + tries
      + "\n best N=" + bestN + ", best R=" + bestR
      + "\n A=" + A + ", B=" + B
      + "\n Error A=" + String.format("%.4f",bestErrorA) + ", Error B=" + String.format("%.4f",bestErrorB) ;
  }

  private Pair<Integer, Integer> calcRequiredSize(Integer n, Integer r) throws Exception {
    tries++;

    if (max_tries <= tries)
      return new Pair<>(bestN, bestR);

    if (n < 1 || r < 1)
      throw new Exception("I cant guess it =(. n: " + n + ", r:" + r + " tries: " + max_tries);

    Log.i(SizeBinomialEstimationCalc.class.toString(), "calc: n:" + n + " r:" + r);

    Double errorA, errorB;

    // Gb(r / n; p0) = alpha
    A = round(new BinomialDistributionCalc().p(p0).n(n).r(r).calculatePx().g());
    // Fb(r / n; p1) = beta
    Integer bR = lessThan ? r -1 : r + 1;
    B = round(new BinomialDistributionCalc().p(p1).n(n).r(bR).calculatePx().f());

    errorA = alpha - A;
    errorB = beta - B;

    if (Math.abs(errorA) < bestErrorA && Math.abs(errorB) < bestErrorB) {
      bestN = n;
      bestR = r;
      bestErrorA = round(Math.abs(errorA));
      bestErrorB = round(Math.abs(errorB));
    }
    // Acepted difference
    if ((Math.abs(errorA) < 0.001) && (Math.abs(errorB) < 0.001))
      return new Pair<>(n, r);
    else {

      // try with new possible R
      Integer newR = new BinomialDistributionCalc().n(n).p(p0).g(alpha).calculatePx().r();
      if (!newR.equals(r)) {
        return calcRequiredSize(new Double(n).intValue(), new Double(newR).intValue());
      } else {
        if ((errorA + errorB) > 0) {
          // try with a smaller size
          return calcRequiredSize(new Double(n * 0.9).intValue(), new Double(newR).intValue());
        } else {
          //try with a higher size
          return calcRequiredSize(new Double(n + 7).intValue(), new Double(newR).intValue());
        }
      }
    }
  }
}
