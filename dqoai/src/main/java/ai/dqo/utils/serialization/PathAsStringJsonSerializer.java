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

import java.io.IOException;
import java.nio.file.Path;

/**
 * Custom JSON serializer that serializes {@link Path} as a string.
 */
public class PathAsStringJsonSerializer extends JsonSerializer<Path> {
    @Override
    public void serialize(Path o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (o == null) {
            return;
        }

        String pathAsLinuxString = o.toString().replace('\\', '/');
        jsonGenerator.writeString(pathAsLinuxString);
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, Path value) {
        return value == null;
    }
}
