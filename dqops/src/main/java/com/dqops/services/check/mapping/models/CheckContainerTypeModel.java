/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping.models;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Model identifying the check type and timescale of checks belonging to a container.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckContainerTypeModel", description = "Model identifying the check type and timescale of checks belonging to a container.")
public class CheckContainerTypeModel {

    public CheckContainerTypeModel(@NotNull CheckType checkType, CheckTimeScale checkTimeScale) {
        this.checkType = checkType;
        this.checkTimeScale = checkTimeScale;
    }

    @JsonPropertyDescription("Check type.")
    private CheckType checkType;

    @JsonPropertyDescription("Check timescale.")
    private CheckTimeScale checkTimeScale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckContainerTypeModel that = (CheckContainerTypeModel) o;

        if (checkType != that.checkType) return false;
        return checkTimeScale == that.checkTimeScale;
    }

    @Override
    public int hashCode() {
        int result = checkType.hashCode();
        result = 31 * result + (checkTimeScale != null ? checkTimeScale.hashCode() : 0);
        return result;
    }
}
