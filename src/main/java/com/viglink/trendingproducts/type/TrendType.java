package com.viglink.trendingproducts.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;

@JsonDeserialize(using = TrendType.TrendTypeDeserializer.class)
public enum TrendType {

    CLICK("click"),
    REVENUE("revenue");

    private String jsonName;

    TrendType(String jsonName) {
        this.jsonName = jsonName;
    }

    @JsonValue
    public String getJsonName() {
        return jsonName;
    }

    @Override
    public String toString() {
        return jsonName;
    }

    public class TrendTypeDeserializer extends JsonDeserializer<TrendType> {
        @Override
        public TrendType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return TrendType.valueOf(jsonParser.getText());
        }
    }
}
