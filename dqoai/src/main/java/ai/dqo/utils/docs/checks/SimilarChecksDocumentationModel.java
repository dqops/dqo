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
package ai.dqo.utils.docs.checks;

import ai.dqo.utils.docs.rules.RuleDocumentationModel;
import ai.dqo.utils.docs.sensors.SensorDocumentationModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Documentation model that describes multiple similar checks.
 */
@Data
public class SimilarChecksDocumentationModel {
    /**
     * Sensor documentation model.
     */
    private SensorDocumentationModel sensor;

    /**
     * Rule documentation model.
     */
    private RuleDocumentationModel rule;

    /**
     * Check description extracted from the JavaDoc comment for the whole check definition class.
     */
    private String checkSpecClassJavaDoc;

    /**
     * Sensor target ('table' or 'column')
     */
    private String target;

    /**
     * Sensor category.
     */
    private String category;

    /**
     * Check name inside the category. It is usually the name of the experiment check.
     */
    private String primaryCheckName;

    /**
     * List of all similar checks.
     */
    private List<CheckDocumentationModel> allChecks = new ArrayList<>();
}
