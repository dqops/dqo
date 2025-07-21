/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.checkresults.models;

import com.dqops.checks.CheckType;
import com.dqops.utils.docs.generators.SampleListUtility;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Check detailed results. Returned in the context of a single data group, with a supplied list of other data groups.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultsListModel {
    @JsonPropertyDescription("Check hash")
    private long checkHash;

    @JsonPropertyDescription("Check category name")
    private String checkCategory;

    @JsonPropertyDescription("Check name")
    private String checkName;

    @JsonPropertyDescription("Check display name")
    private String checkDisplayName;

    @JsonPropertyDescription("Check type")
    private CheckType checkType;

    @JsonPropertyDescription("Data groups list")
    private List<String> dataGroups;

    @JsonPropertyDescription("Selected data group")
    private String dataGroup;

    @JsonPropertyDescription("Single check results")
    private List<CheckResultEntryModel> checkResultEntries = new ArrayList<>();

    public static class CheckResultsListModelSampleFactory implements SampleValueFactory<CheckResultsListModel> {
        @Override
        public CheckResultsListModel createSample() {
            List<CheckResultEntryModel> checkResultEntryModels = SampleListUtility.generateList(
                    CheckResultEntryModel.class, 4,

                    CheckResultEntryModel::getId,
                    s -> Long.toString(new Random(Long.parseLong(s)).nextLong()),
                    CheckResultEntryModel::setId,

                    CheckResultEntryModel::getExecutedAt,
                    i -> i.plus(1L, ChronoUnit.HOURS),
                    CheckResultEntryModel::setExecutedAt,

                    CheckResultEntryModel::getTimePeriod,
                    d -> d.plusHours(1L),
                    CheckResultEntryModel::setTimePeriod
            );
            for (int i = 0; i < checkResultEntryModels.size(); ++i) {
                CheckResultEntryModel c = checkResultEntryModels.get(i);
                c.setActualValue(c.getActualValue() + 10*i);
                c.setExpectedValue(c.getExpectedValue() + 10*i);
                c.setWarningLowerBound(c.getWarningLowerBound() + 10*i);
                c.setWarningUpperBound(c.getWarningUpperBound() + 10*i);
                c.setErrorLowerBound(c.getErrorLowerBound() + 10*i);
                c.setErrorUpperBound(c.getErrorUpperBound() + 10*i);
                c.setFatalLowerBound(c.getFatalLowerBound() + 10*i);
                c.setFatalUpperBound(c.getFatalUpperBound() + 10*i);
            }

            CheckResultEntryModel c = checkResultEntryModels.get(0);

            return new CheckResultsListModel() {{
                setCheckHash(c.getCheckHash());
                setCheckCategory(c.getCheckCategory());
                setCheckName(c.getCheckName());
                setCheckDisplayName(c.getCheckDisplayName());
                setCheckType(c.getCheckType());
                setDataGroup(c.getDataGroup());
                setCheckResultEntries(checkResultEntryModels);
            }};
        }
    }
}
