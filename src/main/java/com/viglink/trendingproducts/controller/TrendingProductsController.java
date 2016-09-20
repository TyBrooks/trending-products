package com.viglink.trendingproducts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.viglink.trendingproducts.model.*;
import com.viglink.trendingproducts.utils.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class TrendingProductsController {

    private ObjectMapper objectMapper = new ObjectMapper();
    private RandomUtils randomUtils = new RandomUtils();
    private JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    @PostMapping(value = "/trending-products", produces = "application/json")
    @ResponseBody
    ResponseEntity<String> getRealTrending(@Valid @RequestBody TrendingProductsParameters requestParams,
                           BindingResult validationResult,
                           @RequestParam(defaultValue = "1000", name = "per_page") int perPage,
                           @RequestParam(defaultValue = "1") int page) throws JsonProcessingException {

        if (validationResult.hasErrors()) {
            System.out.println("FOUND ERRORS: " + validationResult.getErrorCount());
            return new ResponseEntity<String>(objectMapper.writeValueAsString(createErrorResponse(validationResult)), HttpStatus.BAD_REQUEST);
        }

        System.out.println(requestParams.toString());

        TrendingProduct[] results = new TrendingProduct[randomUtils.randomNumber(2)];
        for (int i = 0; i < results.length; i++)
            results[i] = randomUtils.generateTrendingProduct(true);

        return new ResponseEntity<String>(objectMapper.writeValueAsString(wrapResponse(results, page, perPage)), HttpStatus.OK);
    }

    private TrendingProductDto wrapResponse(TrendingProduct[] results, int page, int perPage) {
        if (results.length < perPage) {
            return new TrendingProductDto(1, 1, results);
        }

        boolean isRemainder = (results.length % perPage) == 0;
        int total = results.length / perPage + (isRemainder ? 1 : 0);

        return new TrendingProductDto(page, total, results);
    }

    private ObjectNode createErrorResponse(BindingResult result) {
        ObjectNode root = nodeFactory.objectNode();
        root.put("message", "There were problems with your request parameters");
        ArrayNode errorsList = root.putArray("errors");

        for (ObjectError error : result.getAllErrors()) {
            String field;
            try {
                field = ((FieldError) error).getField();
            } catch (Exception e) {
                field = null;
            }

            ObjectNode errorRoot = errorsList.addObject();
            errorRoot.put("type", "parameter");
            errorRoot.put("name", field);
            errorRoot.put("problem", error.getDefaultMessage());
        }

        return root;
    }

}