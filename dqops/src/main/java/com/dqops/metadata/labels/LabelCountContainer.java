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

package com.dqops.metadata.labels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An object that stores all found labels and counts their occurrences.
 */
public class LabelCountContainer {
    private final HashMap<String, LabelCounter> labels = new HashMap<>();
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
     * Adds counts and registers new labels from a different count container.
     * @param otherContainer Other count container.
     */
    public void addCountsFromContainer(LabelCountContainer otherContainer) {
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
