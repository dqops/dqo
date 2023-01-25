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
package ai.dqo.services.check.matching;

import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.services.check.mapping.models.UICheckModel;

/**
 * Describes a single check that is similar to other checks in other check types.
 */
public class SimilarCheckModel {
    private CheckType checkType;
    private CheckTimeScale timeScale;
    private String category;
    private UICheckModel checkModel;

    /**
     * Creates a similar check model.
     * @param checkType Check type.
     * @param timeScale Time scale (optional, null for experiments).
     * @param category Check category name.
     * @param checkModel Check UI model with the check name and additional information about the check.
     */
    public SimilarCheckModel(CheckType checkType, CheckTimeScale timeScale, String category, UICheckModel checkModel) {
        this.checkType = checkType;
        this.timeScale = timeScale;
        this.category = category;
        this.checkModel = checkModel;
    }

    /**
     * Return the check type.
     * @return Check type.
     */
    public CheckType getCheckType() {
        return checkType;
    }

    /**
     * Return the time scale. The time scale is optional and maybe null.
     * @return Time scale or null.
     */
    public CheckTimeScale getTimeScale() {
        return timeScale;
    }

    /**
     * Return the category name that is used in YAML.
     * @return Category name.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Return the check UI model with more information about the check.
     * @return Full check model.
     */
    public UICheckModel getCheckModel() {
        return checkModel;
    }
}
