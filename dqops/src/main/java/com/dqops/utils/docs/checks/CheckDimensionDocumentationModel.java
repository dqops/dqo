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
     * List of checks, grouped by similar checks.
     */
    private List<SimilarChecksDocumentationModel> checkGroups = new ArrayList<>();
}
