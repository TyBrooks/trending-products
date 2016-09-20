package com.viglink.trendingproducts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Product {

    @JsonProperty
    private final Long id;
    @JsonProperty
    private final String imageUrl;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final Float price;
    @JsonProperty
    private final String merchantName;
    @JsonProperty
    private final String targetUrl;
    @JsonProperty
    private final String category;

    public Product(Long id, String imageUrl, String title, Float price, String merchantName, String targetUrl, String category) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
        this.merchantName = merchantName;
        this.targetUrl = targetUrl;
        this.category = category;
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

    public Float getPrice() {
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
}
