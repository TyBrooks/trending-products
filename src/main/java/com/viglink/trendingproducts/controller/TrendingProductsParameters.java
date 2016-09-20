package com.viglink.trendingproducts.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.viglink.trendingproducts.type.LookbackType;
import com.viglink.trendingproducts.type.TrendType;

import javax.validation.constraints.NotNull;
import java.util.Arrays;


public class TrendingProductsParameters {
// TODO add annotation-based validation here. Add custom ser/de for lookback enum
    //TODO annotation-based validation doesn't work

    @JsonProperty(required = true)
    @NotNull
    private TrendType type;
    @JsonProperty(required = true)
    @NotNull
    private LookbackType lookback;
    @JsonProperty
    private Long accountId;
    @JsonProperty
    private Long[] campaigns;
    @JsonProperty
    private String category;
    @JsonProperty
    private String merchant;
    @JsonProperty
    private String product;

    public TrendingProductsParameters() {
    }

    public TrendingProductsParameters(TrendType type, LookbackType lookback, Long accountId, Long[] campaigns, String category, String merchant, String product) {
        this.type = type;
        this.lookback = lookback;
        this.accountId = accountId;
        this.campaigns = campaigns;
        this.category = category;
        this.merchant = merchant;
        this.product = product;
    }

    public TrendType getType() {
        return type;
    }

    public LookbackType getLookback() {
        return lookback;
    }

    public Long getAccountId() {
        return accountId;
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
        return "TrendingProductsParameters{" +
                "type='" + type + '\'' +
                ", lookback=" + lookback +
                ", accountId=" + accountId +
                ", campaigns=" + Arrays.toString(campaigns) +
                ", category='" + category + '\'' +
                ", merchant='" + merchant + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
