/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

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
