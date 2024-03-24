package com.dqops.metadata.sources.fileformat.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum JsonRecordsType {
    @JsonProperty("auto")
    auto("auto"),

    @JsonProperty("true")
    true_value("true"),

    @JsonProperty("false")
    false_value("false");

    private final String value;

    JsonRecordsType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
