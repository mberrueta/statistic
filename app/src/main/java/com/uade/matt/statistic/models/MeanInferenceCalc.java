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
    private Double /*mean,*/ standardDeviation;

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

        //conocido sigma
        if (!isNullorZero(standardDeviation)) {
            // Normal z
            Double multiplier = new NormalDistributionCalc()
                    .f(1 - (alpha / 2))
                    .calculatePx()
                    .x();
            calculate(multiplier, standardDeviation);
        } else {

            if (isNullorZero(sampleSize)) {
                // metodo iterativo sacar n.no importa el primer valor
                sampleSize = calcSampleSizeIterativeMethod(500, 5);
            }

            // T of student
            Double multiplier = new TDistributionCalc()
                    .f(1 - (alpha / 2))
                    .degreesOfFreedom((double) (sampleSize - 1))
                    .calculatePx()
                    .x();

            calculate(multiplier, sampleStandardDeviation);
        }

        Log.i(MeanInferenceCalc.class.toString(), "Post: " + this.toString());


        return this;
    }

    private Integer calcSampleSizeIterativeMethod(Integer n, Integer maxTries) {
        Log.i(MeanInferenceCalc.class.toString(),
                "calcSampleSizeIterativeMethod n: " + n +
                        " maxtries: " + maxTries
        );

        if (maxTries < 1)
            return n;

        Double t = new TDistributionCalc()
                .f(1 - (alpha / 2))
                .degreesOfFreedom((double) (n - 1))
                .calculatePx()
                .x();

        Double temp = t * sampleStandardDeviation / sampleError;
        temp = Math.ceil(Math.pow(temp, 2));//round up and ^ 2

        if (temp.intValue() != n)
            return calcSampleSizeIterativeMethod(temp.intValue(), maxTries--);

        return temp.intValue();
    }

    private void calculate(double multiplier, Double variation) {
        Double temp;
        Double finitePopulationCorrection = 1.0;

        if (isNullorZero(sampleSize)) {
            temp = multiplier * variation / sampleError;
            temp = Math.ceil(Math.pow(temp, 2));//round up and ^ 2
            Integer infinitePopulationSampleSize = temp.intValue();

            // in finite population case
            if (!isNullorZero(size)) {
                sampleSize = new Double(
                        Math.ceil(
                                ((double) size * infinitePopulationSampleSize) / (size + infinitePopulationSampleSize)
                        )).intValue();
            } else
                sampleSize = infinitePopulationSampleSize;

        }

        if (!isNullorZero(size))
            finitePopulationCorrection = Math.sqrt((size - sampleSize) / size);

        temp = multiplier * variation * finitePopulationCorrection / Math.sqrt(sampleSize);
        limitInf = sampleMean - temp;
        limitSup = sampleMean + temp;

        temp = Math.ceil(temp); //round up
        sampleError = temp.intValue();
    }


    @Override
    public String toString() {
        return "MeanInferenceCalc{" +
//                "mean=" + mean +
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
