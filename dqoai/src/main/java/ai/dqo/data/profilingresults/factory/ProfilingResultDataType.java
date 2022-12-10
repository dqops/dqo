package ai.dqo.data.profilingresults.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of data types that were detected as the profile result.
 */
public enum ProfilingResultDataType {
    @JsonProperty("null")
    NULL("null"),

    @JsonProperty("boolean")
    BOOLEAN("boolean"),

    @JsonProperty("string")
    STRING("string"),

    @JsonProperty("integer")
    INTEGER("integer"),

    @JsonProperty("float")
    FLOAT("float"),

    @JsonProperty("date")
    DATE("date"),

    @JsonProperty("datetime")
    DATETIME("datetime"),

    @JsonProperty("instant")
    INSTANT("instant"),

    @JsonProperty("time")
    TIME("time");

    private String name;

    ProfilingResultDataType(String name) {
        this.name = name;
    }

    /**
     * Returns the profiler name.
     * @return Profiler name.
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a profiler result data type from the name.
     * @param name result data type name.
     * @return Profiler result type.
     */
    public static ProfilingResultDataType fromName(String name) {
        switch (name) {
            case "null":
                return NULL;
            case "boolean":
                return BOOLEAN;
            case "string":
                return STRING;
            case "integer":
                return INTEGER;
            case "float":
                return FLOAT;
            case "date":
                return DATE;
            case "datetime":
                return DATETIME;
            case "instant":
                return INSTANT;
            case "time":
                return TIME;
            default:
                throw new IllegalArgumentException("Unsupported value " + name);
        }
    }
}
