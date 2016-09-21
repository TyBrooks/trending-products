package com.viglink.trendingproducts;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

public class ApiDocumentation {

    private JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Rule
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("target/generated-snippets");

    private RequestSpecification spec;

    @Before
    public void setUp() {
        this.spec = new RequestSpecBuilder().addFilter(
                documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void documentResponseFieldsHighLevel() throws Exception {
        ObjectNode root = nodeFactory.objectNode();
        root.put("type", "click");
        root.put("lookback", "1d");
        root.put("account", 1234);
        ArrayNode campaigns = root.putArray("campaigns");
        campaigns.add(3456);
        campaigns.add(4567);
        root.put("category", "HG");
        root.put("merchant", "ebay");
        root.put("product", "lamp");

        RestAssured.given(this.spec)
                .body(objectMapper.writeValueAsString(root))
                .contentType(ContentType.JSON)
                .accept("application/json")
                .filter(document("response-high-level", responseFields(
                        fieldWithPath("pagination").description("Pagination Metadata"),
                        fieldWithPath("results[]").type("Array of Objects").description("Array of trending products (Product + Trend = TrendingProduct)"),
                        fieldWithPath("results[].product").type("Product").description("Product Metadata"),
                        fieldWithPath("results[].trend").type("Trend").description("Trend metadata")
                )))
                .when().post("/trending-products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentResponseProducts() throws Exception {
        ObjectNode root = nodeFactory.objectNode();
        root.put("type", "click");
        root.put("lookback", "1d");
        root.put("account", 1234);
        ArrayNode campaigns = root.putArray("campaigns");
        campaigns.add(3456);
        campaigns.add(4567);
        root.put("category", "HG");
        root.put("merchant", "ebay");
        root.put("product", "lamp");


        RestAssured.given(this.spec)
                .body(objectMapper.writeValueAsString(root))
                .contentType(ContentType.JSON)
                .accept("application/json")
                .filter(document("response-product", responseFields(
                        fieldWithPath("pagination").ignored(),
                        fieldWithPath("results[].product.id").description("A unique identifier for a given product."),
                        fieldWithPath("results[].product.imageUrl").description(""),
                        fieldWithPath("results[].product.title").description(""),
                        fieldWithPath("results[].product.price").description(""),
                        fieldWithPath("results[].product.merchantName").description("Merchant name associated with extracted product content"),
                        fieldWithPath("results[].product.targetUrl").description(""),
                        fieldWithPath("results[].product.category").description("VigLink's category hierarchy for the product"),
                        fieldWithPath("results[].trend").ignored()
                )))
                .when().post("/trending-products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentResponseTrends() throws Exception {
        ObjectNode root = nodeFactory.objectNode();
        root.put("type", "click");
        root.put("lookback", "1d");
        root.put("account", 1234);
        ArrayNode campaigns = root.putArray("campaigns");
        campaigns.add(3456);
        campaigns.add(4567);
        root.put("category", "HG");
        root.put("merchant", "ebay");
        root.put("product", "lamp");


        RestAssured.given(this.spec)
                .body(objectMapper.writeValueAsString(root))
                .contentType(ContentType.JSON)
                .accept("application/json")
                .filter(document("response-trend", responseFields(
                        fieldWithPath("pagination").ignored(),
                        fieldWithPath("results[].product").ignored(),
                        fieldWithPath("results[].trend.weight").description("Relative strength of the trend"),
                        fieldWithPath("results[].trend.type").description("What weight represents")
                )))
                .when().post("/trending-products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentResponseParamsAll() throws Exception {
        ObjectNode root = nodeFactory.objectNode();
        root.put("type", "click");
        root.put("lookback", "1d");
        root.put("account", 1234);
        ArrayNode campaigns = root.putArray("campaigns");
        campaigns.add(3456);
        campaigns.add(4567);
        root.put("category", "HG");
        root.put("merchant", "ebay");
        root.put("product", "lamp");



        RestAssured.given(this.spec)
                .body(objectMapper.writeValueAsString(root))
                .contentType(ContentType.JSON)
                .accept("application/json")
                .filter(document("response-params", preprocessResponse(Preprocessors.prettyPrint()), responseFields(
                        fieldWithPath("pagination").description("The current page in the results list"),
                        fieldWithPath("pagination.page").description("Current page"),
                        fieldWithPath("pagination.total").description("The total pages returned by the query"),
                        fieldWithPath("results[]").description("Array of trending products (Product + Trend = TrendingProduct)"),
                        fieldWithPath("results[].product").description("Product Metadata (see below)"),
                        fieldWithPath("results[].product.id").description("A unique identifier of a given product. TBD how implemented"),
                        fieldWithPath("results[].product.imageUrl").description("Url containing a picture of the product"),
                        fieldWithPath("results[].product.title").description("Product Title"),
                        fieldWithPath("results[].product.price").description("Product price (in USD?)"),
                        fieldWithPath("results[].product.merchantName").description("Name of the merchant associated with extracted product content"),
                        fieldWithPath("results[].product.targetUrl").description("Page where product can be purchased"),
                        fieldWithPath("results[].product.category").description("VigLink's category hierarchy for the product"),
                        fieldWithPath("results[].trend").description("Trend metadata (see below)"),
                        fieldWithPath("results[].trend.weight").description("Relative strength of the trend (see type)"),
                        fieldWithPath("results[].trend.type").description("What the weight describes")
                )))
                .when().post("/trending-products?page=1&per_page=3")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void testRequestParamsSearch() throws Exception {
        ObjectNode root = nodeFactory.objectNode();
        root.put("type", "click");
        root.put("lookback", "1d");
        root.put("account", 1234);
        ArrayNode campaigns = root.putArray("campaigns");
        campaigns.add(3456);
        campaigns.add(4567);
        root.put("category", "HG");
        root.put("merchant", "ebay");
        root.put("product", "lamp");

        RestAssured.given(this.spec)
                .body(objectMapper.writeValueAsString(root))
                .contentType(ContentType.JSON)
                .filter(document("request-search-params", requestFields(
                        fieldWithPath("type").description("'click' | 'revenue'"),
                        fieldWithPath("lookback").description("'1h' | '1d' | '7d' | '30d'"),
                        fieldWithPath("account").description("Leave blank for network-wide."),
                        fieldWithPath("campaigns").type("array of longs").description("Campaigns to filter on"),
                        fieldWithPath("category").description("VigLink product category. E.g. 'HG>EC'"),
                        fieldWithPath("merchant").description("Merchant name or domain. Full-text search"),
                        fieldWithPath("product").description("Full-text search"))))
                .when().post("/trending-products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void testRequestParamsPagination() throws Exception {
        ObjectNode root = nodeFactory.objectNode();
        //pagination params
        root.put("type", "click");
        root.put("lookback", "1d");


        RestAssured.given(this.spec)
                .body(objectMapper.writeValueAsString(root))
                .contentType(ContentType.JSON)
                .filter(document("request-pagination-params", preprocessResponse(Preprocessors.prettyPrint()), requestParameters(
                    parameterWithName("page").description("page number to return"),
                    parameterWithName("per_page").description("total items to return per page")
                )))
                .when().post("/trending-products?page=1&per_page=100")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentRequestParamsRequired() throws Exception {
        ObjectNode root = nodeFactory.objectNode();
        //pagination params
        root.put("type", "click");
        root.put("lookback", "1d");

        RestAssured.given(this.spec)
                .body(objectMapper.writeValueAsString(root))
                .contentType(ContentType.JSON)
                .filter(document("request-min-params", preprocessResponse(Preprocessors.prettyPrint()), requestFields(
                        fieldWithPath("type").ignored(),
                        fieldWithPath("lookback").ignored())))
                .when().post("/trending-products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentErrorResponse() throws Exception {
        RestAssured.given(this.spec)
                .body("{\"type\":\"click\"}")
                .contentType(ContentType.JSON)
                .filter(document("bad-request", preprocessResponse(Preprocessors.prettyPrint())))
                .when().post("/trending-products")
                .then().assertThat().statusCode(is(400));
    }

}
