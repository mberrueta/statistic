package com.uade.matt.statistic.models;

import org.apache.commons.math3.distribution.BinomialDistribution;

import lombok.Getter;
import lombok.Setter;

import static com.uade.matt.statistic.R.id.p;

public class BinomialDistributionCalc {
    private BinomialDistribution dist;
    // trials
    @Getter @Setter private int n;
    // observed
    @Getter @Setter private int r;
    // probability
    @Getter @Setter private double p;

    @Getter @Setter private double f, g, pbin;

    public BinomialDistributionCalc(int n, int r, double p) {
        this.n = n;
        this.r = r;
        this.p = p;
    }

    public BinomialDistributionCalc calculatePx() {
        if (n == 0) {
            f = g = pbin = 1.0;
        } else {

            BinomialDistribution distribution = new BinomialDistribution(n, p);

            f = distribution.cumulativeProbability(r);
            g = r > 0 ? distribution.cumulativeProbability(r - 1, n) : 1.0;
            pbin = distribution.probability(r);
        }
        return this;
    }
}
