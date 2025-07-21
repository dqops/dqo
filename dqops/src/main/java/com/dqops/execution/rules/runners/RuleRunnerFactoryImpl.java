/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules.runners;

import com.dqops.execution.rules.runners.python.PythonRuleRunner;
import com.dqops.metadata.definitions.rules.RuleRunnerType;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Rule runner factory.
 */
@Component
public class RuleRunnerFactoryImpl implements RuleRunnerFactory {
    private final BeanFactory beanFactory;
    private final PythonRuleRunner pythonRuleRunner;

    /**
     * Creates a sensor runner factory.
     * @param beanFactory Bean factory.
     */
    @Autowired
    public RuleRunnerFactoryImpl(BeanFactory beanFactory, PythonRuleRunner pythonRuleRunner) {
        this.beanFactory = beanFactory;
        this.pythonRuleRunner = pythonRuleRunner;
    }

    /**
     * Gets an instance of a rule runner given the class name.
     *
     * @param ruleRunnerType            Rule runner type.
     * @param ruleRunnerClassName Rule runner class name (optional, only for a custom java class).
     * @return Rule runner class name.
     */
    public AbstractRuleRunner getRuleRunner(RuleRunnerType ruleRunnerType, String ruleRunnerClassName) {
        switch (ruleRunnerType) {
            case python:
                return this.pythonRuleRunner;
            case custom_class:
                return createCustomRuleRunnerByJavaClass(ruleRunnerClassName);
            default:
                throw new IllegalArgumentException("Unsupported rule runner type: " + ruleRunnerType);
        }
    }

    /**
     * Gets an instance of a rule runner given the class name.
     * @param ruleRunnerClassName Rule runner class name.
     * @return Rule runner class name.
     */
    public AbstractRuleRunner createCustomRuleRunnerByJavaClass(String ruleRunnerClassName) {
        try {
            Class<?> runnerClassType = Class.forName(ruleRunnerClassName);
            AbstractRuleRunner abstractRuleRunner = (AbstractRuleRunner) beanFactory.getBean(runnerClassType);
            return abstractRuleRunner;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Runner " + ruleRunnerClassName + " not found");
        }
    }
}
