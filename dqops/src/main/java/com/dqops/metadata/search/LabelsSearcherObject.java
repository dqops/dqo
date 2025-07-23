/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
