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

package com.dqops.metadata.labels.labelcontainers;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Object that counts recurrences of a label.
 */
public final class LabelCounter {
    private String label;
    private TreeMap<String, LabelCounter> nestedLabels;
    private final AtomicInteger labelsCount = new AtomicInteger();
    private final AtomicInteger nestedLabelsCount = new AtomicInteger();

    public LabelCounter() {
    }

    public LabelCounter(String label) {
        this.label = label;
    }

    public LabelCounter(String label, int labelsCount) {
        this.label = label;
        this.labelsCount.addAndGet(labelsCount);
    }

    public LabelCounter(String label, int labelsCount, int nestedLabelsCount) {
        this.label = label;
        this.labelsCount.addAndGet(labelsCount);
        if (nestedLabelsCount > 0) {
            this.nestedLabelsCount.addAndGet(nestedLabelsCount);
        }
    }

    /**
     * Returns the label.
     * @return Label, including the path from the parent labels (separated by '/').
     */
    public String getLabel() {
        return label;
    }

    /**
     * An optional dictionary of nested labels, if this label is just a prefix.
     */
    public synchronized List<LabelCounter> getNestedLabels() {
        if (this.nestedLabels == null) {
            return null;
        }

        List<LabelCounter> allNestedLabels = this.nestedLabels.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
        return allNestedLabels;
    }

    /**
     * Retrieves a child label by name.
     * @param childLabel Child label (text) to find.
     * @return Label counter of the child label or null, when the child label was not found.
     */
    public synchronized LabelCounter getChildLabel(String childLabel) {
        if (this.nestedLabels == null) {
            return null;
        }

        return this.nestedLabels.get(childLabel);
    }

    /**
     * Returns the number of occurrences of this label.
     * @return Number of occurrences.
     */
    public int getLabelsCount() {
        return this.labelsCount.get();
    }

    /**
     * Returns the number of occurrences of child labels, excluding a bare label.
     * @return The number of nested labels.
     */
    public int getNestedLabelsCount() {
        return this.nestedLabelsCount.get();
    }

    /**
     * Adds a direct child label or returns a child label it is already present in the dictionary of child labels. Does not increase any counters.
     * @param childLabel The text of the child label, limited to the child name (without the path separated by '/' characters).
     * @return Label counter for the child label.
     */
    public LabelCounter addNestedLabel(String childLabel) {
        String labelWithPrefix = this.label + "/" + childLabel;
        if (this.nestedLabels == null) {
            this.nestedLabels = new TreeMap<>();
            LabelCounter childLabelCounter = new LabelCounter(labelWithPrefix);
            this.nestedLabels.put(childLabel, childLabelCounter);
            return childLabelCounter;
        }

        LabelCounter existingLabelCounter = this.nestedLabels.get(childLabel);
        if (existingLabelCounter != null) {
            return existingLabelCounter;
        }

        LabelCounter newLabelCounter = new LabelCounter(labelWithPrefix);
        this.nestedLabels.put(childLabel, newLabelCounter);
        return newLabelCounter;
    }

    /**
     * Creates a new instance of a label. A label that contains multiple elements separated by '/' characters is split into elements and a tree is created.
     * @param label Label to create.
     * @return Label counter for the label or it's tree.
     */
    public static LabelCounter createLabel(String label) {
        int indexOfFirstSegment = label.indexOf('/');
        if (indexOfFirstSegment < 0) {
            return new LabelCounter(label, 1);
        }

        String rootLabelName = label.substring(0, indexOfFirstSegment);
        LabelCounter rootLabel = new LabelCounter(rootLabelName, 0);
        rootLabel.incrementDeepChildLabel(label);

        return rootLabel;
    }

    /**
     * Adds this label to the tree, incrementing the nested child count for all labels on the path.
     * For the deepest label, instead of incrementing nested labels count, increments the label count because that is the terminal label.
     * NOTE: This operation should be called only on the root label.
     * @param fullLabel Full label.
     */
    public void incrementDeepChildLabel(String fullLabel) {
        if (label.indexOf('/') < 0) {
            this.labelsCount.incrementAndGet();
            return;
        }

        String[] labelElements = StringUtils.split(fullLabel, '/');
        LabelCounter currentLabelLevel = this;
        for (int i = 1; i < labelElements.length; i++) {
            currentLabelLevel.nestedLabelsCount.incrementAndGet();
            currentLabelLevel = currentLabelLevel.addNestedLabel(labelElements[i]);
        }
        currentLabelLevel.labelsCount.incrementAndGet();
    }

    /**
     * Increments the counts in the current label by the counts in the other label (that should be the same).
     * Also creates or increments counts for all nested labels.
     * @param otherLabelCounter The other label to import.
     */
    public void addCounts(LabelCounter otherLabelCounter) {
        assert Objects.equals(this.label, otherLabelCounter.label);

        this.labelsCount.addAndGet(otherLabelCounter.labelsCount.get());
        this.nestedLabelsCount.addAndGet(otherLabelCounter.nestedLabelsCount.get());

        synchronized (this) {
            if (this.nestedLabels == null) {
                if (otherLabelCounter.nestedLabels == null) {
                    return;
                }

                TreeMap<String, LabelCounter> newNestedLabels = new TreeMap<>();
                this.nestedLabels = newNestedLabels;
                for (Map.Entry<String, LabelCounter> otherRootLabelKeyValue : otherLabelCounter.nestedLabels.entrySet()) {
                    newNestedLabels.put(otherRootLabelKeyValue.getKey(), otherRootLabelKeyValue.getValue().clone());
                }
            } else {
                if (otherLabelCounter.nestedLabels == null) {
                    return;
                }

                for (Map.Entry<String, LabelCounter> otherRootLabelKeyValue : otherLabelCounter.nestedLabels.entrySet()) {
                    LabelCounter existingRootLabel = this.nestedLabels.get(otherRootLabelKeyValue.getKey());
                    if (existingRootLabel != null) {
                        existingRootLabel.addCounts(otherRootLabelKeyValue.getValue());
                    } else {
                        this.nestedLabels.put(otherRootLabelKeyValue.getKey(), otherRootLabelKeyValue.getValue().clone());
                    }
                }
            }
        }
    }

    /**
     * Decrements the counts in the current label by the counts in the other label (that should be the same).
     * Also removes labels with no counts or decrements counts for all nested labels.
     * @param otherLabelCounter The other label to import in a reverse mode.
     */
    public void subtractCounts(LabelCounter otherLabelCounter) {
        assert Objects.equals(this.label, otherLabelCounter.label);

        this.labelsCount.addAndGet(-otherLabelCounter.labelsCount.get());
        this.nestedLabelsCount.addAndGet(-otherLabelCounter.nestedLabelsCount.get());

        synchronized (this) {
            if (this.nestedLabels == null) {
                assert otherLabelCounter.nestedLabels == null;
            } else {
                if (otherLabelCounter.nestedLabels == null) {
                    return;
                }

                List<String> emptyLabelsToRemove = null;
                for (Map.Entry<String, LabelCounter> otherRootLabelKeyValue : otherLabelCounter.nestedLabels.entrySet()) {
                    LabelCounter existingRootLabel = this.nestedLabels.get(otherRootLabelKeyValue.getKey());
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
                        this.nestedLabels.remove(keyToRemove);
                    }
                }
            }
        }
    }

    /**
     * Checks if the label has any occurrences. Returns true if there are any labels in use, or false when the label is no longer referenced and can be removed.
     * @return True - the label is in use, false - no more usages.
     */
    public boolean isEmpty() {
        return this.labelsCount.get() == 0 && this.nestedLabelsCount.get() == 0; // if nestedLabelsCount is 0, the list of child labels should be empty
    }

    /**
     * Creates and returns a deep copy of this object.
     */
    public LabelCounter clone() {
        LabelCounter cloned = new LabelCounter(this.label, this.labelsCount.get(), this.nestedLabelsCount.get());
        if (this.nestedLabels != null) {
            TreeMap<String, LabelCounter> clonedNestedLabels = new TreeMap<>();
            cloned.nestedLabels = clonedNestedLabels;
            for (Map.Entry<String, LabelCounter> nestedLabelKeyValue : this.nestedLabels.entrySet()) {
                clonedNestedLabels.put(nestedLabelKeyValue.getKey(), nestedLabelKeyValue.getValue().clone());
            }
        }
        
        return cloned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabelCounter that = (LabelCounter) o;

        if (labelsCount.get() != that.labelsCount.get()) return false;
        if (nestedLabelsCount.get() != that.nestedLabelsCount.get()) return false;
        if (!Objects.equals(label, that.label)) return false;
        return Objects.equals(nestedLabels, that.nestedLabels);
    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (nestedLabels != null ? nestedLabels.hashCode() : 0);
        result = 31 * result + labelsCount.get();
        result = 31 * result + nestedLabelsCount.get();
        return result;
    }

    @Override
    public String toString() {
        return "LabelCounter{" +
                "label='" + label + '\'' +
                ", labelCount=" + labelsCount.get() +
                ", nestedLabelsCount=" + nestedLabelsCount.get() +
                '}';
    }
}
