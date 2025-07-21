/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.client;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;

public class OpenApiUtils {
    private static String applicationJson = "application/json";
    private static String textPlain = "text/plain";

    public static String getEffective$refFromContent(Content content) {
        Schema<?> schema = getEffectiveSchemaFromContent(content);
        return getEffective$refFromSchema(schema);
    }
    public static Schema<?> getEffectiveSchemaFromContent(Content content) {
        if (content == null) {
            return null;
        }

        Schema<?> returnSchema = null;
        if (content.containsKey(applicationJson)) {
            returnSchema = content.get(applicationJson).getSchema();
        } else if (content.containsKey(textPlain)) {
            returnSchema = content.get(textPlain).getSchema();
        }
        return returnSchema;
    }

    public static String getEffective$refFromSchema(Schema<?> schema) {
        if (schema == null) {
            return null;
        }

        if (schema instanceof StringSchema) {
            return "string";
        }

        String $ref = schema.get$ref();
        if ($ref == null && schema.getItems() != null){
            return getEffective$refFromSchema(schema.getItems());
        }
        return $ref;
    }
}
