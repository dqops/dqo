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
package ai.dqo.services.check.mapping.models;

import ai.dqo.metadata.search.CheckSearchFilters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * UI model that returns the form definition and the form data to edit all checks within a single category.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIQualityCategoryModel", description = "UI model that returns the form definition and the form data to edit all checks within a single category.")
public class UIQualityCategoryModel {
    @JsonPropertyDescription("Data quality check category name.")
    private String category;

    @JsonPropertyDescription("Help text that describes the category.")
    private String helpText;

    @JsonPropertyDescription("List of data quality checks within the category.")
    private List<UICheckModel> checks = new ArrayList<>();

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    public UIQualityCategoryModel() {
    }

    /**
     * Creates a UI check category model, given a category name.
     * @param category Category name.
     */
    public UIQualityCategoryModel(String category) {
        this.category = category;
    }
}
