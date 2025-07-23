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
 * Container object with a list of checks within the same data quality dimension.
 */
@Data
public class CheckDimensionDocumentationModel {
    /**
     * Check target ('table' or 'column')
     */
    private String target;

    /**
     * The name of the data quality dimension from the primary check class.
     */
    private String qualityDimension;

    /**
     * The name of the data quality dimension from the primary check class, but in lower case.
     */
    private String qualityDimensionLowercase;

    /**
     * List of checks, grouped by similar checks.
     */
    private List<SimilarChecksDocumentationModel> checkGroups = new ArrayList<>();
}
