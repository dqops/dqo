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
package com.dqops.rules;

import com.dqops.BaseTest;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AbstractRuleParametersSpecTests extends BaseTest {
    private AbstractRuleParametersSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new TestableAbstractRuleParametersSpec();
    }

    public class TestableAbstractRuleParametersSpec extends AbstractRuleParametersSpec {

        /**
         * Returns the child map on the spec class with all fields.
         *
         * @return Return the field map.
         */
        @Override
        protected ChildHierarchyNodeFieldMap getChildMap() {
            return AbstractRuleParametersSpec.FIELDS;
        }

        /**
         * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
         *
         * @return Rule definition name (python module name without .py extension).
         */
        @Override
        public String getRuleDefinitionName() {
            return null;
        }

        /**
         * Decreases the rule severity by changing the parameters.
         * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
         *
         * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
         */
        @Override
        public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {

        }
    }
}
