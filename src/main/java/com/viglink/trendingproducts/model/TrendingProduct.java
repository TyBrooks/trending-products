package com.viglink.trendingproducts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class TrendingProduct {

    @JsonProperty("trend")
    private final Trend trend;
    @JsonProperty("product")
    private final Product product;

    public TrendingProduct(Trend trend, Product product) {
        this.trend = trend;
        this.product = product;
    }

    public Trend getTrend() {
        return trend;
    }

    public Product getProduct() {
        return product;
    }
}
