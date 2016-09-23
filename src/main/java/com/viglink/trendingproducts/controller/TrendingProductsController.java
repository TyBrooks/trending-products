package com.viglink.trendingproducts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.viglink.trendingproducts.api.ApiErrorResponse;
import com.viglink.trendingproducts.api.ApiResponse;
import com.viglink.trendingproducts.api.ApiSuccessResponse;
import com.viglink.trendingproducts.model.Product;
import com.viglink.trendingproducts.utils.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class TrendingProductsController {

    private RandomUtils randomUtils = new RandomUtils();

    @GetMapping(value = "/product", params = "trend", produces = "application/json")
    @ResponseBody
    ResponseEntity<ApiResponse> getTrending(
            @Valid TrendingProductDto requestParams, BindingResult validationResult,
            @RequestParam(value = "per_page", defaultValue = "100") int perPage,
            @RequestParam(value = "page", defaultValue = "1") int page) throws JsonProcessingException {


        if (validationResult.hasErrors()) {
            ApiErrorResponse errorResponse = createErrorResponse(validationResult);
            return new ResponseEntity<ApiResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        ApiSuccessResponse<Product> response = generateRandomResponse(page, perPage);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    private ApiSuccessResponse<Product> generateRandomResponse(int page, int perPage) {
        int resultsLength = randomUtils.randomNumber(2);

        int toGenerate;
        ApiSuccessResponse.Pagination pagination;
        if (resultsLength < perPage) {
            pagination = new ApiSuccessResponse.Pagination(1, perPage, resultsLength);
            toGenerate = resultsLength;
        } else {
            int max = (int) Math.ceil(1.0 * resultsLength / perPage);
            pagination = new ApiSuccessResponse.Pagination(Math.min(page, max), perPage, resultsLength);

            toGenerate = (page >= max && resultsLength % perPage != 0) ? resultsLength % perPage : perPage;
        }

        List<Product> products = Lists.newArrayList();
        for (int i = 0; i < toGenerate; i++) products.add(randomUtils.generateRandomProduct());

        return new ApiSuccessResponse<Product>(products, pagination);
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleError(Exception e) throws JsonProcessingException {
        String message =  "There were problems parsing your request parameters";

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(message, null);
        return new ResponseEntity<ApiResponse>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private ApiErrorResponse createErrorResponse(BindingResult result) {
        String message = "There were problems with your request parameters";
        List<Map<String, String>> errors = Lists.newArrayList();

        for (ObjectError error : result.getAllErrors()) {
            String field;
            try {
                Map<String, String> errorInfo = Maps.newHashMap();
                field = ((FieldError) error).getField();
                errorInfo.put("type", "parameter");
                errorInfo.put("name", field);
                errorInfo.put("problem", error.getDefaultMessage());

                errors.add(errorInfo);
            } catch (Exception e) {

            }
        }

        return new ApiErrorResponse(message, errors);
    }

}