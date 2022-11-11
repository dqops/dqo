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

public class DataStreamSearcherObject {
	private DataStreamMappingSpec connectionDataStreams;
	private DataStreamMappingSpec tableDataStreams;
	private DataStreamMappingSpec columnDataStreams;

	public DataStreamMappingSpec getConnectionDataStreams() {
		return this.connectionDataStreams;
	}

	public void setConnectionDataStreams(DataStreamMappingSpec connectionDataStreams) {
		this.connectionDataStreams = connectionDataStreams;
	}

	public DataStreamMappingSpec getTableDataStreams() {
		return this.tableDataStreams;
	}

	public void setTableDataStreams(DataStreamMappingSpec tableDataStreams) {
		this.tableDataStreams = tableDataStreams;
	}

	public DataStreamMappingSpec getColumnDataStreams() {
		return this.columnDataStreams;
	}

	public void setColumnDataStreams(DataStreamMappingSpec columnDataStreams) {
		this.columnDataStreams = columnDataStreams;
	}
}
