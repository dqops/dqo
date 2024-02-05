package com.dqops.connectors.duckdb;

import java.util.HashMap;
import java.util.Map;

/**
 * Column type mappings to duckdb types.
 */
public class DuckDbTypesMappings {
    /**
     * Type mappings between tablesaw columns types and duck db.
     */
    public static final Map<String, String> CSV_TYPES_TO_DUCK_DB = new HashMap<>(){{
        put("LOCAL_DATE", "DATE");
    }};
}
