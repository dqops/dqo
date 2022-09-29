package ai.dqo.metadata.fields;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of supported field types that are used as sensor or rule parameters.
 */
public enum ParameterDataType {
    @JsonProperty("string")
    string_type,

    @JsonProperty("boolean")
    boolean_type,

    @JsonProperty("integer")
    integer_type,

    @JsonProperty("long")
    long_type,

    @JsonProperty("double")
    double_type,

    @JsonProperty("instant")
    instant_type,

    @JsonProperty("column_name")
    column_name_type, // this is a string, but should match a known column name from the same table

    @JsonProperty("enum")
    enum_type,

    @JsonProperty("string_list")
    string_list_type,

    @JsonProperty("object")
    object_type
}
