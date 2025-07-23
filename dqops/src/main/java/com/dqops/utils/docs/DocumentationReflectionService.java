/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs;

import com.dqops.utils.docs.generators.TypeModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.ReflectionService;

import java.lang.reflect.Type;
import java.util.function.Function;

public interface DocumentationReflectionService {
    ClassInfo getClassInfoForClass(Class<?> targetClass);

    TypeModel getObjectsTypeModel(Type type, Function<Class<?>, String> objectLinkAccessor);

    ReflectionService getReflectionService();
}
