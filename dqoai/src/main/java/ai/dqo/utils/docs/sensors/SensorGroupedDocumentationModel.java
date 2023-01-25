package ai.dqo.utils.docs.sensors;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Sensor groups model. Contains the sensor target ('table' or 'column') and map contain all information
 * about the sensor grouped by sensor category.
 */
@Data
public class SensorGroupedDocumentationModel {
    /**
     * Sensor target ('table' or 'column')
     */
    private String target;

    /**
     * Map contain all information about the sensors grouped by sensor category (eg. bool, completeness etc.).
     */
    private Map<String, List<SensorDocumentationModel>> groupedSensors;
}
