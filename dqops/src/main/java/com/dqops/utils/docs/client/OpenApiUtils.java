/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
