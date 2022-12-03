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

import ai.dqo.metadata.groupings.DataStreamMappingSpecMap;

/**
 * Helper object used when a search visitor is traversing nodes to find target checks to execute, given the data stream hierarchy tags.
 */
public class DataStreamSearcherObject {
	private DataStreamMappingSpecMap tableDataStreams;

	/**
	 * Returns a collection (map) of named data streams defined at a table level.
	 * @return Data streams map.
	 */
	public DataStreamMappingSpecMap getTableDataStreams() {
		return this.tableDataStreams;
	}

	/**
	 * Stores (remembers) the data stream mappings collection from a table level.
	 * @param tableDataStreams Data streams map from the table level.
	 */
	public void setTableDataStreams(DataStreamMappingSpecMap tableDataStreams) {
		this.tableDataStreams = tableDataStreams;
	}
}
