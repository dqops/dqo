/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson module that adds support for a deserialization problem handler to ignore some errors in YAML files.
 */
public class LooseDeserializationModule extends SimpleModule {
    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.addDeserializationProblemHandler(new YamlLooseDeserializationProblemHandler());
    }
}
