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
package ai.dqo.services.rule;

import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.rest.models.metadata.RuleBasicModel;

/**
 * Interface for mapping a RuleBasicModel (REST model) object to a RuleDefinitionWrapper object.
 */
public interface RuleMappingService {

    /**
     * Returns the rule definition wrapper object.
     * @param ruleBasicModel Rule basic model.
     * @return rule definition wrapper object.
     */
    RuleDefinitionWrapper toRuleDefinitionWrapper(RuleBasicModel ruleBasicModel);

    /**
     * Returns the rule definition spec object.
     * @param ruleBasicModel Rule basic model.
     * @return the rule definition spec object.
     */
    RuleDefinitionSpec withRuleDefinitionSpec(RuleBasicModel ruleBasicModel);

    /**
     * Returns the file content object with Python module content.
     * @param ruleBasicModel Rule basic model.
     * @return file content object with Python module content.
     */
    FileContent withRuleDefinitionPythonModuleContent(RuleBasicModel ruleBasicModel);

    /**
     * Returns the rule basic model object.
     * @param ruleDefinitionWrapper Rule basic model.
     * @return rule basic model object.
     */
    RuleBasicModel toRuleBasicModel(RuleDefinitionWrapper ruleDefinitionWrapper);
}
