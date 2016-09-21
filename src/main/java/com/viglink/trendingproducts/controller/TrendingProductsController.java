package com.viglink.trendingproducts.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TrendingProductsController {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private RandomUtils randomUtils = new RandomUtils();
    private JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    @PostMapping(value = "/trending-products", produces = "application/json")
    @ResponseBody
    ResponseEntity<ObjectNode> getRealTrending(@Valid @RequestBody TrendingProductsRequest requestParams,
                           BindingResult validationResult) throws JsonProcessingException {


        if (validationResult.hasErrors()) {
            return new ResponseEntity<ObjectNode>(createErrorResponse(validationResult), HttpStatus.BAD_REQUEST);
        }

        int page = requestParams.getPage();
        int perPage = requestParams.getPerPage();
        TrendingProductsParameters parameters = requestParams.getTrendingProductsParameters();

//        Map<String, String> errors = new HashMap<String, String>();
//        if (parameters.getType() == null)
//            errors.put("type", "is required");
//
//        if (parameters.getLookback() == null)
//            errors.put("lookback", "is required");
//
//        if (!errors.isEmpty()) {
//            return new ResponseEntity<ObjectNode>(createErrorResponse(errors), HttpStatus.BAD_REQUEST);
//        }

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
        ObjectNode pagination = root.putObject("pagination");

        if (results.length < perPage) {
            pagination.put("page", 1);
            pagination.put("total", 1);
            root.putArray("results").addAll(objectMapper.convertValue(results, ArrayNode.class));
        } else {
            boolean isRemainder = (results.length % perPage) != 0;
            int total = results.length / perPage + (isRemainder ? 1 : 0);

            int numResults = (page >= total && isRemainder) ? results.length % perPage : perPage;
            TrendingProduct[] subset = new TrendingProduct[numResults];
            System.arraycopy(results, 0, subset, 0, numResults);

            pagination.put("page", Math.min(page, total));
            pagination.put("total", total);
            root.putArray("results").addAll(objectMapper.convertValue(subset, ArrayNode.class));
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

    private ObjectNode createErrorResponse(Map<String, String> errors) {
        ObjectNode root = nodeFactory.objectNode();
        root.put("message", "There were problems with your request parameters");
        ArrayNode errorsList = root.putArray("errors");

        for (Map.Entry<String, String> error : errors.entrySet()) {
            ObjectNode errorRoot = errorsList.addObject();
            errorRoot.put("type", "parameter");
            errorRoot.put("name", error.getKey());
            errorRoot.put("problem", error.getValue());
        }

        return root;
    }

    @JsonDeserialize(using = TrendingProductRequestDeserializer.class)
    private class TrendingProductsRequest {
        @Valid
        private TrendingProductsParameters trendingProductsParameters;
        private int page = 1;
        private int perPage = 1000;

        public TrendingProductsRequest() {
        }

        public TrendingProductsParameters getTrendingProductsParameters() {
            return trendingProductsParameters;
        }

        public void setTrendingProductsParameters(TrendingProductsParameters trendingProductsParameters) {
            this.trendingProductsParameters = trendingProductsParameters;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }
    }

    private class TrendingProductRequestDeserializer extends JsonDeserializer<TrendingProductsRequest> {

        private final ObjectReader paramsReader = objectMapper.readerFor(TrendingProductsParameters.class);

        @Override
        public TrendingProductsRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            TrendingProductsRequest request = new TrendingProductsRequest();
            Map<String, String> fieldErrors = new HashMap<String, String>();

            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            if (!(node instanceof ObjectNode)) {
                fieldErrors.put("all", "invalid json");
                throw new RuntimeException("invalid json");
            }

            ObjectNode object = (ObjectNode) node;

            if (object.has("page")) {
                try {
                    int page = object.get("page").intValue();
                    request.setPage(page);
                } catch (Exception e) {
                    fieldErrors.put("page", "not a valid integer");
                }

                object.remove("page");
            }

            if (object.has("per_page")) {
                try {
                    int perPage = object.get("per_page").intValue();
                    request.setPerPage(perPage);
                } catch (Exception e) {
                    fieldErrors.put("perPage", "not a valid integer");
                }

                object.remove("per_page");
            }

            TrendingProductsParameters params = paramsReader.readValue(object);
            request.setTrendingProductsParameters(params);

            return request;
        }
    }
}