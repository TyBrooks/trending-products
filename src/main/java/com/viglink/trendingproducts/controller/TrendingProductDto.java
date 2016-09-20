package com.viglink.trendingproducts.controller;

import com.viglink.trendingproducts.model.TrendingProduct;

/**
 * Created by tybrooks on 9/19/16.
 */
public class TrendingProductDto {
    private TrendingProduct[] results;
    private Pagination pagination;

    public TrendingProductDto(int page, int total, TrendingProduct[] results) {
        this.pagination = new Pagination(page, total);
        this.results = results;
    }

    public TrendingProduct[] getResults() {
        return results;
    }

    public Pagination getPagination() {
        return pagination;
    }

    private final class Pagination {
        private int page;
        private int total;

        public Pagination(int page, int total) {
            this.page = page;
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public int getTotal() {
            return total;
        }
    }
}
