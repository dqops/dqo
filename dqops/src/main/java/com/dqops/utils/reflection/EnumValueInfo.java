/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.reflection;

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
