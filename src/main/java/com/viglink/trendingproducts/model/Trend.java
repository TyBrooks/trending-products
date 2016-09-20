package com.viglink.trendingproducts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viglink.trendingproducts.type.TrendType;

public final class Trend {

    @JsonProperty
    private final Float weight;
    @JsonProperty
    private final TrendType type;

    public Trend(Float weight, TrendType type) {
        this.weight = weight;
        this.type = type;
    }

    public Float getWeight() {
        return weight;
    }

    public TrendType getType() {
        return type;
    }
}
