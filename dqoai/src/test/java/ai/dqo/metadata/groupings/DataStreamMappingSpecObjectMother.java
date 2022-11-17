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
package ai.dqo.metadata.groupings;

/**
 * Object mother for {@link DataStreamMappingSpec}.
 */
public class DataStreamMappingSpecObjectMother {
    /**
     * Creates a data stream mapping specification with one level.
     * @param level1 Data stream level 1.
     * @return Data stream mapping configuration.
     */
    public static DataStreamMappingSpec create(DataStreamLevelSpec level1) {
        DataStreamMappingSpec dataStreamMappingSpec = new DataStreamMappingSpec() {{
			setLevel1(level1);
        }};
        return dataStreamMappingSpec;
    }

    /**
     * Creates a data stream mapping specification with two levels.
     * @param level1 Level 1.
     * @param level2 Level 2.
     * @return Data stream mapping.
     */
    public static DataStreamMappingSpec create(DataStreamLevelSpec level1, DataStreamLevelSpec level2) {
        DataStreamMappingSpec dataStreamMappingSpec = new DataStreamMappingSpec() {{
			setLevel1(level1);
			setLevel2(level2);
        }};
        return dataStreamMappingSpec;
    }

    /**
     * Creates a data stream mapping with three levels.
     * @param level1 Level 1.
     * @param level2 Level 2.
     * @param level3 Level 3.
     * @return Data stream mapping configuration.
     */
    public static DataStreamMappingSpec create(DataStreamLevelSpec level1, DataStreamLevelSpec level2, DataStreamLevelSpec level3) {
        DataStreamMappingSpec dataStreamMappingSpec = new DataStreamMappingSpec() {{
			setLevel1(level1);
			setLevel2(level2);
			setLevel3(level3);
        }};
        return dataStreamMappingSpec;
    }

    /**
     * Creates a data stream mapping specification with four levels.
     * @param level1 Level 1.
     * @param level2 Level 2.
     * @param level3 Level 3.
     * @param level4 Level 4.
     * @return Data stream mapping configuration.
     */
    public static DataStreamMappingSpec create(DataStreamLevelSpec level1, DataStreamLevelSpec level2, DataStreamLevelSpec level3, DataStreamLevelSpec level4) {
        DataStreamMappingSpec dataStreamMappingSpec = new DataStreamMappingSpec() {{
			setLevel1(level1);
			setLevel2(level2);
			setLevel3(level3);
			setLevel4(level4);
        }};
        return dataStreamMappingSpec;
    }
}
