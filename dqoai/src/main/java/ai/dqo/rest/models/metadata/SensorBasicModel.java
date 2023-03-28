package ai.dqo.rest.models.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Sensor basic model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ProviderSensorBasicModel", description = "Provider sensor basic model")
public class SensorBasicModel {

    @JsonPropertyDescription("A map of folder-level children sensors.")
    private Map<String, SensorBasicModel> folders;

    @JsonPropertyDescription("Whether the sensor is a User Home sensor.")
    private Boolean custom = null;

    public SensorBasicModel() {}

    public SensorBasicModel(Map<String, SensorBasicModel> folders) {
        this.folders = folders;
    }

    /**
     * Adds a child sensor to the folder-level map.
     * @param path     The path of the child sensor.
     * @param custom Whether the child sensor is User Home or not.
     */
    public void addChild(String path, Boolean custom) {
        if (this.folders == null) {
            this.folders = new HashMap<>();
        }
        String[] parts = path.split("/", 2);
        String name = parts[0];
        String childFolder = parts.length > 1 ? parts[1] : null;
        SensorBasicModel child = this.folders.get(name);
        if (child == null) {
            child = new SensorBasicModel();
            this.folders.put(name, child);
        }
        if (childFolder != null) {
            child.addChild(childFolder, custom);
        }
        if (child.getFolders() == null && childFolder == null) {
            child.setIsCustom(custom);
        }
    }

    public Map<String, SensorBasicModel> getFolders() {
        return this.folders;
    }

    public Boolean isCustom() {
        return this.custom;
    }

    public void setIsCustom(Boolean custom) {
        this.custom = custom;
    }
}