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
package ai.dqo.rest.controllers;

import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.definitions.sensors.*;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.rest.models.sensors.SensorBasicModel;
import ai.dqo.rest.models.sensors.SensorFolderModel;
import ai.dqo.services.sensor.SensorFolderModelService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * REST api controller to manage the list of sensors.
 */
@RestController
@RequestMapping("/api/sensors")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Sensors", description = "Sensors definition Management")
public class SensorsController {

    private DqoHomeContextFactory dqoHomeContextFactory;
    private UserHomeContextFactory userHomeContextFactory;
    private SensorFolderModelService sensorFolderModelService;


    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param dqoHomeContextFactory      Dqo home context factory.
     * @param userHomeContextFactory     User home context factory.
     * @param sensorFolderModelService     sensor folder model service
     */
    @Autowired
    public SensorsController(DqoHomeContextFactory dqoHomeContextFactory,
                             UserHomeContextFactory userHomeContextFactory,
                             SensorFolderModelService sensorFolderModelService) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
        this.sensorFolderModelService = sensorFolderModelService;
    }

    /**
     Retrieves a folder sensor model for all sensors.
     This method returns a ResponseEntity containing a Mono of a SensorFolderModel. The method retrieves a SensorFolderModel
     from the sensorFolderModelService, representing all the sensors. The ResponseEntity with a status of OK and the list of
     sensors is returned.
     @return ResponseEntity containing a Mono of a SensorFolderModel representing all the sensors.
     @throws Exception if there is an internal server error while retrieving the list of sensors.
     */
    @GetMapping
    @ApiOperation(value = "getAllSensor", notes = "Returns a sensors file model", response = SensorFolderModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorFolderModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorFolderModel>> getAllSensors() {

        SensorFolderModel sensors = this.sensorFolderModelService.getDqoSensors();

        return new ResponseEntity<>(Mono.just(sensors), HttpStatus.OK);
    }

    /**
     Retrieves a folder sensor model for the specified folders.
     This method returns a ResponseEntity containing a Mono of a Map of ProviderType and SensorBasicModel. The input is a
     path variable representing the folders in which the sensors are located. The method retrieves a SensorFolderModel from
     the sensorFileModelService, and then calls its getSensorBasicModel method to get the list of sensors for the specified
     folders. If the list is empty, a ResponseEntity with a status of NOT_FOUND is returned.
     @param folders The path variable representing the folders in which the sensors are located.
     @return ResponseEntity containing a Mono of a Map of ProviderType and SensorBasicModel representing the list of sensors.
     @throws Exception if there is an internal server error while retrieving the list of sensors.
     */
    @GetMapping("/{folders}")
    @ApiOperation(value = "getSensor", notes = "Returns a file sensor model", response = SensorFolderModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorFolderModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<Map<ProviderType, SensorBasicModel>>> getSensor(
            @PathVariable String[] folders) {

        SensorFolderModel sensors = this.sensorFolderModelService.getDqoSensors();
        Map<ProviderType, SensorBasicModel> sensorBasicModelMap = sensors.getSensorBasicModel(folders);

        if (sensorBasicModelMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Mono.just(sensorBasicModelMap), HttpStatus.OK);
    }

    @PostMapping("/{folders}")
    @ApiOperation(value = "createSensor", notes = "Creates (adds) a new sensor given sensor information.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New sensor successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Sensor with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createSensor(
            @ApiParam("Dictionary of sensor definitions") @RequestBody Map<ProviderType, SensorBasicModel> sensorModelMap,
            @PathVariable String[] folders) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorFolderModel sensors = this.sensorFolderModelService.getDqoSensors();
        Map<ProviderType, SensorBasicModel> sensorBasicModelMap = sensors.getSensorBasicModel(folders);

        StringBuilder sensorName = new StringBuilder();
        for (int i = 0; i < folders.length; i++) {
            sensorName.append(folders[i]);
            if (i < folders.length - 1) {
                sensorName.append("/");
            }
        }

        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName.toString(), true);

        if(sensorDefinitionWrapper == null){
            sensorDefinitionWrapper = sensorDefinitionList.createAndAddNew(sensorName.toString());
        }
        ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();

        for (Map.Entry<ProviderType, SensorBasicModel> sensorsMap : sensorModelMap.entrySet()) {
            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
            providerSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
            providerSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
            userHomeContext.flush();
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     Updates an existing sensor.
     @param sensorModelMap A dictionary of sensor definitions.
     @param folders An array of folder names representing the sensor path.
     @return A response entity indicating the status of the update request.
     */
    @PutMapping("/{folders}")
    @ApiOperation(value = "updateSensor", notes = "Updates an existing sensor folder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sensor folder model successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Folder not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateSensor(
            @ApiParam("Dictionary of sensor definitions") @RequestBody Map<ProviderType, SensorBasicModel> sensorModelMap,
            @PathVariable String[] folders) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorFolderModel sensors = this.sensorFolderModelService.getDqoSensors();
        Map<ProviderType, SensorBasicModel> sensorBasicModelMap = sensors.getSensorBasicModel(folders);

        if (sensorBasicModelMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        StringBuilder sensorName = new StringBuilder();
        for (int i = 0; i < folders.length; i++) {
            sensorName.append(folders[i]);
            if (i < folders.length - 1) {
                sensorName.append("/");
            }
        }

        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName.toString(), true);

        if (sensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();

        for (Map.Entry<ProviderType, SensorBasicModel> sensorsMap : sensorModelMap.entrySet()) {
            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
            providerSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
            providerSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
            userHomeContext.flush();
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     DELETE endpoint for deleting a sensor.
     @param folders The path to the sensor to be deleted.
     @return A ResponseEntity with a Mono that emits no elements and only completes when the sensor is deleted successfully.
     */
    @DeleteMapping("/{folders}")
    @ApiOperation(value = "deleteSensor", notes = "Deletes a sensor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sensor successfully deleted"),
            @ApiResponse(code = 404, message = "Sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteSensor(
            @PathVariable String[] folders) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorFolderModel sensors = this.sensorFolderModelService.getDqoSensors();
        Map<ProviderType, SensorBasicModel> sensorBasicModelMap = sensors.getSensorBasicModel(folders);

        if (sensorBasicModelMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        StringBuilder sensorName = new StringBuilder();
        for (int i = 0; i < folders.length; i++) {
            sensorName.append(folders[i]);
            if (i < folders.length - 1) {
                sensorName.append("/");
            }
        }

        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName.toString(), true);

        if (sensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();

        for (Map.Entry<ProviderType, SensorBasicModel> sensorsMap : sensorBasicModelMap.entrySet()) {
            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
            providerSensorDefinitionWrapper.markForDeletion();
            userHomeContext.flush();
        }

        sensorDefinitionWrapper.markForDeletion();
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }
}