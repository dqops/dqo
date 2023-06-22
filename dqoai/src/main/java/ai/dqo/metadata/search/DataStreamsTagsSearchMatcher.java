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

import ai.dqo.metadata.groupings.DataGroupingConfigurationSpec;

import java.util.Arrays;

/**
 * Data grouping tags search matcher for CLI commands.
 */
public class DataStreamsTagsSearchMatcher {
	/**
	 * Returns a boolean value if filters fit spec.
	 * @param level Data stream level value.
	 * @param dataGroupingConfiguration DimensionsConfigurationSpec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchDataGroupingConfigurationTag(String level, DataGroupingConfigurationSpec dataGroupingConfiguration) {
		if (level == null || dataGroupingConfiguration == null) {
			return true;
		}
		if(dataGroupingConfiguration.getLevel1() == null || dataGroupingConfiguration.getLevel2() == null || dataGroupingConfiguration.getLevel3() == null ||
				dataGroupingConfiguration.getLevel4() == null || dataGroupingConfiguration.getLevel5() == null || dataGroupingConfiguration.getLevel6() == null ||
				dataGroupingConfiguration.getLevel7() == null || dataGroupingConfiguration.getLevel8() == null || dataGroupingConfiguration.getLevel9() == null) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel1().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel1().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel2().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel2().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel3().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel3().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel4().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel4().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel5().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel5().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel6().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel6().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel7().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel7().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel8().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel8().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel9().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel9().getTag(), level)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param columnSearchFilters Column search filters.
	 * @param dataGroupingConfigurationSpec Data grouping configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllColumnDataGroupingTags(ColumnSearchFilters columnSearchFilters, DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
		String[] tags = columnSearchFilters.getTags();
		if (tags == null) {
			return true;
		}
		return Arrays.stream(tags).allMatch(tag -> matchDataGroupingConfigurationTag(tag, dataGroupingConfigurationSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param checkSearchFilters Check search filters.
	 * @param dataGroupingConfigurationSpec Data streams mapping spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllCheckDataStreamsMapping(CheckSearchFilters checkSearchFilters, DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
		String[] tags = checkSearchFilters.getTags();
		if (tags == null) {
			return true;
		}
		return Arrays.stream(tags).allMatch(tag -> matchDataGroupingConfigurationTag(tag, dataGroupingConfigurationSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param tags Array of required tags.
	 * @param dataGroupingConfigurationSpec Data grouping configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllRequiredTags(String[] tags, DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
		if (tags == null) {
			return true;
		}
		return Arrays.stream(tags).allMatch(dimension -> matchDataGroupingConfigurationTag(dimension, dataGroupingConfigurationSpec));
	}
}
