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

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Json serializer object mother.
 */
public final class JsonSerializerObjectMother {
    /**
     * Creates a new json serializer.
     * @return Json serializer.
     */
    public static JsonSerializer createNew() {
        return new JsonSerializerImpl();
    }

    /**
     * Returns the default (singleton) json serializer.
     * @return Json serializer.
     */
    public static JsonSerializer getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(JsonSerializer.class);
    }
}
