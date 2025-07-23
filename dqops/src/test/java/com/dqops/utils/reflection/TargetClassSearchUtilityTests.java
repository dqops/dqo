/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.reflection;

import com.dqops.BaseTest;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.sensors.column.bool.ColumnBoolFalsePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.List;

@SpringBootTest
public class TargetClassSearchUtilityTests extends BaseTest {
    @Test
    void findClasses_whenSearchingForSensorParameterClasses_thenFindsClasses() {
        List<? extends Class<? extends AbstractSensorParametersSpec>> classes =
                TargetClassSearchUtility.findClasses("com.dqops.sensors", Path.of("."), AbstractSensorParametersSpec.class);

        Assertions.assertNotNull(classes);
        Assertions.assertTrue(classes.size() > 30);
        Assertions.assertTrue(classes.contains(ColumnBoolFalsePercentSensorParametersSpec.class));
        Assertions.assertFalse(classes.contains(AbstractSensorParametersSpec.class));
    }
}
