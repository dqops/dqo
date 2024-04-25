/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.metadata.search;

import com.dqops.metadata.labels.LabelSetSpec;

public class LabelsSearcherObject {
	private LabelSetSpec connectionLabels;
	private LabelSetSpec tableLabels;
	private LabelSetSpec columnLabels;

	public LabelSetSpec getConnectionLabels() {
		return this.connectionLabels;
	}

	public void setConnectionLabels(LabelSetSpec connectionLabels) {
		this.connectionLabels = connectionLabels;
	}

	public LabelSetSpec getTableLabels() {
		return this.tableLabels;
	}

	public void setTableLabels(LabelSetSpec tableLabels) {
		this.tableLabels = tableLabels;
	}

	public LabelSetSpec getColumnLabels() {
		return this.columnLabels;
	}

	public void setColumnLabels(LabelSetSpec columnLabels) {
		this.columnLabels = columnLabels;
	}
}
