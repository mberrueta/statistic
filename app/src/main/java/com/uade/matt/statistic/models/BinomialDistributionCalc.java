package com.uade.matt.statistic.models;

import org.apache.commons.math3.distribution.BinomialDistribution;

public class BinomialDistributionCalc {
    private BinomialDistribution dist;
    // trials
    private int n = 1;
    // observed
    private int r = 0;
    // probability
    private double p = 0.01;

    private double leftPvalue, rightPvalue, twoTailPvalue;

//
//    public BinomialDistributionCalc() {
//        dist = new BinomialDistribution(n, p);
//    }


    public BinomialDistributionCalc(int n, int r, double p) {
        this.n = n;
        this.r = r;
        this.p = p;
    }

    public BinomialDistributionCalc calculatePx() {
        if (n == 0) {
            leftPvalue = rightPvalue = twoTailPvalue = 1.0;
        } else {

            BinomialDistribution distribution = new BinomialDistribution(n, p);

            leftPvalue = distribution.cumulativeProbability(r);
            rightPvalue = r > 0 ? distribution.cumulativeProbability(r - 1, n) : 1.0;
            twoTailPvalue = leftPvalue + rightPvalue;
            twoTailPvalue = twoTailPvalue > 1.0 ? 1.0 : twoTailPvalue;
        }
        return this;
    }



    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }


}
