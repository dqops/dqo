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
package com.dqops.services.check.matching;

import com.dqops.checks.AbstractCheckSpec;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Key object that stores a sensor name and rule names. It is used to find similar checks in other check types.
 * It supports equals and hashcode.
 */
@Deprecated
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class SimilarCheckSensorRuleKey implements SimilarCheckGroupingKey {
    private String sensorName;
    private Class<?> sensorParametersClass;
    private String warningRuleName;
    private String errorRuleName;
    private String fatalRuleName;

    /**
     * Predicate method to determine whether the check belongs to the similar check group identified by this key.
     * @param checkSpec Check spec to match to the group.
     * @return True if provided check matches the group.
     */
    @Override
    public boolean matches(AbstractCheckSpec<?, ?, ?, ?> checkSpec) {
        return sensorName.equals(checkSpec.getParameters().getSensorDefinitionName())
                && sensorParametersClass.equals(checkSpec.getParameters().getClass())
                && warningRuleName.equals(checkSpec.getRuleDefinitionName())
                && errorRuleName.equals(checkSpec.getRuleDefinitionName())
                && fatalRuleName.equals(checkSpec.getRuleDefinitionName());
    }
}
