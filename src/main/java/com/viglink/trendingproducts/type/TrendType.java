package com.viglink.trendingproducts.type;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by tybrooks on 9/19/16.
 */
public enum TrendType {

    CLICK("click"), REVENUE("revenue");

    private String jsonName;

    TrendType(String jsonName) {
        this.jsonName = jsonName;
    }

    @JsonValue
    public String getJsonName() {
        return jsonName;
    }
}
