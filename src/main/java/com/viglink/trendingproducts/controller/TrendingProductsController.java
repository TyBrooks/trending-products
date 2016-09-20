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
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    ResponseEntity<ObjectNode> getRealTrending(@Valid @RequestBody TrendingProductsParameters requestParams,
                           BindingResult validationResult,
                           @RequestParam(defaultValue = "1000", name = "per_page") int perPage,
                           @RequestParam(defaultValue = "1") int page) throws JsonProcessingException {

        if (validationResult.hasErrors()) {
            return new ResponseEntity<ObjectNode>(createErrorResponse(validationResult), HttpStatus.BAD_REQUEST);
        }

        int numResults = randomUtils.randomNumber(2);
        TrendingProduct[] results = randomUtils.generateTrendingProducts(numResults);

        return new ResponseEntity<ObjectNode>(createProductsResponse(results, page, perPage), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ObjectNode> handleInvalidRequestParams(HttpMessageNotReadableException e) throws JsonProcessingException {
        ObjectNode root = nodeFactory.objectNode();
        root.put("message", "There were problems with your request parameters");
        ArrayNode errorsList = root.putArray("errors");

        ObjectNode errors = errorsList.addObject();
        errors.put("type", "parameter_parsing");

        return new ResponseEntity<ObjectNode>(root, HttpStatus.BAD_REQUEST);
    }

    private ObjectNode createProductsResponse(TrendingProduct[] results, int page, int perPage) {
        ObjectNode root = nodeFactory.objectNode();
        root.putArray("results").addAll(objectMapper.convertValue(results, ArrayNode.class));

        if (results.length < perPage) {
            root.put("page", 1);
            root.put("total", 1);
        } else {
            boolean isRemainder = (results.length % perPage) == 0;
            int total = results.length / perPage + (isRemainder ? 1 : 0);

            root.put("page", Math.min(page, total));
            root.put("total", total);
        }

        return root;
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