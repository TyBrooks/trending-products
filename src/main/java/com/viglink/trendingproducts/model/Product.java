package com.viglink.trendingproducts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public final class Product {

    @JsonProperty
    private final Long id;
    @JsonProperty
    private final String imageUrl;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final BigDecimal price;
    @JsonProperty
    private final String merchantName;
    @JsonProperty
    private final String targetUrl;
    @JsonProperty
    private final String category;
    @JsonProperty("trends")
    private final List<Trend> trends;

    public Product(Long id, String imageUrl, String title, BigDecimal price, String merchantName, String targetUrl, String category, List<Trend> trends) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
        this.merchantName = merchantName;
        this.targetUrl = targetUrl;
        this.category = category;
        this.trends = trends;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getCategory() {
        return category;
    }

    public List<Trend> getTrends() {
        return trends;
    }
}
