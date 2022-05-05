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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Marker interface applied to specification objects that should not be rendered to YAML because they contain default values
 * and the rendered YAML will be a mix of YAML and JSON (a node will look like:  node_name: {} )
 */
@JsonIgnoreProperties("default")
public interface YamlNotRenderWhenDefault {
    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    boolean isDefault();
}
