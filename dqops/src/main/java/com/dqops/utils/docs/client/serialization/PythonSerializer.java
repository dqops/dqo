/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
package com.dqops.utils.docs.client.serialization;

import org.springframework.stereotype.Component;

/**
 * Python serializer.
 */
@Component
public interface PythonSerializer {
    /**
     * Serializes an object as a Python snippet creating a new instance of this object.
     * @param source Source object to be serialized.
     * @return Object serialized as a Python snippet.
     */
    String serialize(Object source);

    /**
     * Serializes an object as a Python snippet creating a new instance of this object, using a pretty print writer.
     * @param source Source object to be serialized.
     * @return Object serialized as a Python snippet, formatted.
     */
     String serializePrettyPrint(Object source);
}
