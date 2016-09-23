package com.viglink.trendingproducts;

import com.google.common.collect.Maps;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.restassured.operation.preprocess.RestAssuredPreprocessors;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
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
    public void documentRequestSearchParams() throws Exception {
        Map<String, Object> params = generateMinParams();
        addSearchParams(params);

        RestAssured.given(this.spec)
                .parameters(params)
                .filter(document("request-search-params", requestParameters(
                        parameterWithName("trend").description("'click' | 'revenue'"),
                        parameterWithName("from").description("'1h' | '1d' | '7d' | '30d'"),
                        parameterWithName("until").description("'now'"),
                        parameterWithName("account").description("Id. Leave blank for network-wide"),
                        parameterWithName("campaigns").description("Array Field: user Ids"),
                        parameterWithName("category").description("VigLink product category"),
                        parameterWithName("merchant").description("Merchant name or domain. Full-text search"),
                        parameterWithName("title").description("Full-text search"))))
                .when().get("/products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentRequestPaginationParams() throws Exception {
        Map<String, Object> params = generateMinParams();
        addPaginationParams(params);

        RestAssured.given(this.spec)
                .parameters(params)
                .filter(document("request-pagination-params", preprocessRequest(RestAssuredPreprocessors.modifyUris().host("qa-trending-1.va.ec2.viglink.com").port(8080)),
                        preprocessResponse(Preprocessors.prettyPrint(), RestAssuredPreprocessors.modifyUris().host("qa-trending-1.va.ec2.viglink.com").port(8080)), requestParameters(
                                parameterWithName("page").description("page number to return"),
                                parameterWithName("per_page").description("total items to return per page"),
                                parameterWithName("trend").ignored(),
                                parameterWithName("from").ignored()
                        )))
                .when().get("/products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentRequestRequiredParams() throws Exception {
        Map<String, Object> params = generateMinParams();

        RestAssured.given(this.spec)
                .parameters(params)
                .filter(document("request-min-params", preprocessRequest(RestAssuredPreprocessors.modifyUris().host("qa-trending-1.va.ec2.viglink.com").port(8080)),
                        preprocessResponse(Preprocessors.prettyPrint(), RestAssuredPreprocessors.modifyUris().host("qa-trending-1.va.ec2.viglink.com").port(8080))))
                .when().get("/products")
                .then().assertThat().statusCode(is(200));
    }


    @Test
    public void documentResponseOrganization() throws Exception {
        Map<String, Object> params = generateMinParams();
        addSearchParams(params);

        RestAssured.given(this.spec)
                .parameters(params)
                .accept("application/json")
                .filter(document("response-high-level", responseFields(
                        fieldWithPath("pagination").description("Pagination Metadata"),
                        fieldWithPath("results[]").type("Array of Products").description("See below."),
                        fieldWithPath("results[].trends[]").type("Array of Trends").description("See below.")
                )))
                .when().get("/products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentTypeProducts() throws Exception {
        Map<String, Object> params = generateMinParams();
        addSearchParams(params);

        RestAssured.given(this.spec)
                .parameters(params)
                .accept("application/json")
                .filter(document("response-product", responseFields(
                        fieldWithPath("pagination").ignored(),
                        fieldWithPath("results[].id").description("A unique identifier for a given "),
                        fieldWithPath("results[].imageUrl").description(""),
                        fieldWithPath("results[].title").description(""),
                        fieldWithPath("results[].price").description(""),
                        fieldWithPath("results[].merchantName").description("Merchant name associated with extracted content"),
                        fieldWithPath("results[].targetUrl").description(""),
                        fieldWithPath("results[].category").description("VigLink's category hierarchy"),
                        fieldWithPath("results[].trends[]").ignored()
                )))
                .when().get("/products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentTypeTrends() throws Exception {
        Map<String, Object> params = generateMinParams();
        addSearchParams(params);


        RestAssured.given(this.spec)
                .parameters(params)
                .accept("application/json")
                .filter(document("response-trend", responseFields(
                        fieldWithPath("pagination").ignored(),
                        fieldWithPath("results[]").ignored(),
                        fieldWithPath("results[].trends[].weight").description("Relative strength of the trend"),
                        fieldWithPath("results[].trends[].type").description("What weight represents")
                )))
                .when().get("/products")
                .then().assertThat().statusCode(is(200));
    }

    @Test
    public void documentResponseError() throws Exception {
        RestAssured.given(this.spec)
                .param("trend", "click")
                .contentType(ContentType.JSON)
                .filter(document("bad-request", preprocessResponse(Preprocessors.prettyPrint())))
                .when().get("/products")
                .then().assertThat().statusCode(is(400));
    }

    @Test
    public void testResponseParamsAll() throws Exception {
        Map<String, Object> params = generateMinParams();
        addSearchParams(params);
        addPaginationParams(params);

        RestAssured.given(this.spec)
                .parameters(params)
                .accept("application/json")
                .filter(document("response-params", preprocessResponse(Preprocessors.prettyPrint()), responseFields(
                        fieldWithPath("pagination").description("The current page in the results list"),
                        fieldWithPath("pagination.page").description("Current page"),
                        fieldWithPath("pagination.perPage").description("The total pages returned by the query"),
                        fieldWithPath("pagination.totalItems").description("total number of items"),
                        fieldWithPath("results[].id").description("A unique identifier of a given product. TBD how implemented"),
                        fieldWithPath("results[].imageUrl").description("Url containing a picture of the product"),
                        fieldWithPath("results[].title").description("Product Title"),
                        fieldWithPath("results[].price").description("Product price (in USD?)"),
                        fieldWithPath("results[].merchantName").description("Name of the merchant associated with extracted product content"),
                        fieldWithPath("results[].targetUrl").description("Page where product can be purchased"),
                        fieldWithPath("results[].category").description("VigLink's category hierarchy for the product"),
                        fieldWithPath("results[].trends[]").description("Trend metadata (see below)"),
                        fieldWithPath("results[].trends[].weight").description("Relative strength of the trend (see type)"),
                        fieldWithPath("results[].trends[].type").description("What the weight describes")
                )))
                .when().get("/products")
                .then().assertThat().statusCode(is(200));
    }

    private Map<String, Object> generateMinParams() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("trend", "click");
        map.put("from", "1d");

        return map;
    }

    private void addSearchParams(Map<String, Object> current) {
        current.put("until", "now");
        current.put("account", 1234);
        current.put("campaigns", 345); // TODO test array params
        current.put("category", "HG");
        current.put("merchant", "ebay");
        current.put("title", "lamp");
    }

    private void addPaginationParams(Map<String, Object> current) {
        current.put("page", 2);
        current.put("per_page", 2);
    }

}
