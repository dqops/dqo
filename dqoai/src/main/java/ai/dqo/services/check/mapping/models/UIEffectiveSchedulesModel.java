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
import ai.dqo.metadata.scheduling.RecurringSchedulesSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * Model of configured schedule groups (on connection or table).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIEffectiveSchedulesModel", description = "Model of configured schedule groups (on connection or table).")
public class UIEffectiveSchedulesModel {
    @JsonPropertyDescription("Field value for profiling schedule group.")
    private UIEffectiveScheduleModel profilingSchedule;
    @JsonPropertyDescription("Field value for daily schedule group.")
    private UIEffectiveScheduleModel dailySchedule;
    @JsonPropertyDescription("Field value for monthly schedule group.")
    private UIEffectiveScheduleModel monthlySchedule;

    public static UIEffectiveSchedulesModel fromRecurringSchedulesSpec(
            RecurringSchedulesSpec schedulesSpec,
            Function<RecurringScheduleSpec, LocalDateTime> specToLocalDateTimeConverter) {
        UIEffectiveSchedulesModel uiEffectiveSchedulesModel = new UIEffectiveSchedulesModel();

        uiEffectiveSchedulesModel.profilingSchedule = getEffectiveScheduleModel(
                schedulesSpec.getProfiling(), CheckRunRecurringScheduleGroup.profiling, specToLocalDateTimeConverter);
        uiEffectiveSchedulesModel.dailySchedule = getEffectiveScheduleModel(
                schedulesSpec.getDaily(), CheckRunRecurringScheduleGroup.daily, specToLocalDateTimeConverter);
        uiEffectiveSchedulesModel.monthlySchedule = getEffectiveScheduleModel(
                schedulesSpec.getMonthly(), CheckRunRecurringScheduleGroup.monthly, specToLocalDateTimeConverter);

        if (uiEffectiveSchedulesModel.profilingSchedule == null &&
                uiEffectiveSchedulesModel.dailySchedule == null &&
                uiEffectiveSchedulesModel.monthlySchedule == null) {
            return null;
        }
        return uiEffectiveSchedulesModel;
    }

    protected static UIEffectiveScheduleModel getEffectiveScheduleModel(
            RecurringScheduleSpec scheduleSpec,
            CheckRunRecurringScheduleGroup scheduleGroup,
            Function<RecurringScheduleSpec, LocalDateTime> specToLocalDateTimeConverter) {
        if (scheduleSpec.isDisabled()) {
            return null;
        } else {
            return UIEffectiveScheduleModel.fromRecurringScheduleSpec(
                    scheduleSpec,
                    scheduleGroup,
                    specToLocalDateTimeConverter);
        }
    }
}
