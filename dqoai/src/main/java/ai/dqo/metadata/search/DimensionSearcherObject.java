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

import ai.dqo.metadata.groupings.DimensionsConfigurationSpec;

public class DimensionSearcherObject {
	private DimensionsConfigurationSpec connectionDimension;
	private DimensionsConfigurationSpec tableDimension;
	private DimensionsConfigurationSpec columnDimension;

	public DimensionsConfigurationSpec getConnectionDimension() {
		return this.connectionDimension;
	}

	public void setConnectionDimension(DimensionsConfigurationSpec connectionDimension) {
		this.connectionDimension = connectionDimension;
	}

	public DimensionsConfigurationSpec getTableDimension() {
		return this.tableDimension;
	}

	public void setTableDimension(DimensionsConfigurationSpec tableDimension) {
		this.tableDimension = tableDimension;
	}

	public DimensionsConfigurationSpec getColumnDimension() {
		return this.columnDimension;
	}

	public void setColumnDimension(DimensionsConfigurationSpec columnDimension) {
		this.columnDimension = columnDimension;
	}
}
