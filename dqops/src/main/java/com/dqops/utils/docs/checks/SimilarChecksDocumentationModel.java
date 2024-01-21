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
package com.dqops.utils.docs.checks;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Documentation model that describes multiple similar checks.
 */
@Data
public class SimilarChecksDocumentationModel {
    /**
     * Check description extracted from the JavaDoc comment for the whole check definition class.
     */
    private String checkSpecClassJavaDoc;

    /**
     * Sensor target ('table' or 'column')
     */
    private String target;

    /**
     * Sensor category.
     */
    private String category;

    /**
     * Similar check root name (e.g. daily_partition_row_count -> row_count).
     */
    private String primaryCheckName;

    /**
     * True for standard data quality checks, false for advanced.
     */
    private boolean standard;

    /**
     * The name of the data quality dimension from the primary check class.
     */
    private String qualityDimension;

    /**
     * List of all similar checks.
     */
    private List<CheckDocumentationModel> allChecks = new ArrayList<>();
}
