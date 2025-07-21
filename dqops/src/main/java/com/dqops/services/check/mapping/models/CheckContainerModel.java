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

import com.dqops.checks.DefaultRuleSeverityLevel;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.id.HierarchyIdModel;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model that returns the form definition and the form data to edit all data quality checks divided by categories.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckContainerModel", description = "Model that returns the form definition and the form data to edit all data quality checks divided by categories.")
public class CheckContainerModel {
    @JsonPropertyDescription("List of all data quality categories that contain data quality checks inside.")
    private List<QualityCategoryModel> categories = new ArrayList<>();

    @JsonPropertyDescription("Model of configured schedule enabled on the check container.")
    private EffectiveScheduleModel effectiveSchedule;

    @JsonPropertyDescription("State of the effective scheduling on the check container.")
    private ScheduleEnabledStatusModel effectiveScheduleEnabledStatus;

    @JsonPropertyDescription("The name of the column that partitioned checks will use for the time period partitioning. Important only for partitioned checks.")
    private String partitionByColumn;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check container")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

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

    public CheckContainerModel() {
    }

    /**
     * Iterates over categories and removes checks that are already configured. This method is used by the rule mining module.
     */
    public void dropConfiguredChecks() {
        for (QualityCategoryModel categoryModel : this.getCategories()) {
            categoryModel.dropConfiguredChecks();
        }
    }

    /**
     * Drops empty categories that do not have any checks.
     */
    public void dropEmptyCategories() {
        ArrayList<QualityCategoryModel> copyOfCategories = new ArrayList<>(this.categories);
        for (QualityCategoryModel categoryModel : copyOfCategories) {
            if (categoryModel.getChecks().isEmpty()) {
                this.categories.remove(categoryModel);
            }
        }
    }

    /**
     * Changes the default rule severity that is proposed in the check editor to a given severity level.
     * It is used by the profiling default checks to disable proposing rule configuration, and focus on data quality assessment.
     * @param defaultRuleSeverityLevel Default rule severity level to assign to all checks.
     */
    public void changeDefaultSeverityLevel(DefaultRuleSeverityLevel defaultRuleSeverityLevel) {
        for (QualityCategoryModel categoryModel : this.getCategories()) {
            for (CheckModel checkModel : categoryModel.getChecks()) {
                checkModel.setDefaultSeverity(defaultRuleSeverityLevel);
            }
        }
    }

    /**
     * Collects the hierarchy ids of all configured checks in the model.
     * @return Hierarchy ids of all configured checks.
     */
    public List<HierarchyIdModel> collectConfiguredChecksHierarchyIds() {
        List<HierarchyIdModel> resultList = new ArrayList<>();

        for (QualityCategoryModel categoryModel : this.getCategories()) {
            for (CheckModel checkModel : categoryModel.getChecks()) {
                if (checkModel.isConfigured() && checkModel.getCheckSpec() != null && checkModel.getCheckSpec().getHierarchyId() != null) {
                    HierarchyIdModel hierarchyIdModel = checkModel.getCheckSpec().getHierarchyId().toHierarchyIdModel();
                    resultList.add(hierarchyIdModel);
                }
            }
        }

        return resultList;
    }

    /**
     * Removes the "comparisons" category, because we cannot copy these checks without creating a new comparison configuration.
     */
    public void removeComparisonCategory() {
        this.categories.removeIf(categoryModel -> Objects.equals(categoryModel.getCategory(), AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME));
    }

    public static class CheckContainerModelSampleFactory implements SampleValueFactory<CheckContainerModel> {
        @Override
        public CheckContainerModel createSample() {
            return new CheckContainerModel() {{
                setCategories(List.of(new QualityCategoryModel.QualityCategoryModelSampleFactory().createSample()));
            }};
        }
    }
}
