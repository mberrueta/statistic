package com.uade.matt.statistic.utils;

import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Helper {
    public enum NumberType {
        DOUBLE, INTEGER
    }

    public static Object getParsed(NumberType numberType, EditText et) {

        if (et.getText().toString().trim().length() == 0)
            return null;

        String s = et.getText().toString().trim();
        switch (numberType) {
            case DOUBLE:
                return Double.parseDouble(et.getText().toString().trim());
            case INTEGER:
                return Integer.parseInt(et.getText().toString().trim());
            default:
                return s;
        }
    }

    public static Double round(Double number) {
        DecimalFormat df = new DecimalFormat("#.########");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double
                .parseDouble(df.format(number));
    }

    public static class Dto {
        public Integer id;
        public BigDecimal value;
        public Boolean isMax;

        public Dto(Integer id, Double value, Boolean isMax) {
            this.id = id;
            this.value = BigDecimal.valueOf(value);
            this.isMax = isMax;
        }
    }

    public static boolean isNullorZero(Integer i){
        return 0 == ( i == null ? 0 : i);
    }
    public static boolean isNullorZero(Double i){
        return 0 == ( i == null ? 0 : i);
    }
    public static boolean isNullorEmpty( final String s ) {
        return s == null || s.toString().trim().isEmpty();
    }
}
