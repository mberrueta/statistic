package com.uade.matt.statistic.models;

import android.util.Log;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.uade.matt.statistic.utils.Helper.isNullorZero;

@Accessors(chain = true, fluent = true)
public class MeanInferenceCalc extends InferenceCalc {

    //poblacion
    @Getter
    @Setter
    private Double mean, size, standardDeviation;

    //poblacion
    @Getter
    @Setter
    private Double sampleMean, sampleSize, sampleStandardDeviation;

    //limits
    @Getter
    @Setter
    private Double limitInf, limitSup, alpha, error;


    //calcs
    @Getter
    @Setter
    private Double sampleError, successProb;

    @Getter
    @Setter
    private String resultMessage;

    public MeanInferenceCalc calc() {
        Log.i(MeanInferenceCalc.class.toString(), "Pre: " + this.toString());

        //conocido sigma
        if(!isNullorZero(standardDeviation)){

            successProb = 1 - (alpha / 2);
            double z = new NormalDistributionCalc().f(successProb).calculatePx().x();

            if(isNullorZero(sampleSize))
            {
                sampleSize = Math.pow(z * standardDeviation / error, 2) ;
            }


            sampleError = z * standardDeviation / Math.sqrt(sampleSize);
            limitInf = sampleMean - sampleError;
            limitSup = sampleMean + sampleError;

        }
        else
        {
            //desconocido sigma
        }

        Log.i(MeanInferenceCalc.class.toString(), "Post: " + this.toString());


        return this;
    }


    @Override
    public String toString() {
        return "MeanInferenceCalc{" +
                "mean=" + mean +
                ", size=" + size +
                ", standardDeviation=" + standardDeviation +
                ", sampleMean=" + sampleMean +
                ", sampleSize=" + sampleSize +
                ", sampleStandardDeviation=" + sampleStandardDeviation +
                ", limitInf=" + limitInf +
                ", limitSup=" + limitSup +
                ", error=" + error +
                ", alpha=" + alpha +
                ", sampleError=" + sampleError +
                ", successProb=" + successProb +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
