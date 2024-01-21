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
package com.dqops.services.check.mapping.basicmodels;

import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Simplistic model that returns a single data quality check, its name and "configured" flag.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckListModel", description = "Simplistic model that returns a single data quality check, its name and \"configured\" flag")
public class CheckListModel implements Comparable<CheckListModel> {
    @JsonPropertyDescription("Check category.")
    private String checkCategory;

    /**
     * Data quality check name that is used in YAML file. Identifies the data quality check.
     */
    @JsonPropertyDescription("Data quality check name that is used in YAML.")
    private String checkName;

    @JsonPropertyDescription("Help text that describes the data quality check.")
    private String helpText;

    @JsonPropertyDescription("True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.")
    private boolean configured;

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull CheckListModel o) {
        if (this.configured && !o.configured) {
            return -1;
        } else if (!this.configured && o.configured) {
            return 1;
        }

        return this.checkName.compareTo(o.checkName);
    }

    public static class CheckListModelSampleFactory implements SampleValueFactory<CheckListModel> {
        @Override
        public CheckListModel createSample() {
            return new CheckListModel() {{
                setCheckCategory(SampleStringsRegistry.getCategoryName());
                setCheckName(SampleStringsRegistry.getCheckName());
                setHelpText(SampleStringsRegistry.getHelpText());
                setConfigured(true);
            }};
        }
    }
}
