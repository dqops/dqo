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

import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

/**
 * Model of a configured schedule (on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIEffectiveScheduleModel", description = "Model of a configured schedule (enabled on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.")
public class UIEffectiveScheduleModel {
    @JsonPropertyDescription("Field value for a schedule group to which this schedule belongs.")
    private CheckRunRecurringScheduleGroup scheduleGroup;

    @JsonPropertyDescription("Field value for a CRON expression defining the scheduling.")
    private String cronExpression;

    @JsonPropertyDescription("Field value for the time at which the scheduled checks will be executed. Null if disabled.")
    @JsonInclude
    private LocalDateTime timeOfExecution;

    @JsonPropertyDescription("Field value for the time left until the execution of scheduled checks.")
    private Duration timeUntilExecution;

    public static UIEffectiveScheduleModel fromRecurringScheduleSpec(
            RecurringScheduleSpec scheduleSpec,
            CheckRunRecurringScheduleGroup scheduleGroup,
            Function<RecurringScheduleSpec, LocalDateTime> specToLocalDateTimeConverter) {
        UIEffectiveScheduleModel uiEffectiveScheduleModel = new UIEffectiveScheduleModel();
        uiEffectiveScheduleModel.scheduleGroup = scheduleGroup;
        uiEffectiveScheduleModel.cronExpression = scheduleSpec.getCronExpression();

        if (!scheduleSpec.isDisabled()) {
            if (specToLocalDateTimeConverter != null) {
                uiEffectiveScheduleModel.timeOfExecution = specToLocalDateTimeConverter.apply(scheduleSpec);
            }

            if (uiEffectiveScheduleModel.timeOfExecution != null) {
                uiEffectiveScheduleModel.timeUntilExecution =
                        Duration.between(LocalDateTime.now(), uiEffectiveScheduleModel.timeOfExecution)
                                .truncatedTo(ChronoUnit.SECONDS);
            }
        }

        return uiEffectiveScheduleModel;
    }
}
