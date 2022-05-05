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
 * Object mother for DimensionsConfigurationSpec.
 */
public class DimensionsConfigurationSpecObjectMother {
    /**
     * Creates a dimension specification with one dimension.
     * @param dimension1 Dimension 1.
     * @return Dimension configuration.
     */
    public static DimensionsConfigurationSpec create(DimensionMappingSpec dimension1) {
        DimensionsConfigurationSpec dimensionsConfigurationSpec = new DimensionsConfigurationSpec() {{
			setDimension1(dimension1);
        }};
        return dimensionsConfigurationSpec;
    }

    /**
     * Creates a dimension specification with two dimensions.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @return Dimension configuration.
     */
    public static DimensionsConfigurationSpec create(DimensionMappingSpec dimension1, DimensionMappingSpec dimension2) {
        DimensionsConfigurationSpec dimensionsConfigurationSpec = new DimensionsConfigurationSpec() {{
			setDimension1(dimension1);
			setDimension2(dimension2);
        }};
        return dimensionsConfigurationSpec;
    }

    /**
     * Creates a dimension specification with three dimensions.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @return Dimension configuration.
     */
    public static DimensionsConfigurationSpec create(DimensionMappingSpec dimension1, DimensionMappingSpec dimension2, DimensionMappingSpec dimension3) {
        DimensionsConfigurationSpec dimensionsConfigurationSpec = new DimensionsConfigurationSpec() {{
			setDimension1(dimension1);
			setDimension2(dimension2);
			setDimension3(dimension3);
        }};
        return dimensionsConfigurationSpec;
    }

    /**
     * Creates a dimension specification with four dimensions.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param dimension4 Dimension 4.
     * @return Dimension configuration.
     */
    public static DimensionsConfigurationSpec create(DimensionMappingSpec dimension1, DimensionMappingSpec dimension2, DimensionMappingSpec dimension3, DimensionMappingSpec dimension4) {
        DimensionsConfigurationSpec dimensionsConfigurationSpec = new DimensionsConfigurationSpec() {{
			setDimension1(dimension1);
			setDimension2(dimension2);
			setDimension3(dimension3);
			setDimension4(dimension4);
        }};
        return dimensionsConfigurationSpec;
    }
}
