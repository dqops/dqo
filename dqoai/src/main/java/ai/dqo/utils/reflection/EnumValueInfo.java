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
package ai.dqo.utils.reflection;

/**
 * Describes a single enum value of an enumeration type.
 */
public class EnumValueInfo {
    private Enum<?> enumInstance;
    private String javaName;
    private String yamlName;
    private String displayName;

    /**
     * Returns the instance (singleton) of an enum value.
     * @return Enum instance.
     */
    public Enum<?> getEnumInstance() {
        return enumInstance;
    }

    /**
     * Stores an instance of an enum type.
     * @param enumInstance Enum instance (single value).
     */
    public void setEnumInstance(Enum<?> enumInstance) {
        this.enumInstance = enumInstance;
    }

    /**
     * Returns the java name of the enum value.
     * @return Enum value used in Java.
     */
    public String getJavaName() {
        return javaName;
    }

    /**
     * Stores the enum value as used in Java code.
     * @param javaName Enum value used in the code.
     */
    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    /**
     * Returns the enum value that is used in YAML.
     * @return YAML compatible value.
     */
    public String getYamlName() {
        return yamlName;
    }

    /**
     * Stores the value that is used in YAML.
     * @param yamlName YAML compatible name.
     */
    public void setYamlName(String yamlName) {
        this.yamlName = yamlName;
    }

    /**
     * Returns the display name used in the UI on the edit screens.
     * @return Display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Stores a display name.
     * @param displayName Display name.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
