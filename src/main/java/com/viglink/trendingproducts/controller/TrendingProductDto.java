package com.viglink.trendingproducts.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendingProductDto {

    @NotNull
    private String trend;
    @NotNull
    private String from;
    private String until = "now";
    private Long account;
    private Long[] campaigns;
    private String category;
    private String merchant;
    private String product;

    public TrendingProductDto() {
    }

    public String getTrend() {
        return trend;
    }

    public String getFrom() {
        return from;
    }

    public String getUntil() {
        return until;
    }

    public Long getAccount() {
        return account;
    }

    public Long[] getCampaigns() {
        return campaigns;
    }

    public String getCategory() {
        return category;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getProduct() {
        return product;
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
