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
