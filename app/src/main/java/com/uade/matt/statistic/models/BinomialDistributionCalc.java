package com.uade.matt.statistic.models;

import android.util.Log;

import org.apache.commons.math3.distribution.BinomialDistribution;

import lombok.Getter;
import lombok.Setter;

import static com.uade.matt.statistic.R.id.p;

public class BinomialDistributionCalc {
    private BinomialDistribution dist;
    // trials
    @Getter @Setter private Integer n;
    // observed
    @Getter @Setter private Integer r;
    // probability
    @Getter @Setter private Double p;

    @Getter @Setter private Double f, g, pbin;

    public BinomialDistributionCalc(int n, int r, double p) {
        this.n = n;
        this.r = r;
        this.p = p;
    }

    public BinomialDistributionCalc calculatePx() {
        Log.i(BinomialDistributionCalc.class.toString(), "Pre: " + this.toString());

        if (n == 0) {
            f = g = pbin = 1.0;
        } else {

            BinomialDistribution distribution = new BinomialDistribution(n, p);

            f = distribution.cumulativeProbability(r);
            g = r > 0 ? distribution.cumulativeProbability(r - 1, n) : 1.0;
            pbin = distribution.probability(r);
        }
        Log.i(BinomialDistributionCalc.class.toString(), "Post: " + this.toString());

        return this;
    }

    @Override
    public String toString() {
        return "BinomialDistributionCalc{" +
                "dist=" + dist +
                ", n=" + n +
                ", r=" + r +
                ", p=" + p +
                ", f=" + f +
                ", g=" + g +
                ", pbin=" + pbin +
                '}';
    }
}
