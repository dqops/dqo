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

import com.dqops.metadata.id.HierarchyNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Search parameter object passed to the search visitors. Collects found nodes.
 */
public class SearchParameterObject {
	private List<HierarchyNode> nodes;
	private DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject;
	private LabelsSearcherObject labelsSearcherObject;

	/**
	 * Creates a new instance of search parameter object.
	 * @param nodes Target collection of nodes that will receive values.
	 * @param dataGroupingConfigurationSearcherObject Data stream search helper for searching for data streams at multiple levels.
	 * @param labelsSearcherObject Labels search helper for searching for labels at multiple levels.
	 */
	public SearchParameterObject(List<HierarchyNode> nodes, DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject, LabelsSearcherObject labelsSearcherObject) {
		this.nodes = nodes;
		this.dataGroupingConfigurationSearcherObject = dataGroupingConfigurationSearcherObject;
		this.labelsSearcherObject = labelsSearcherObject;
	}

	/**
	 * Creates a new parameter object.
	 * @param nodes Target list that will receive found nodes.
	 */
	public SearchParameterObject(List<HierarchyNode> nodes) {
		this.nodes = nodes;
		this.dataGroupingConfigurationSearcherObject = new DataGroupingConfigurationSearcherObject();
		this.labelsSearcherObject = new LabelsSearcherObject();
	}

	/**
	 * Creates a default search parameter.
	 */
	public SearchParameterObject() {
		this(new ArrayList<>());
	}

	/**
	 * Returns a list of found nodes.
	 * @return Found nodes.
	 */
	public List<HierarchyNode> getNodes() {
		return nodes;
	}

	/**
	 * Returns a data stream searcher that can search for data streams configured with static values.
	 * @return Data stream searcher.
	 */
	public DataGroupingConfigurationSearcherObject getDataStreamSearcherObject() {
		return dataGroupingConfigurationSearcherObject;
	}

	/**
	 * Returns a labels searcher that identify nodes that must match a label filter.
	 * @return Label search helper.
	 */
	public LabelsSearcherObject getLabelsSearcherObject() {
		return labelsSearcherObject;
	}
}
