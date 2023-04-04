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

package ai.dqo.services.check.mapping.utils;

import ai.dqo.services.check.mapping.basicmodels.UICheckBasicModel;
import ai.dqo.services.check.mapping.basicmodels.UICheckContainerBasicModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for operations related to {@link UICheckContainerBasicModel}.
 */
public class UICheckContainerBasicModelUtility {
    /**
     * Get all categories to which the checks from the model belong.
     * @param model {@link UICheckContainerBasicModel} from which to extract.
     * @return Distinct list of category names in the checks of the model, sorted ascending.
     */
    public static List<String> getCheckCategoryNames(UICheckContainerBasicModel model) {
        return model.getChecks().stream()
                .map(UICheckBasicModel::getCheckCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
