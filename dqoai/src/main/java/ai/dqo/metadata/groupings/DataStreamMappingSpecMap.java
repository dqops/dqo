package ai.dqo.metadata.groupings;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;

import java.util.Map;

/**
 * Dictionary of named data stream mappings defined on a table level.
 */
public class DataStreamMappingSpecMap extends AbstractDirtyTrackingSpecMap<DataStreamMappingSpec> {
    public static final String DEFAULT_MAPPING_NAME = "default";

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DataStreamMappingSpecMap clone() {
        DataStreamMappingSpecMap cloned = new DataStreamMappingSpecMap();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(cloned.getHierarchyId().clone());
        }

        for (Map.Entry<String, DataStreamMappingSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), keyPair.getValue().clone());
        }

        if (!this.isDirty()) {
            cloned.clearDirty(false);
        }

        return cloned;
    }

    /**
     * Creates a copy of the data stream mappings map. Additionally, parameters are expanded.
     * @return Deep cloned map with parameters expanded inside all data stream levels.
     */
    public DataStreamMappingSpecMap expandAndTrim(SecretValueProvider secretValueProvider) {
        DataStreamMappingSpecMap trimmed = new DataStreamMappingSpecMap();
        for(Map.Entry<String, DataStreamMappingSpec> keyValuePair : this.entrySet()) {
            trimmed.put(keyValuePair.getKey(), keyValuePair.getValue().expandAndTrim(secretValueProvider));
        }
        return trimmed;
    }
}
