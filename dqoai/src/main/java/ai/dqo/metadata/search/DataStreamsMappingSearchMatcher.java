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
 * Data stream search matcher for CLI commands.
 */
public class DataStreamsMappingSearchMatcher {
	/**
	 * Returns a boolean value if filters fit spec.
	 * @param level Data stream level value.
	 * @param dataStreams DimensionsConfigurationSpec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchDataStreamsMapping(String level, DataStreamMappingSpec dataStreams) {
		if (level == null || dataStreams == null) {
			return true;
		}
		if(dataStreams.getLevel1() == null || dataStreams.getLevel2() == null || dataStreams.getLevel3() == null ||
				dataStreams.getLevel4() == null || dataStreams.getLevel5() == null || dataStreams.getLevel6() == null ||
				dataStreams.getLevel7() == null || dataStreams.getLevel8() == null || dataStreams.getLevel9() == null) {
			return true;
		}
		if (level.equals(dataStreams.getLevel1().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel1().getTag(), level)) {
			return true;
		}
		if (level.equals(dataStreams.getLevel2().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel2().getTag(), level)) {
			return true;
		}
		if (level.equals(dataStreams.getLevel3().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel3().getTag(), level)) {
			return true;
		}
		if (level.equals(dataStreams.getLevel4().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel4().getTag(), level)) {
			return true;
		}
		if (level.equals(dataStreams.getLevel5().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel5().getTag(), level)) {
			return true;
		}
		if (level.equals(dataStreams.getLevel6().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel6().getTag(), level)) {
			return true;
		}
		if (level.equals(dataStreams.getLevel7().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel7().getTag(), level)) {
			return true;
		}
		if (level.equals(dataStreams.getLevel8().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel8().getTag(), level)) {
			return true;
		}
		if (level.equals(dataStreams.getLevel9().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataStreams.getLevel9().getTag(), level)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param connectionSearchFilters Connection search filters.
	 * @param dataStreamMappingSpec Data streams mapping spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllConnectionDataStreamsMapping(ConnectionSearchFilters connectionSearchFilters, DataStreamMappingSpec dataStreamMappingSpec) {
		String[] dimensions = connectionSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDataStreamsMapping(dimension, dataStreamMappingSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param tableSearchFilters Table search filters.
	 * @param dataStreamsMappingSpec Data streams configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllTableDataStreams(TableSearchFilters tableSearchFilters, DataStreamMappingSpec dataStreamsMappingSpec) {
		String[] dimensions = tableSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDataStreamsMapping(dimension, dataStreamsMappingSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param columnSearchFilters Column search filters.
	 * @param dataStreamsMappingSpec Data streams mapping spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllColumnDataStreams(ColumnSearchFilters columnSearchFilters, DataStreamMappingSpec dataStreamsMappingSpec) {
		String[] dimensions = columnSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDataStreamsMapping(dimension, dataStreamsMappingSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param checkSearchFilters Check search filters.
	 * @param dataStreamMappingSpec Data streams mapping spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllCheckDataStreamsMapping(CheckSearchFilters checkSearchFilters, DataStreamMappingSpec dataStreamMappingSpec) {
		String[] dimensions = checkSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDataStreamsMapping(dimension, dataStreamMappingSpec));
	}
}
