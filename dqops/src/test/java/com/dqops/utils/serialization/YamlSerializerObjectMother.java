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

import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoLoggingUserErrorsConfigurationProperties;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.logging.UserErrorLoggerImpl;
import org.springframework.beans.factory.BeanFactory;

/**
 * Yaml serializer object mother.
 */
public final class YamlSerializerObjectMother {
    /**
     * Creates a new yaml serializer.
     * @return Yaml serializer.
     */
    public static YamlSerializer createNew() {
        return new YamlSerializerImpl(DqoConfigurationPropertiesObjectMother.getDefaultCloned(), new UserErrorLoggerImpl(new DqoLoggingUserErrorsConfigurationProperties()));
    }

    /**
     * Returns the default (singleton) yaml serializer.
     * @return Yaml serializer.
     */
    public static YamlSerializer getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(YamlSerializer.class);
    }
}
