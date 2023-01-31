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
package ai.dqo.metadata.basespecs;

import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.id.*;
import ai.dqo.utils.reflection.ClassInfo;
import ai.dqo.utils.reflection.FieldInfo;
import ai.dqo.utils.reflection.ReflectionService;
import ai.dqo.utils.reflection.ReflectionServiceSingleton;
import ai.dqo.utils.serialization.DeserializationAware;
import ai.dqo.utils.serialization.YamlNotRenderWhenDefault;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Base class for all spec classes in the tree. Provides basic dirty checking.
 */
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSpec extends BaseDirtyTrackingSpec implements HierarchyNode, YamlNotRenderWhenDefault, DeserializationAware {
    /**
     * Default empty field map.
     */
    public static final ChildHierarchyNodeFieldMapImpl<AbstractSpec> FIELDS = (ChildHierarchyNodeFieldMapImpl<AbstractSpec>) ChildHierarchyNodeFieldMap.empty();

    /**
     * Node hierarchy id that identifies the node within the node tree.
     */
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HierarchyId hierarchyId;

    /**
     * Set to true when the object was created as a result of deserialization from YAML.
     * We can detect that the object was not simply created by a constructor, but was created by jackson.
     */
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private boolean wasDeserialized;

    /**
     * Collection of ignored properties that were present in the YAML specification file, but were not present on the node.
     * The user has added invalid properties. We only want to know the names of these properties for validation purposes.
     */
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Map<String, Object> ignoredProperties;

    /**
     * Returns the hierarchy ID of this node.
     *
     * @return Hierarchy ID of this node.
     */
    @Override
    public HierarchyId getHierarchyId() {
        return hierarchyId;
    }

    /**
     * Replaces the hierarchy ID. A new hierarchy ID is also propagated to all child nodes.
     *
     * @param hierarchyId New hierarchy ID.
     */
    @Override
    public void setHierarchyId(HierarchyId hierarchyId) {
        this.hierarchyId = hierarchyId;
		propagateHierarchyIdToFields(hierarchyId);
    }

    /**
     * Returns true if the object instance was created as a result of deserialization from YAML.
     * @return True when this object instance was created by the jackson deserializer. False when it was created using the constructor.
     */
    public boolean isWasDeserialized() {
        return wasDeserialized;
    }

    /**
     * Returns a dictionary of invalid properties that were present in the YAML specification file, but were not declared in the class.
     * Returns null when all properties were valid.
     * @return True when undefined properties were present in the YAML file that failed the deserialization. Null when all properties were valid (declared).
     */
    public Map<String, Object> getIgnoredProperties() {
        return ignoredProperties;
    }

    /**
     * Assigns the new hierarchy ID on child nodes.
     * @param hierarchyId New hierarchy id of the current node that should be propagated to the field getter map.
     */
    protected void propagateHierarchyIdToFields(HierarchyId hierarchyId) {
        ChildHierarchyNodeFieldMap childFieldMap = this.getChildMap();
        childFieldMap.propagateHierarchyIdToChildren(this, hierarchyId);
    }

    /**
     * Returns the child map on the spec class with all fields.
     * @return Return the field map.
     */
    protected abstract ChildHierarchyNodeFieldMap getChildMap();

    /**
     * Returns a named child. It is a named object in an object map (column map, test map) or a field name.
     *
     * @param childName Child name.
     * @return Child node.
     */
    @Override
    public HierarchyNode getChild(Object childName) {
        ChildHierarchyNodeFieldMap childFieldMap = this.getChildMap();
        assert (childName.toString() != null) : "child name is null";
        assert (childFieldMap.getFieldGetter(childName.toString()) != null) : "child name " + childName + " missing on class " + this.getClass().getCanonicalName() + ", verify that the field name in the field name is correct";
        return childFieldMap.getFieldGetter(childName.toString()).apply(this);
    }

    /**
     * Propagates a hierarchy ID to a child node, creating a child hierarchy ID that is the hierarchy ID of this node with an extra element, the field name.
     * @param childNode Child node.
     * @param fieldName Field name.
     */
    protected void propagateHierarchyIdToField(HierarchyNode childNode, String fieldName) {
        if (childNode == null || this.hierarchyId == null) {
            return;
        }

        HierarchyId childHierarchyId = new HierarchyId(this.getHierarchyId(), fieldName);
        childNode.setHierarchyId(childHierarchyId);

        assert getChild(fieldName) != null && getChild(fieldName).getHierarchyId().equals(childHierarchyId);
    }

    /**
     * Returns an iterable that iterates over child nodes.
     *
     * @return Iterable to iterate over child nodes.
     */
    @Override
    public Iterable<HierarchyNode> children() {
        return new FieldIterable(this, getChildMap());
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    @JsonIgnore
    public boolean isDirty() {
        if (super.isDirty()) {
            return true;
        }

        for(HierarchyNode child : this.children()) {
            if (child.isDirty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        super.clearDirty(propagateToChildren);

        if (propagateToChildren) {
            for (HierarchyNode child : this.children()) {
                child.clearDirty(true);
            }
        }
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    @JsonIgnore
    public boolean isDefault() {
        for (HierarchyNode child : this.children()) {
            if (child == null) {
                continue;
            }
            if (child instanceof YamlNotRenderWhenDefault) {
                YamlNotRenderWhenDefault notRenderWhenDefaultChild = (YamlNotRenderWhenDefault) child;
				boolean childIsDefault = notRenderWhenDefaultChild.isDefault();
                if (!childIsDefault) {
                    return false;
                }
            }
            else {
                return false; // non default child found
            }
        }

        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();
        ClassInfo myClassInfo = reflectionService.getClassInfoForClass(this.getClass());

        List<FieldInfo> fields = myClassInfo.getFields();
        for (FieldInfo fieldInfo : fields) {
            ParameterDataType dataType = fieldInfo.getDataType();
            if (dataType == ParameterDataType.object_type) {
                continue;
            }

            Object fieldValue = fieldInfo.getRawFieldValue(this);
            Object defaultValue = fieldInfo.getDefaultValue();

            if (!Objects.equals(fieldValue, defaultValue)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Copies non-null properties from <code>sourceObject</code> to the current instance.
     * @param sourceObject Source object.
     */
    public void copyNotNullPropertiesFrom(AbstractSpec sourceObject) {
        if (sourceObject == null) {
            return;
        }

        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();
        ClassInfo myClassInfo = reflectionService.getClassInfoForClass(this.getClass());

        List<FieldInfo> fields = myClassInfo.getFields();
        for (FieldInfo fieldInfo : fields) {
            ParameterDataType dataType = fieldInfo.getDataType();
            Object newValue = fieldInfo.getRawFieldValue(sourceObject);
            Object currentValue = fieldInfo.getRawFieldValue(this);

            if (newValue == null) {
                continue;
            }

            if (dataType == ParameterDataType.object_type) {
                if (AbstractSpec.class.isAssignableFrom(fieldInfo.getClazz())) {
                    if (currentValue == null) {
                        fieldInfo.setRawFieldValue(newValue, this);
                    }
                    else {
                        AbstractSpec currentObject = (AbstractSpec) currentValue;
                        currentObject.copyNotNullPropertiesFrom((AbstractSpec) newValue);
                    }
                } else if (Map.class.isAssignableFrom(fieldInfo.getClazz())) {
                    if (currentValue == null) {
                        fieldInfo.setRawFieldValue(newValue, this);
                    } else {
                        @SuppressWarnings("rawtypes") Map currentObjectMap = (Map) currentValue;
                        //noinspection rawtypes
                        currentObjectMap.putAll((Map)newValue);
                    }
                }
            } else {
                fieldInfo.setRawFieldValue(newValue, this);
            }
        }
    }

    /**
     * Called after the object was deserialized from JSON or YAML.
     */
    @Override
    public void onDeserialized() {
        this.wasDeserialized = true;
    }

    /**
     * Called by Jackson property when an undeclared property was present in the deserialized YAML or JSON text.
     * @param name Undeclared (and ignored) property name.
     * @param value Property value.
     */
    @JsonAnySetter
    public void handleUndeclaredProperty(String name, Object value) {
        if (this.ignoredProperties == null) {
            this.ignoredProperties = new LinkedHashMap<>();
        }
        this.ignoredProperties.put(name, value);
    }
}
