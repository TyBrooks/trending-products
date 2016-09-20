package com.viglink.trendingproducts;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.viglink.trendingproducts.controller.TrendingProductsParameters;
import com.viglink.trendingproducts.type.LookbackType;
import com.viglink.trendingproducts.type.TrendType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.restdocs.JUnitRestDocumentation;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

public class ApiDocumentation {

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
    public void testTrendingProductsResponseParams() {
        RestAssured.given(this.spec)
                .accept("application/json")
                .filter(document("response-params", responseFields(
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
                .when().get("/trending-products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void testTrendingProductsRequestParams() {
        TrendingProductsParameters parameters = new TrendingProductsParameters(TrendType.CLICK, LookbackType.ONE_DAY, 1234L,
                new Long[] { 3456L, 7891L }, "HG", "ebay", "lamp");

        RestAssured.given(this.spec)
                .body(parameters)
                .contentType(ContentType.JSON)
                .filter(document("request-params", requestParameters(
                        parameterWithName("page").description("The page to retrieve"),
                        parameterWithName("per_page").description("Entries per page"),
                        parameterWithName("type").description("'click' | 'revenue'"),
                        parameterWithName("lookback").description("'1h' | '1d' | '7d' | '30d'"),
                        parameterWithName("account").description("account id. Leave blank for network-wide."),
                        parameterWithName("campaigns").description("Campaigns to filter on"),
                        parameterWithName("category").description("category"),
                        parameterWithName("merchant").description("Merchant name or domain"),
                        parameterWithName("product").description("Product title"))))
                .when().post("/trending-products")
                .then().assertThat().statusCode(is(200));
    }

}
