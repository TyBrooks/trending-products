package com.viglink.trendingproducts.type;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;

@JsonDeserialize(using = LookbackType.LookbackTypeDeserializer.class)
public enum LookbackType {

    ONE_HOUR("1h"),
    ONE_DAY("1d"),
    SEVEN_DAYS("7d"),
    THIRTY_DAYS("30d");

    private String code;

    LookbackType(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    public class LookbackTypeDeserializer extends JsonDeserializer<LookbackType> {
        @Override
        public LookbackType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return LookbackType.valueOf(jsonParser.getText());
        }
    }
}
