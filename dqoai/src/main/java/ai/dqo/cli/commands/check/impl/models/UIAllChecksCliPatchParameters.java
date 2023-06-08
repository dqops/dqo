/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.cli.commands.check.impl.models;

import ai.dqo.metadata.search.CheckSearchFilters;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * Parameter object for creating pruned patch trees of all checks that fit the filters.
 */
@Data
public class UIAllChecksCliPatchParameters {
    @NotNull
    CheckSearchFilters checkSearchFilters;

    boolean overrideConflicts;

    boolean disableWarningLevel;
    boolean disableErrorLevel;
    boolean disableFatalLevel;

    Map<String, String> sensorOptions;

    Map<String, String> warningLevelOptions;
    Map<String, String> errorLevelOptions;
    Map<String, String> fatalLevelOptions;
}
