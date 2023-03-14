package ai.dqo.utils.docs.sensors;

import lombok.Data;

import java.util.List;

/**
 * Sensor groups model. Contains the sensor target ('table' or 'column'), sensor category and list contain
 * all information about the grouped sensors.
 */
@Data
public class SensorGroupedDocumentationModel {
    /**
     * Sensor target ('table' or 'column')
     */
    private String target;

    /**
     * Sensor category.
     */
    private String category;

    /**
     * List contain all information about the grouped sensors.
     */
    List<SensorDocumentationModel> collectedSensors;
}
