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

package ai.dqo.services.check.mapping.utils;

import ai.dqo.services.check.mapping.basicmodels.CheckBasicModel;
import ai.dqo.services.check.mapping.basicmodels.CheckContainerBasicModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for operations related to {@link CheckContainerBasicModel}.
 */
public class CheckContainerBasicModelUtility {
    /**
     * Get all categories to which the checks from the model belong.
     * @param model {@link CheckContainerBasicModel} from which to extract.
     * @return Distinct list of category names in the checks of the model, sorted ascending.
     */
    public static List<String> getCheckCategoryNames(CheckContainerBasicModel model) {
        return model.getChecks().stream()
                .map(CheckBasicModel::getCheckCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
