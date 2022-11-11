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
package ai.dqo.metadata.search;

import ai.dqo.metadata.groupings.DataStreamMappingSpec;

import java.util.Arrays;

/**
 * Dimension search matcher for CLI commands.
 */
public class DimensionSearchMatcher {
	/**
	 * Returns a boolean value if filters fit spec.
	 * @param dimension Dimension.
	 * @param dimensions DimensionsConfigurationSpec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchDimension(String dimension, DataStreamMappingSpec dimensions) {
		if (dimension == null || dimensions == null) {
			return true;
		}
		if(dimensions.getLevel1() == null || dimensions.getLevel2() == null || dimensions.getLevel3() == null ||
				dimensions.getLevel4() == null || dimensions.getLevel5() == null || dimensions.getLevel6() == null ||
				dimensions.getLevel7() == null || dimensions.getLevel8() == null || dimensions.getLevel9() == null) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel1().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel1().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel2().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel2().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel3().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel3().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel4().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel4().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel5().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel5().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel6().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel6().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel7().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel7().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel8().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel8().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getLevel9().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getLevel9().getStaticValue(), dimension)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param connectionSearchFilters Connection search filters.
	 * @param dimensionSpec Dimensions configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllConnectionDimensions(ConnectionSearchFilters connectionSearchFilters, DataStreamMappingSpec dimensionSpec) {
		String[] dimensions = connectionSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDimension(dimension, dimensionSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param tableSearchFilters Table search filters.
	 * @param dimensionSpec Dimensions configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllTableDimensions(TableSearchFilters tableSearchFilters, DataStreamMappingSpec dimensionSpec) {
		String[] dimensions = tableSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDimension(dimension, dimensionSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param columnSearchFilters Column search filters.
	 * @param dimensionSpec Dimensions configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllColumnDimensions(ColumnSearchFilters columnSearchFilters, DataStreamMappingSpec dimensionSpec) {
		String[] dimensions = columnSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDimension(dimension, dimensionSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param checkSearchFilters Check search filters.
	 * @param dimensionSpec Dimensions configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllCheckDimensions(CheckSearchFilters checkSearchFilters, DataStreamMappingSpec dimensionSpec) {
		String[] dimensions = checkSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDimension(dimension, dimensionSpec));
	}
}
