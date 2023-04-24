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
package ai.dqo.utils.docs.sensors;

import lombok.Data;

import java.util.List;

/**
 * Sensor groups model. Contains the sensor target ('table' or 'column'), sensor category and list contain
 * all information about the grouped sensors.
 */
@Data
public class SensorGroupedDocumentationModel {
    /**
     * Sensor target ('table' or 'column')
     */
    private String target;

    /**
     * Sensor category.
     */
    private String category;

    /**
     * List contain all information about the grouped sensors.
     */
    List<SensorDocumentationModel> collectedSensors;
}
