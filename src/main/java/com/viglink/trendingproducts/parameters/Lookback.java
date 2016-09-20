package com.viglink.trendingproducts.parameters;

/**
 * Created by tybrooks on 9/19/16.
 */
public enum Lookback {

    ONE_HOUR("1h"), ONE_DAY("1d"), SEVEN_DAYS("7d"), THIRTY_DAYS("30d");

    private String code;

    Lookback(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
