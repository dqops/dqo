/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
    /**
     * Check category.
     */
    @JsonPropertyDescription("Check category.")
    private String checkCategory;

    /**
     * Data quality check name that is used in YAML file. Identifies the data quality check.
     */
    @JsonPropertyDescription("Data quality check name that is used in YAML.")
    private String checkName;

    /**
     * Help text that describes the data quality check.
     */
    @JsonPropertyDescription("Help text that describes the data quality check.")
    private String helpText;

    /**
     * True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.
     */
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
