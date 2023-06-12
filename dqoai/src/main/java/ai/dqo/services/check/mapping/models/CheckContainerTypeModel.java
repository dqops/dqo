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

import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
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
