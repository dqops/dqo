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

import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

/**
 * Model of a configured schedule (on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "EffectiveScheduleModel", description = "Model of a configured schedule (enabled on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.")
public class EffectiveScheduleModel {
    @JsonPropertyDescription("Field value for a schedule group to which this schedule belongs.")
    private CheckRunScheduleGroup scheduleGroup;

    @JsonPropertyDescription("Field value for the level at which the schedule has been configured.")
    private EffectiveScheduleLevelModel scheduleLevel;

    @JsonPropertyDescription("Field value for a CRON expression defining the scheduling.")
    private String cronExpression;

    @JsonPropertyDescription("Field value for the time at which the scheduled checks will be executed.")
    private ZonedDateTime timeOfExecution;

    @JsonPropertyDescription("Field value for the time left until the execution of scheduled checks.")
    private Duration timeUntilExecution;

    @JsonPropertyDescription("Field value stating if the schedule has been explicitly disabled.")
    private Boolean disabled;

    /**
     * Creates {@link EffectiveScheduleModel} based on provided <code>scheduleSpec</code>.
     * @param scheduleSpec                 Schedule spec on which to base the model.
     * @param scheduleGroup                Schedule group to which the schedule should belong.
     * @param scheduleLevel                Schedule level on which the schedule has been configured.
     * @param specToZonedDateTimeConverter Function extracting the date from <code>scheduleSpec</code>
     *                                     that will be regarded as the time of next execution, if scheduling is not disabled.
     * @return {@link EffectiveScheduleModel} instance based on the <code>scheduleSpec</code>.
     */
    public static EffectiveScheduleModel fromMonitoringScheduleSpec(
            MonitoringScheduleSpec scheduleSpec,
            CheckRunScheduleGroup scheduleGroup,
            EffectiveScheduleLevelModel scheduleLevel,
            Function<MonitoringScheduleSpec, ZonedDateTime> specToZonedDateTimeConverter) {
        EffectiveScheduleModel effectiveScheduleModel = new EffectiveScheduleModel();
        effectiveScheduleModel.scheduleGroup = scheduleGroup;
        effectiveScheduleModel.scheduleLevel = scheduleLevel;
        effectiveScheduleModel.cronExpression = scheduleSpec.getCronExpression();

        if (!scheduleSpec.isDisabled()) {
            if (specToZonedDateTimeConverter != null) {
                effectiveScheduleModel.timeOfExecution = specToZonedDateTimeConverter.apply(scheduleSpec);
            }

            if (effectiveScheduleModel.timeOfExecution != null) {
                effectiveScheduleModel.timeUntilExecution =
                        Duration.between(ZonedDateTime.now(), effectiveScheduleModel.timeOfExecution)
                                .truncatedTo(ChronoUnit.SECONDS);
            }
        }
        effectiveScheduleModel.disabled = scheduleSpec.isDisabled();

        return effectiveScheduleModel;
    }
}
