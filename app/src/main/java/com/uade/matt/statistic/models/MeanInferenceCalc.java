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
    private Integer size;

    @Getter
    @Setter
    private Double mean, standardDeviation;

    //poblacion
    @Getter
    @Setter
    private Integer sampleSize;
    @Getter
    @Setter
    private Double sampleMean, sampleStandardDeviation;

    //limits
    @Getter
    @Setter
    private Double limitInf, limitSup, alpha;


    //calcs
    @Getter
    @Setter
    private Integer sampleError;

    @Getter
    @Setter
    private Double successProb;

    @Getter
    @Setter
    private String resultMessage;

    public MeanInferenceCalc calc() {
        Log.i(MeanInferenceCalc.class.toString(), "Pre: " + this.toString());
        Double temp = 0.0;

        //conocido sigma
        if(!isNullorZero(standardDeviation)){

            successProb = 1 - (alpha / 2);
            double z = new NormalDistributionCalc().f(successProb).calculatePx().x();

            if(isNullorZero(sampleSize))
            {
                temp = z * standardDeviation / sampleError;
                temp = Math.ceil(Math.pow(temp, 2));//round up and ^ 2
                sampleSize = temp.intValue();
            }


            temp = z * standardDeviation / Math.sqrt(sampleSize);
            limitInf = sampleMean - temp;
            limitSup = sampleMean + temp;

            temp = Math.ceil(temp); //round up
            sampleError = temp.intValue();



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
                ", sampleError=" + sampleError +
                ", alpha=" + alpha +
                ", sampleError=" + sampleError +
                ", successProb=" + successProb +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
