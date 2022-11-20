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
package ai.dqo.data.ruleresults.snapshot;

import ai.dqo.data.ruleresults.factory.RuleResultsTableFactoryImpl;
import ai.dqo.data.ruleresults.filestorage.DummyRuleResultsFileStorageService;
import ai.dqo.data.readouts.factory.SensorReadoutsTableFactoryImpl;

/**
 * Object mother for {@link RuleResultsSnapshotFactory}
 */
public class RuleResultsSnapshotFactoryObjectMother {
    /**
     * Creates a rule result storage service that returns a dummy storage service.
     * It will behave like there are no historic rule results that are saved will be discarded.
     * @return Rule result storage service.
     */
    public static RuleResultsSnapshotFactory createDummyRuleResultsStorageService() {
        return new RuleResultsSnapshotFactoryImpl(new DummyRuleResultsFileStorageService(), new RuleResultsTableFactoryImpl(new SensorReadoutsTableFactoryImpl()));
    }
}
