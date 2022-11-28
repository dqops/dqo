package ai.dqo.metadata.id;

import ai.dqo.metadata.sources.PhysicalTableName;

import java.util.Map;

/**
 * Serializable model of {@link HierarchyId} that is sent to UI. It works as a serialization/deserialization surrogate for a hierarchy id that may contain objects of various types on the path.
 */
public class HierarchyIdModel {
    private Object[] path;

    /**
     * Default constructor.
     */
    public HierarchyIdModel() {
    }

    /**
     * Creates a hierarchy id model from a path.
     * @param path Path.
     */
    public HierarchyIdModel(Object[] path) {
        this.path = path;
    }

    /**
     * Converts a hierarchy model to a hierarchy id, using correct data types.
     * @return Hierarchy id.
     */
    public HierarchyId toHierarchyId() {
        Object[] fixedPath = new Object[this.path.length];
        for (int i = 0; i < fixedPath.length; i++) {
            Object currentPathElement = this.path[i];
            if (currentPathElement instanceof Long) {
                fixedPath[i] = (int) ((Long)currentPathElement).longValue();
            } else if (currentPathElement instanceof String) {
                fixedPath[i] = currentPathElement;
            }
            else if (currentPathElement instanceof Map) {
                Map map = (Map)currentPathElement;
                if (map.containsKey("schemaName") && map.containsKey("tableName")) {
                    String schemaName = map.get("schemaName").toString();
                    String tableName = map.get("tableName").toString();
                    fixedPath[i] = new PhysicalTableName(schemaName, tableName);
                }
                else {
                    throw new RuntimeException("Unsupported object on the HierarchyNodeModel");
                }
            }
            else {
                throw new RuntimeException("Unsupported object on the HierarchyNodeModel");
            }
        }

        return new HierarchyId(fixedPath);
    }

    /**
     * Returns the hierarchy path.
     * @return Hierarchy path.
     */
    public Object[] getPath() {
        return path;
    }

    /**
     * Sets the hierarchy path.
     * @param path Path with objects.
     */
    public void setPath(Object[] path) {
        this.path = path;
    }
}
