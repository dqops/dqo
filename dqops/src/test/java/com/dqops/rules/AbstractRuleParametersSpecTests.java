/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
