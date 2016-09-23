package com.viglink.trendingproducts.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
                ", title='" + title + '\'' +
                '}';
    }
}
