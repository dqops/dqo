/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Result object returned from a rule that evaluated a single alert.
 * Python rule functions will return instances of this object.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RuleExecutionResult {
    private Boolean passed;
    private Double newActualValue;
    private Double expectedValue;
    private Double lowerBound;
    private Double upperBound;
    private boolean modelIsOutdated;

    /**
     * Returns the status if the rule has passed.
     * @return Rule has passed and the sensor readout is valid according to the rule.
     */
    public Boolean getPassed() {
        return passed;
    }

    /**
     * Sets the rule pass status.
     * @param passed True when the rule was passed (readout is correct) or false when the value was rejected and an alert will be raised.
     */
    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    /**
     * Returns a new (updated) actual value that was recalculated be the rule and should be stored in the check result instead of the value returned from the sensor.
     * @return New updated actual value to store.
     */
    public Double getNewActualValue() {
        return newActualValue;
    }

    /**
     * Stores a new updated actual value that should be stored in the check result.
     * @param newActualValue New updated check result.
     */
    public void setNewActualValue(Double newActualValue) {
        this.newActualValue = newActualValue;
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

    /**
     * Returns a boolean flag which says that the ML model that the rule uses is outdated and should be refreshed.
     * @return True when the model is outdated.
     */
    public boolean isModelIsOutdated() {
        return modelIsOutdated;
    }

    /**
     * Sets a flag which informs the DQOps engine that the ML model should be refreshed.
     * @param modelIsOutdated The model is outdated and should be refreshed.
     */
    public void setModelIsOutdated(boolean modelIsOutdated) {
        this.modelIsOutdated = modelIsOutdated;
    }
}
