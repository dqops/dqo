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
