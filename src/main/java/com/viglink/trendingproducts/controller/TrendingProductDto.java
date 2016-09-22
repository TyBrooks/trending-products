package com.viglink.trendingproducts.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendingProductDto {

    @JsonProperty(required = true)
    @NotNull
    private String trend;
    @JsonProperty(required = true)
    @NotNull
    private String from;
    @JsonProperty
    private String until = "now";
    @JsonProperty
    private Long account;
    @JsonProperty
    private Long[] campaigns;
    @JsonProperty
    private String category;
    @JsonProperty
    private String merchant;
    @JsonProperty
    private String product;

    public TrendingProductDto() {
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long[] getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Long[] campaigns) {
        this.campaigns = campaigns;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "TrendingProductDto{" +
                "trend=" + trend +
                ", from=" + from +
                ", until='" + until + '\'' +
                ", account=" + account +
                ", campaigns=" + Arrays.toString(campaigns) +
                ", category='" + category + '\'' +
                ", merchant='" + merchant + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
