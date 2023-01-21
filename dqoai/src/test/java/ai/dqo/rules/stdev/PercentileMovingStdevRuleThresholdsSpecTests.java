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
package ai.dqo.rules.stdev;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PercentileMovingStdevRuleThresholdsSpecTests extends BaseTest {
    private PercentileMovingStdevRuleThresholdsSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new PercentileMovingStdevRuleThresholdsSpec();
    }

    @Test
    void getTimeWindow_whenObjectCreated_thenTimewindowIsInitialized() {
        Assertions.assertNotNull(this.sut.getTimeWindow());
    }
}
