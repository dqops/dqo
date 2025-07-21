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
 * Simplistic model that returns the list of data quality checks, their names, categories and "configured" flag.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckContainerListModel", description = "Simplistic model that returns the list of data quality checks, their names, categories and \"configured\" flag.")
public class CheckContainerListModel {
    /**
     * Simplistic list of all data quality checks.
     */
    @JsonPropertyDescription("Simplistic list of all data quality checks.")
    private List<CheckListModel> checks = new ArrayList<>();

    /**
     * Boolean flag that decides if the current user can edit the check.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can edit the check.")
    private boolean canEdit;

    /**
     * Boolean flag that decides if the current user can run checks.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can run checks.")
    private boolean canRunChecks;

    /**
     * Boolean flag that decides if the current user can delete data (results).
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can delete data (results).")
    private boolean canDeleteData;

    public CheckContainerListModel() {
    }

    public static class CheckContainerListModelSampleFactory implements SampleValueFactory<CheckContainerListModel> {
        @Override
        public CheckContainerListModel createSample() {
            int checkCount = 3;
            int checkCategoryCount = 2;
            List<CheckListModel> checkListModels = new ArrayList<>(checkCount);
            for (int i = 1; i <= checkCount; ++i) {
                for (int j = 1; j <= checkCategoryCount; ++j) {
                    CheckListModel checkListModel = new CheckListModel.CheckListModelSampleFactory().createSample();
                    checkListModel.setCheckName(checkListModel.getCheckName() + "_" + i);
                    checkListModel.setCheckCategory(checkListModel.getCheckCategory() + "_" + j);
                    checkListModels.add(checkListModel);
                }
            }
            return new CheckContainerListModel() {{
                setChecks(checkListModels);
                setCanRunChecks(true);
                setCanEdit(false);
                setCanDeleteData(true);
            }};
        }
    }
}
