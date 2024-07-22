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
package com.dqops.services.check.mapping.models;

import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model that returns the form definition and the form data to edit all checks within a single category.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "QualityCategoryModel", description = "Model that returns the form definition and the form data to edit all checks within a single category.")
public class QualityCategoryModel {
    /**
     * Data quality check category name.
     */
    @JsonPropertyDescription("Data quality check category name.")
    private String category;

    /**
     * The name of the reference table configuration used for a cross table data comparison (when the category is 'comparisons').
     */
    @JsonPropertyDescription("The name of the reference table configuration used for a cross table data comparison (when the category is 'comparisons').")
    private String comparisonName;

    /**
     * The name of the column in the reference table that is compared.
     */
    @JsonPropertyDescription("The name of the column in the reference table that is compared.")
    private String compareToColumn;

    /**
     * Help text that describes the category.
     */
    @JsonPropertyDescription("Help text that describes the category.")
    private String helpText;

    /**
     * List of data quality checks within the category.
     */
    @JsonPropertyDescription("List of data quality checks within the category.")
    private List<CheckModel> checks = new ArrayList<>();

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to start the job.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this quality category.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this quality category.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    public QualityCategoryModel() {
    }

    /**
     * Creates a check category model, given a category name.
     * @param category Category name.
     */
    public QualityCategoryModel(String category) {
        this.category = category;
    }

    public static class QualityCategoryModelSampleFactory implements SampleValueFactory<QualityCategoryModel> {
        @Override
        public QualityCategoryModel createSample() {
            return new QualityCategoryModel() {{
                setCategory(SampleStringsRegistry.getCategoryName());
                setHelpText(SampleStringsRegistry.getHelpText());
                setChecks(List.of(new CheckModel.CheckModelSampleFactory().createSample()));
            }};
        }
    }
}
