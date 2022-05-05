/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.utils.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.Console;
import java.io.IOException;

/**
 * Custom YAML serializer added to columns that return objects that should be not rendered in YAML because when they are empty (only default values),
 * then the rendered value would be like:  node_name: {}  which will make editing YAML much harder (it will become a JSON with JSON rules)
 */
public class IgnoreEmptyYamlSerializer extends JsonSerializer<YamlNotRenderWhenDefault> {
    @Override
    public void serialize(YamlNotRenderWhenDefault o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (o == null || o.isDefault()) {
            return;
        }

        jsonGenerator.writeObject(o);
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, YamlNotRenderWhenDefault value) {
        try {
            return value == null || value.isDefault();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
}
