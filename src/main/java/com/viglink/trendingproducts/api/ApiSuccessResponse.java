package com.viglink.trendingproducts.api;

import java.util.List;

public class ApiSuccessResponse<Item> implements ApiResponse {

    private final List<Item> results;
    private final Pagination pagination;

    public ApiSuccessResponse(List<Item> results, Pagination pagination) {
        this.results = results;
        this.pagination = pagination;
    }

    public List<Item> getResults() {
        return results;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public static class Pagination {

        private int page;
        private int perPage;
        private int totalItems;

        public Pagination(int page, int perPage, int totalItems) {
            this.page = page;
            this.perPage = perPage;
            this.totalItems = totalItems;
        }

        public int getPage() {
            return page;
        }

        public int getPerPage() {
            return perPage;
        }

        public int getTotalItems() {
            return totalItems;
        }
    }

}
