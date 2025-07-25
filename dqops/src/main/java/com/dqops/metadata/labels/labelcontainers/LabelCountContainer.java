/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.labels.labelcontainers;

import com.dqops.metadata.labels.LabelSetSpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.parquet.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An object that stores all found labels and counts their occurrences.
 */
public class LabelCountContainer {
    private final TreeMap<String, LabelCounter> labels = new TreeMap<>();
    private final Object lock = new Object();

    /**
     * Returns a map of all root level labels in this counter.
     * @return Map of all root level labels.
     */
    public List<LabelCounter> getLabels() {
        synchronized (this.lock) {
            return this.labels.values().stream().collect(Collectors.toUnmodifiableList());
        }
    }

    /**
     * Retrieves a label. Supports multi-level labels separated by /.
     * @param label Label to find.
     * @return Label counter or null, when the label was not found.
     */
    public LabelCounter getLabelCounter(String label) {
        if (Strings.isNullOrEmpty(label)) {
            return null;
        }

        String[] labelElements = StringUtils.split(label, '/');

        LabelCounter rootLabel;
        synchronized (this.lock) {
            rootLabel = this.labels.get(labelElements[0]);
        }

        if (rootLabel == null) {
            return null;
        }

        if (labelElements.length == 1) {
            return rootLabel;
        }

        LabelCounter currentCounter = rootLabel;
        for (int i = 1; i < labelElements.length; i++) {
            String childLabel = labelElements[i];
            currentCounter = currentCounter.getChildLabel(childLabel);
            if (currentCounter == null) {
                return null;
            }
        }

        return currentCounter;
    }

    /**
     * Checks if the container is empty (has no labels).
     * @return Container is empty.
     */
    public boolean isEmpty() {
        synchronized (this.lock) {
            return this.labels.isEmpty();
        }
    }

    /**
     * Increments the count of labels for a hierarchical label. If the label is in the form grandparent/parent/child, then increments
     * the nested labels counts for the grandparent and parent, and the label count for the child.
     * For simple labels, just increments the label count.
     * NOTE: This method is not synchronized and is not thread-safe. It should be used only to generate the original object, not the shared object.
     * @param label Label to add to the counter.
     */
    public void incrementLabelCount(String label) {
        if (label == null || label.isEmpty()) {
            return;
        }

        String rootLabelKey = label;
        int indexOfSplit = label.indexOf('/');
        if (indexOfSplit >= 0) {
            // multi-level label
            rootLabelKey = label.substring(0, indexOfSplit);
        }

        LabelCounter rootLabel = this.labels.get(rootLabelKey);
        if (rootLabel != null) {
            rootLabel.incrementDeepChildLabel(label);
            return;
        }

        LabelCounter newLabelCounter = LabelCounter.createLabel(label);
        this.labels.put(rootLabelKey, newLabelCounter);
    }

    /**
     * Imports all labels from a labels set. A labels set is a metadata object used in the DQOps spec objects.
     * @param labels Source labels.
     */
    public void importLabelSet(LabelSetSpec labels) {
        if (labels == null || labels.isEmpty()) {
            return;
        }

        for (String label : labels) {
            incrementLabelCount(label);
        }
    }

    /**
     * Adds counts and registers new labels from a different count container.
     * @param otherContainer Other count container.
     */
    public void addCountsFromContainer(LabelCountContainer otherContainer) {
        if (otherContainer == null || otherContainer.isEmpty()) {
            return;
        }

        synchronized (this.lock) {
            for (Map.Entry<String, LabelCounter> otherRootLabelKeyValue : otherContainer.labels.entrySet()) {
                LabelCounter existingRootLabel = this.labels.get(otherRootLabelKeyValue.getKey());
                if (existingRootLabel != null) {
                    existingRootLabel.addCounts(otherRootLabelKeyValue.getValue());
                } else {
                    this.labels.put(otherRootLabelKeyValue.getKey(), otherRootLabelKeyValue.getValue().clone());
                }
            }
        }
    }

    /**
     * Subtracts (decrements) counts and unregisters empty labels from a different count container.
     * @param otherContainer Other count container.
     */
    public void subtractCountsFromContainer(LabelCountContainer otherContainer) {
        if (otherContainer == null || otherContainer.isEmpty()) {
            return;
        }

        synchronized (this.lock) {
            List<String> emptyLabelsToRemove = null;
            for (Map.Entry<String, LabelCounter> otherRootLabelKeyValue : otherContainer.labels.entrySet()) {
                LabelCounter existingRootLabel = this.labels.get(otherRootLabelKeyValue.getKey());
                existingRootLabel.subtractCounts(otherRootLabelKeyValue.getValue());
                if (existingRootLabel.isEmpty()) {
                    if (emptyLabelsToRemove == null) {
                        emptyLabelsToRemove = new ArrayList<>();
                    }

                    emptyLabelsToRemove.add(otherRootLabelKeyValue.getKey());
                }
            }

            if (emptyLabelsToRemove != null) {
                for (String keyToRemove : emptyLabelsToRemove) {
                    this.labels.remove(keyToRemove);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabelCountContainer that = (LabelCountContainer) o;

        return labels.equals(that.labels);
    }

    @Override
    public int hashCode() {
        return labels.hashCode();
    }
}
