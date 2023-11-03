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
package com.dqops.metadata.basespecs;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.metadata.id.*;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.serialization.DeserializationAware;
import com.dqops.utils.serialization.YamlNotRenderWhenDefault;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rits.cloning.Cloner;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

/**
 * Base class for all spec classes in the tree. Provides basic dirty checking.
 */
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSpec extends BaseDirtyTrackingSpec
        implements HierarchyNode, YamlNotRenderWhenDefault, DeserializationAware, Cloneable {
    public static boolean VALIDATE_FIELD_MAP_ON_WRITE = false;

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
    private Map<String, Object> additionalProperties;

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
        if (!Objects.equals(this.hierarchyId, hierarchyId)) {
            this.hierarchyId = hierarchyId;
            propagateHierarchyIdToFields(hierarchyId);
        }
    }

    /**
     * Returns true if the object instance was created as a result of deserialization from YAML.
     * @return True when this object instance was created by the jackson deserializer. False when it was created using the constructor.
     */
    public boolean isWasDeserialized() {
        return wasDeserialized;
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
     * Assigns the new hierarchy ID on child nodes, ignoring one child node.
     * @param hierarchyId New hierarchy id of the current node that should be propagated to the field getter map.
     * @param ignoredNode Child node name to ignore.
     */
    protected void propagateHierarchyIdToFieldsExcept(HierarchyId hierarchyId, String ignoredNode) {
        ChildHierarchyNodeFieldMap childFieldMap = this.getChildMap();
        childFieldMap.propagateHierarchyIdToChildrenExcept(this, hierarchyId, ignoredNode);
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
        GetHierarchyChildNodeFunc<HierarchyNode> child = childFieldMap.getFieldGetter(childName.toString());
        if (child != null) {
            return child.apply(this);
        }
        return null;
    }

    /**
     * Propagates a hierarchy ID to a child node, creating a child hierarchy ID that is the hierarchy ID of this node with an extra element, the field name.
     * @param childNode Child node.
     * @param fieldName Field name.
     */
    protected void propagateHierarchyIdToField(HierarchyNode childNode, Object fieldName) {
        if (VALIDATE_FIELD_MAP_ON_WRITE) {
            if (childNode != getChild(fieldName)) {
                throw new DqoRuntimeException("Child node " + fieldName + " on the class " + this.getClass().getSimpleName() +
                        " uses a wrong name when accessing the field map, update the names of fields.");
            }
        }

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
     * Check if the object is dirty (has changes), but the <code>checkAlsoChildren</code> parameter wil decide if we want to iterate also over child items (which could trigger loading them).
     *
     * @return True when the object is dirty and has modifications.
     */
    @JsonIgnore
    public boolean isDirty(boolean checkAlsoChildren) {
        if (super.isDirty()) {
            return true;
        }

        if (checkAlsoChildren) {
            for(HierarchyNode child : this.children()) {
                if (child.isDirty()) {
                    return true;
                }
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

        ClassInfo myClassInfo = this.getChildMap().getReflectionClassInfo();

        List<FieldInfo> fields = myClassInfo.getFields();
        for (FieldInfo fieldInfo : fields) {
            ParameterDataType dataType = fieldInfo.getDataType();
            if (dataType == ParameterDataType.object_type) {
                continue;
            }

            Object fieldValue = fieldInfo.getRawFieldValue(this);
            Object defaultValue = fieldInfo.getDefaultValue();

            if (fieldValue instanceof String && ((String)fieldValue).length() == 0) {
                continue; // this is the default value, we are not storing empty strings in Yaml
            }

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

        ClassInfo myClassInfo = this.getChildMap().getReflectionClassInfo();

        List<FieldInfo> fields = myClassInfo.getFields();
        for (FieldInfo fieldInfo : fields) {
            ParameterDataType dataType = fieldInfo.getDataType();
            Object newValue = fieldInfo.getRawFieldValue(sourceObject);
            Object currentValue = fieldInfo.getRawFieldValue(this);

            if (newValue == null) {
                continue;
            }

            if (dataType == ParameterDataType.object_type) {
                if (newValue instanceof AbstractSpec) {
                    if (currentValue == null) {
                        fieldInfo.setRawFieldValue(newValue, this);
                    }
                    else {
                        AbstractSpec currentObject = (AbstractSpec) currentValue;
                        currentObject.copyNotNullPropertiesFrom((AbstractSpec) newValue);
                    }
                } else if (newValue instanceof Map) {
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
     * Returns a dictionary of invalid properties that were present in the YAML specification file, but were not declared in the class.
     * Returns null when all properties were valid.
     * @return True when undefined properties were present in the YAML file that failed the deserialization. Null when all properties were valid (declared).
     */
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    /**
     * Sets a new dictionary of additional properties. It is used to store custom sensor and custom rule parameters.
     * @param additionalProperties Dictionary of additional properties (fields that were not mapped to JSON).
     */
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.setDirtyIf(!Objects.equals(this.additionalProperties, additionalProperties));
        this.additionalProperties = additionalProperties;
    }

    /**
     * Called by Jackson property when an undeclared property was present in the deserialized YAML or JSON text.
     * @param name Undeclared (and ignored) property name.
     * @param value Property value.
     */
    @JsonAnySetter
    public void handleUndeclaredProperty(String name, Object value) {
        if (this.additionalProperties == null) {
            this.additionalProperties = new LinkedHashMap<>();
        }
        this.additionalProperties.put(name, value);
    }

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public AbstractSpec deepClone() {
        try {
            AbstractSpec cloned = (AbstractSpec) super.clone();
            if (this.additionalProperties != null) {
                Cloner cloner = new Cloner();
                cloned.additionalProperties = cloner.deepClone(this.additionalProperties);
            }

            ClassInfo myClassInfo = this.getChildMap().getReflectionClassInfo();

            List<FieldInfo> fields = myClassInfo.getFields();
            for (FieldInfo fieldInfo : fields) {
                ParameterDataType dataType = fieldInfo.getDataType();
                if (dataType != ParameterDataType.object_type) {
                    continue; // we are not cloning basic types
                }

                Object currentValue = fieldInfo.getRawFieldValue(this);

                if (currentValue == null) {
                    continue;
                }

                if (currentValue instanceof HierarchyNode) {
                    HierarchyNode hierarchyNode = (HierarchyNode)currentValue;
                    HierarchyNode clonedChild = hierarchyNode.deepClone();
                    fieldInfo.setRawFieldValue(clonedChild, cloned);
                }
                else if (currentValue instanceof Map){
                    Map<?,?> clonedChild = cloneMap((Map<?,?>) currentValue);
                    fieldInfo.setRawFieldValue(clonedChild, cloned);
                }
                else {
                    throw new UnsupportedOperationException("Cannot clone object of type " + currentValue.getClass().getCanonicalName() +
                            " on field: " + fieldInfo.getClassFieldName() + ", class: " + this.getClass().getCanonicalName());
                }
            }

            cloned.clearDirty(false);
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new UnsupportedOperationException("Cannot clone the object ", ex);
        }
    }

    /**
     * Creates and returns a clone (copy) of Map object.
     */
    public Map<?,?> cloneMap(Map<?,?> originalMap) {

        if (originalMap instanceof HashMap) {
            HashMap<?, ?> sourceHashMap = (HashMap<?, ?>) originalMap;
            return (HashMap<?, ?>) sourceHashMap.clone();
        }
        HashMap<?,?> sourceMap = new LinkedHashMap<>();

        for (Map.Entry<?,?> keyValuePair : originalMap.entrySet()) {
            Object key = keyValuePair.getKey();
            Object value = keyValuePair.getValue();
            Map<Object, Object> objectMap = (Map<Object, Object>) sourceMap;
            objectMap.put(key, value);
        }
        return sourceMap;
    }
}
