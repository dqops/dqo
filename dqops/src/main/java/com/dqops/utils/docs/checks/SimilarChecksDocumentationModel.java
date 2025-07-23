/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
     * The name of the markdown file with the documentation of the category.
     */
    private String categoryPageName;

    /**
     * Similar check root name (e.g. daily_partition_row_count -> row_count).
     */
    private String primaryCheckName;

    /**
     * Friendly name of a data quality check.
     */
    private String friendlyName;

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
