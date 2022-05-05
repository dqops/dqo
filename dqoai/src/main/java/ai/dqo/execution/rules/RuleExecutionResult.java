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
package ai.dqo.execution.rules;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Result object returned from a rule that evaluated a single alert.
 * Python rule functions will return instances of this object.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RuleExecutionResult {
    private boolean passed;
    private Double expectedValue;
    private Double lowerBound;
    private Double upperBound;

    /**
     * Returns the status if the rule has passed.
     * @return Rule has passed and the sensor reading is valid according to the rule.
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Sets the rule pass status.
     * @param passed True when the rule was passed (reading is correct) or false when the value was rejected and an alert will be raised.
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    /**
     * Returns the expected value that the rule considered as a valid value.
     * @return Expected value.
     */
    public Double getExpectedValue() {
        return expectedValue;
    }

    /**
     * Sets the expected value.
     * @param expectedValue Expected value.
     */
    public void setExpectedValue(Double expectedValue) {
        this.expectedValue = expectedValue;
    }

    /**
     * Returns the lowest accepted value (optional).
     * @return Lowest accepted value, used as a lower band.
     */
    public Double getLowerBound() {
        return lowerBound;
    }

    /**
     * Sets the lowest accepted value (optional).
     * @param lowerBound Lowest accepted value.
     */
    public void setLowerBound(Double lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * Sets the highest accepted value (upper bound), it is an optional value that may be returned by a rule.
     * @return Upper bound.
     */
    public Double getUpperBound() {
        return upperBound;
    }

    /**
     * Sets the upper bound value (maximum accepted value).
     * @param upperBound Upper bound value.
     */
    public void setUpperBound(Double upperBound) {
        this.upperBound = upperBound;
    }
}
