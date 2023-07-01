/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.cli.terminal;

import java.util.*;

/**
 * Formatted table DTO.
 */
public class FormattedTableDto<T> {
    private final List<T> rows = new ArrayList<>();
    private final LinkedHashMap<String, Object> headers = new LinkedHashMap<>();

    public FormattedTableDto() {
    }

    /**
     * Creates a formatted table dto, given a list.
     * @param rows Collection of rows.
     */
    public FormattedTableDto(Collection<T> rows) {
        this.rows.addAll(rows);
    }

    /**
     * Creates a formatted table dto, given a list of dtos and the dictionary of field to header names.
     * @param rows Collection of rows.
     */
    public FormattedTableDto(Collection<T> rows, Map<String, String> headers) {
        this.rows.addAll(rows);
        for (Map.Entry<String, String> entries : headers.entrySet()) {
            this.headers.put(entries.getKey(), entries.getValue());
        }
    }


    /**
     * Returns a list of rows.
     * @return List of rows.
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * Returns a dictionary of column headers to be used in a rendered table.
     * @return Headers.
     */
    public LinkedHashMap<String, Object> getHeaders() {
        return headers;
    }

    /**
     * Add row(s) to the list.
     * @param rows Rows to be added.
     */
    public void addRows(T... rows) {
        for(T row : rows) {
            this.rows.add(row);
        }
    }

    /**
     * Adds all rows from a given collection.
     * @param source Source collection.
     */
    public void addRows(Collection<T> source) {
		this.rows.addAll(source);
    }

    /**
     * Adds a column definition to the rendered table.
     * @param objectFieldName Field name in the T object that will be rendered.
     * @param columnDisplayHeader Column display name.
     */
    public void addColumnHeader(String objectFieldName, String columnDisplayHeader) {
		this.headers.put(objectFieldName, columnDisplayHeader);
    }
}
