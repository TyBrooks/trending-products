package com.viglink.trendingproducts.parameters;

/**
 * Created by tybrooks on 9/19/16.
 */
public class TrendingProductsParametersDto {
// TODO add annotation-based validation here. Add custom ser/de for lookback enum

    private String type;
    private String lookback;
    private Long accountId;
    private Long[] campaigns;
    private String category;
    private String merchant;
    private String product;

}
