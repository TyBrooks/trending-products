package com.viglink.trendingproducts.type;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by tybrooks on 9/19/16.
 */
public enum LookbackType {

    ONE_HOUR("1h"), ONE_DAY("1d"), SEVEN_DAYS("7d"), THIRTY_DAYS("30d");

    private String code;

    LookbackType(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
