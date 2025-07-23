/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.matching;

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.services.check.mapping.models.CheckModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Describes a single check that is similar to other checks in other check types.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SimilarCheckModel", description = "Model that identifies a similar check in another category or another type of check (monitoring, partition).")
public class SimilarCheckModel {
    /**
     * The check target (table or column).
     */
    @JsonPropertyDescription("The check target (table or column).")
    private CheckTarget checkTarget;

    /**
     * The check type.
     */
    @JsonPropertyDescription("The check type.")
    private CheckType checkType;

    /**
     * The time scale (daily, monthly). The time scale is optional and can be null (for profiling checks).
     */
    @JsonPropertyDescription("The time scale (daily, monthly). The time scale is optional and can be null (for profiling checks).")
    private CheckTimeScale timeScale;

    /**
     * The check's category.
     */
    @JsonPropertyDescription("The check's category.")
    private String category;

    /**
     * The similar check name in another category.
     */
    @JsonPropertyDescription("Similar check name in another category.")
    private String checkName;

    /**
     * A check model.
     */
    @JsonIgnore
    private CheckModel checkModel;

    public SimilarCheckModel() {
    }

    /**
     * Creates a similar check model.
     * @param checkTarget Check target (table or column).
     * @param checkType   Check type.
     * @param timeScale   Time scale (optional, null for experiments).
     * @param category    Check category name.
     * @param checkModel  Check model with the check name and additional information about the check.
     */
    public SimilarCheckModel(CheckTarget checkTarget,
                             CheckType checkType,
                             CheckTimeScale timeScale,
                             String category,
                             CheckModel checkModel) {
        this.checkTarget = checkTarget;
        this.checkType = checkType;
        this.timeScale = timeScale;
        this.category = category;
        this.checkName = checkModel.getCheckName();
        this.checkModel = checkModel;
    }
}
