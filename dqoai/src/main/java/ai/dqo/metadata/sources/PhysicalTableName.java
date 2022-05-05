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
package ai.dqo.metadata.sources;

import ai.dqo.core.filesystem.virtual.FileNameSanitizer;
import ai.dqo.metadata.search.StringPatternComparer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

import java.util.Objects;

/**
 * Physical table name that is a combination of a schema name and a physical table name (without any quoting or escaping).
 */
public class PhysicalTableName implements Cloneable {
    private String schemaName;
    private String tableName;

    /**
     * Creates an empty physical table name.
     */
    public PhysicalTableName() {
    }

    /**
     * Creates a physical table name given the schema name and table name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     */
    public PhysicalTableName(String schemaName, String tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    /**
     * Parses a physical table name given a base file name of a table spec yaml without the file extension.
     * @param baseFileName Base file name (encoded) to be split on the '.' dot and decoded.
     * @return Physical table name.
     */
    public static PhysicalTableName fromBaseFileName(String baseFileName) {
        int indexOfDot = baseFileName.indexOf('.');

        if (indexOfDot == 0) {
            // partially valid name (useful only for pattern matching) that is like ".tablename", it is treated as a pattern "*.tablename"
            return new PhysicalTableName("*", FileNameSanitizer.decodeFileSystemName(baseFileName.substring(1)));
        }
        else if (indexOfDot == baseFileName.length() - 1) {
            // partially valid name (useful only for pattern matching) that is like "schemaname.", it is treated as a pattern "schemaname.*"
            return new PhysicalTableName(FileNameSanitizer.decodeFileSystemName(baseFileName.substring(0, baseFileName.length() - 1)), "*");
        }
        else if (indexOfDot > 0) {
            // valid name with both schema . table parts
            return new PhysicalTableName(FileNameSanitizer.decodeFileSystemName(baseFileName.substring(0, indexOfDot)),
                    FileNameSanitizer.decodeFileSystemName(baseFileName.substring(indexOfDot + 1)));
        }

        // no dot, it is just a name, so we treat it as a table name with a search pattern '*' as the schema name
        return new PhysicalTableName("*", FileNameSanitizer.decodeFileSystemName(baseFileName));
    }

    private static boolean isPartOfDatabaseName(char c) {
        return (c == '[' || c == ']' || c == '\'' || c == '"' || c == '`');
    }

    private static String removeDatabaseIdentifiers(String name) {
        if (!isPartOfDatabaseName(name.charAt(0)) || !isPartOfDatabaseName(name.charAt(name.length() - 1))) {
            return name;
        }
        if (name.charAt(0) == '[' && name.charAt(name.length() - 1) != ']') {
            return name;
        }
        if (name.charAt(0) != '[' && name.charAt(0) != name.charAt(name.length() - 1)) {
            return name;
        }
        return name.substring(1, name.length() - 1);
    }

    /**
     * Creates a physical table name or a search pattern from a "schemaname.tablename" string given by a user int the CLI.
     * It could be a table search pattern like "schema*.tab*". Sanitization is not performed on the name.
     * @param schemaTableName Schema and table search pattern.
     * @return Physical table name or a pattern.
     */
    public static PhysicalTableName fromSchemaTableFilter(String schemaTableName) {
        int indexOfDot = schemaTableName.indexOf('.');

        if (indexOfDot == 0) {
            // partially valid name (useful only for pattern matching) that is like ".tablename", it is treated as a pattern "*.tablename"
            return new PhysicalTableName("*", removeDatabaseIdentifiers(schemaTableName.substring(1)));
        }
        else if (indexOfDot == schemaTableName.length() - 1) {
            // partially valid name (useful only for pattern matching) that is like "schemaname.", it is treated as a pattern "schemaname.*"
            return new PhysicalTableName(removeDatabaseIdentifiers(schemaTableName.substring(0, schemaTableName.length() - 1)), "*");
        }
        else if (indexOfDot > 0) {
            // valid name with both schema . table parts
            return new PhysicalTableName(removeDatabaseIdentifiers(schemaTableName.substring(0, indexOfDot)),
                    removeDatabaseIdentifiers(schemaTableName.substring(indexOfDot + 1)));
        }

        // no dot, it is just a name, so we treat it as a table name with a search pattern '*' as the schema name
        return new PhysicalTableName("*", removeDatabaseIdentifiers(schemaTableName));
    }

    /**
     * Returns a physical schema name.
     * @return Physical schema name.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets a schema name.
     * @param schemaName Schema name.
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * Returns a physical table name.
     * @return Physical table name.
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets a physical table name.
     * @param tableName Physical table name.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Checks if the physical table name is a search pattern. A search pattern contains '*' wildcard characters.
     * @return True when it is a search pattern.
     */
    @JsonIgnore
    public boolean isSearchPattern() {
        return StringPatternComparer.isSearchPattern(this.schemaName) || StringPatternComparer.isSearchPattern(this.tableName);
    }

    /**
     * Compares (equals/like) the current object with a pattern. The comparison is case-insensitive and accepts patterns in the physicalTableName parameter.
     * @param physicalTableName Search pattern to compare the current object with.
     * @return True when the names match, false otherwise.
     */
    public boolean matchPattern(PhysicalTableName physicalTableName) {
        return StringPatternComparer.matchSearchPattern(this.schemaName, physicalTableName.schemaName) &&
               StringPatternComparer.matchSearchPattern(this.tableName, physicalTableName.tableName);
    }

    /**
     * Converts a schema and table name to a file system safe "schema.table" format that is used as a base file name.
     * @return Base file name using the schema dot table names.
     */
    public String toBaseFileName() {
        StringBuilder sb = new StringBuilder();
        if (Strings.isNullOrEmpty(this.schemaName)) {
            sb.append("default");
        }
        else {
            sb.append(FileNameSanitizer.encodeForFileSystem(this.schemaName));
        }

        sb.append('.');

        if (Strings.isNullOrEmpty(this.tableName)) {
            sb.append("default");
        }
        else {
            sb.append(FileNameSanitizer.encodeForFileSystem(this.tableName));
        }

        return sb.toString();
    }

    /**
     * Returns a string representation of the object. The name is "schema.table", without file system encoding and without quoting the table name.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (Strings.isNullOrEmpty(this.schemaName)) {
            sb.append("default");
        }
        else {
            sb.append(this.schemaName);
        }

        sb.append('.');

        if (Strings.isNullOrEmpty(this.tableName)) {
            sb.append("default");
        }
        else {
            sb.append(this.tableName);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalTableName that = (PhysicalTableName) o;
        return Objects.equals(schemaName, that.schemaName) && Objects.equals(tableName, that.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schemaName, tableName);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public PhysicalTableName clone() {
        try {
            PhysicalTableName cloned = (PhysicalTableName) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }
}
