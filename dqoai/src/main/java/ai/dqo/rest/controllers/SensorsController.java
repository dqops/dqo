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
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.rest.models.sensors.ProviderSensorModel;
import ai.dqo.rest.models.sensors.SensorFolderModel;
import ai.dqo.rest.models.sensors.SensorModel;
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
    private final static String SENSORS = "Sensors";


    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param dqoHomeContextFactory      Dqo home context factory.
     * @param userHomeContextFactory     User home context factory.
     */
    @Autowired
    public SensorsController(DqoHomeContextFactory dqoHomeContextFactory,
                             UserHomeContextFactory userHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of all sensors from both Dqo Home and User Home.
     * The method creates a SensorFolderModel object that contains a list of SensorModels.
     * Each SensorModel object represents a single sensor and contains its name, specification, and a map of ProviderSensorModels.
     * ProviderSensorModels contain a SQL template and a ProviderSensorDefinitionSpec object.
     * The method returns a ResponseEntity with the SensorFolderModel object and a HTTP status code.
     * @return ResponseEntity<Mono<SensorFolderModel>> containing a list of all sensors
     */
    @GetMapping
    @ApiOperation(value = "getAllSensor", notes = "Returns a sensors folder model", response = SensorFolderModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorFolderModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorFolderModel>> getAllSensors() {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorFolderModel sensorFolderModel = new SensorFolderModel(SENSORS);

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();

        dqoHome.getSensors().forEach(sensorDefinitionWrapper -> {
            SensorDefinitionWrapper dqoHomeSensorDefinitionWrapper = dqoHomeSensorDefinitionList.getByObjectName(sensorDefinitionWrapper.getName(), true);
            SensorModel dqoHomeSensorModel = new SensorModel(sensorDefinitionWrapper.getName(), sensorDefinitionWrapper.getSpec(), false);
            for (ProviderType providerType : ProviderType.values()) {
                ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = dqoHomeSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
                ProviderSensorModel providerSensorModel = new ProviderSensorModel();
                if (providerSensorDefinitionWrapper != null) {
                    providerSensorModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
                    providerSensorModel.setProviderSensorDefinitionSpec(providerSensorDefinitionWrapper.getSpec());
                }
                dqoHomeSensorModel.getSensors().put(providerType, providerSensorModel);
            }
            sensorFolderModel.addSensor(dqoHomeSensorModel);
        });

        userHome.getSensors().forEach(sensorDefinitionWrapper -> {
            SensorModel userHomeSensorModel = new SensorModel(sensorDefinitionWrapper.getName(), sensorDefinitionWrapper.getSpec(), true);
            sensorDefinitionWrapper.getProviderSensors().forEach(providerSensorDefinitionWrapper -> {
                ProviderSensorModel providerSensorModel = new ProviderSensorModel();
                if (providerSensorDefinitionWrapper != null) {
                    providerSensorModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
                    providerSensorModel.setProviderSensorDefinitionSpec(providerSensorDefinitionWrapper.getSpec());
                    providerSensorModel.setCustom(true);
                }
                userHomeSensorModel.getSensors().put(providerSensorDefinitionWrapper.getProvider(), providerSensorModel);
            });

            sensorFolderModel.addSensor(userHomeSensorModel);
        });

        return new ResponseEntity<>(Mono.just(sensorFolderModel), HttpStatus.OK);
    }

    /**
     * Retrieves a file sensor model.
     * @param folders an array of folder names representing the path to the sensor model
     */
    @GetMapping("/{folders}")
    @ApiOperation(value = "getSensor", notes = "Returns a folder sensor model", response = SensorFolderModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorFolderModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorModel>> getSensor(
            @PathVariable String[] folders) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorFolderModel sensorFolderModel = new SensorFolderModel(SENSORS);

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();

        dqoHome.getSensors().forEach(sensorDefinitionWrapper -> {
            SensorDefinitionWrapper dqoHomeSensorDefinitionWrapper = dqoHomeSensorDefinitionList.getByObjectName(sensorDefinitionWrapper.getName(), true);
            SensorModel dqoHomeSensorModel = new SensorModel(sensorDefinitionWrapper.getName(), sensorDefinitionWrapper.getSpec(), false);
            for (ProviderType providerType : ProviderType.values()) {
                ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = dqoHomeSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
                ProviderSensorModel providerSensorModel = new ProviderSensorModel();
                if (providerSensorDefinitionWrapper != null) {
                    providerSensorModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
                    providerSensorModel.setProviderSensorDefinitionSpec(providerSensorDefinitionWrapper.getSpec());
                }
                dqoHomeSensorModel.getSensors().put(providerType, providerSensorModel);
            }
            sensorFolderModel.addSensor(dqoHomeSensorModel);
        });

        userHome.getSensors().forEach(sensorDefinitionWrapper -> {
            SensorModel userHomeSensorModel = new SensorModel(sensorDefinitionWrapper.getName(), sensorDefinitionWrapper.getSpec(), true);
            sensorDefinitionWrapper.getProviderSensors().forEach(providerSensorDefinitionWrapper -> {
                ProviderSensorModel providerSensorModel = new ProviderSensorModel();
                if (providerSensorDefinitionWrapper != null) {
                    providerSensorModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
                    providerSensorModel.setProviderSensorDefinitionSpec(providerSensorDefinitionWrapper.getSpec());
                    providerSensorModel.setCustom(true);
                }
                userHomeSensorModel.getSensors().put(providerSensorDefinitionWrapper.getProvider(), providerSensorModel);
            });

            sensorFolderModel.addSensor(userHomeSensorModel);
        });

        SensorModel sensorBasicModelMap = sensorFolderModel.getSensorModel(folders);

        if (sensorBasicModelMap == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Mono.just(sensorBasicModelMap), HttpStatus.OK);
    }

    /**
     * Create a new sensor given sensor information.
     * @param sensorModelMap a dictionary of sensor definitions
     */
    @PostMapping
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
            @ApiParam("Dictionary of sensor definitions") @RequestBody SensorModel sensorModelMap) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList userHomeSensorDefinitionList= userHome.getSensors();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();

        SensorDefinitionWrapper dqoHomeSensorDefinitionWrapper = dqoHomeSensorDefinitionList.getByObjectName(sensorModelMap.getSensorName(), true);
        SensorDefinitionWrapper userHomeSensorDefinitionWrapper = userHomeSensorDefinitionList.getByObjectName(sensorModelMap.getSensorName(), true);

        if(userHomeSensorDefinitionWrapper != null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        if(dqoHomeSensorDefinitionWrapper == null){
            SensorDefinitionWrapper newUserHomeSensorDefinitionWrapper = userHomeSensorDefinitionList.createAndAddNew(sensorModelMap.getSensorName());
            newUserHomeSensorDefinitionWrapper.setSpec(sensorModelMap.getSensorDefinitionSpec());
            userHomeContext.flush();

            SensorDefinitionWrapper sensorDefinitionWrapper = userHomeSensorDefinitionList.getByObjectName(sensorModelMap.getSensorName(), true);
            ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();
            for (Map.Entry<ProviderType, ProviderSensorModel> sensorsMap : sensorModelMap.getSensors().entrySet()) {
                if(sensorsMap.getValue().getProviderSensorDefinitionSpec() == null || sensorsMap.getValue().getSqlTemplate() == null){
                    continue;
                }
                ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                providerSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                providerSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());

                userHomeContext.flush();
            }
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
        }

        SensorDefinitionWrapper newUserHomeSensorDefinitionWrapper = userHomeSensorDefinitionList.createAndAddNew(sensorModelMap.getSensorName());
        newUserHomeSensorDefinitionWrapper.setSpec(sensorModelMap.getSensorDefinitionSpec());
        userHomeContext.flush();
        ProviderSensorDefinitionList providerSensorDefinitionList = newUserHomeSensorDefinitionWrapper.getProviderSensors();
        ProviderSensorDefinitionList dqoHomeProviderSensorDefinitionList = dqoHomeSensorDefinitionWrapper.getProviderSensors();

        int countUnique = 0;
        for (Map.Entry<ProviderType, ProviderSensorModel> sensorsMap : sensorModelMap.getSensors().entrySet()) {
            if(sensorsMap.getValue().getProviderSensorDefinitionSpec() == null || sensorsMap.getValue().getSqlTemplate() == null){
                continue;
            }
            ProviderSensorDefinitionWrapper dqoHomeProviderSensor = dqoHomeProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(),true);
            if(dqoHomeProviderSensor.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec()) && dqoHomeProviderSensor.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate())){
                continue;
            }
            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
            providerSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
            providerSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
            userHomeContext.flush();
            countUnique++;
        }
        if(countUnique==0){
            newUserHomeSensorDefinitionWrapper.markForDeletion();
            userHomeContext.flush();
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing sensor folder. If the user edits the definition of an existing sensor in Dqo Home,
     * any custom sensor associated with that definition will be deleted.
     * @param sensorModelMap Dictionary of sensor definitions
     */
    @PutMapping
    @ApiOperation(value = "updateSensor", notes = "Updates an existing sensor folder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sensor folder model successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Folder not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateSensor(
            @ApiParam("Dictionary of sensor definitions") @RequestBody SensorModel sensorModelMap) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList userHomeSensorDefinitionList = userHome.getSensors();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();

        SensorDefinitionWrapper dqoHomeSensorDefinitionWrapper = dqoHomeSensorDefinitionList.getByObjectName(sensorModelMap.getSensorName(), true);
        SensorDefinitionWrapper userHomeSensorDefinitionWrapper = userHomeSensorDefinitionList.getByObjectName(sensorModelMap.getSensorName(), true);
        ProviderSensorDefinitionList userHomeProviderSensorDefinitionList;


        if (dqoHomeSensorDefinitionWrapper == null) {
            userHomeSensorDefinitionWrapper.setSpec(sensorModelMap.getSensorDefinitionSpec());
            userHomeContext.flush();
            ProviderSensorDefinitionList customProviderSensorDefinitionList = userHomeSensorDefinitionWrapper.getProviderSensors();
            for (Map.Entry<ProviderType, ProviderSensorModel> sensorsMap : sensorModelMap.getSensors().entrySet()) {
                ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = customProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                if (providerSensorDefinitionWrapper == null) {
                    ProviderSensorDefinitionWrapper newUserHomeProviderSensorDefinitionWrapper = customProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                    newUserHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                    newUserHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                    userHomeContext.flush();
                } else if ((!providerSensorDefinitionWrapper.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec())
                        || !providerSensorDefinitionWrapper.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate()))) {
                    ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper = customProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                    userHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                    userHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                    userHomeContext.flush();
                }
            }
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
        }

        ProviderSensorDefinitionList dqoHomeProviderSensorDefinitionList = dqoHomeSensorDefinitionWrapper.getProviderSensors();

        if (sensorModelMap.isCustom()) {
            if (!dqoHomeSensorDefinitionWrapper.getSpec().equals(sensorModelMap.getSensorDefinitionSpec())) {
                userHomeSensorDefinitionWrapper.setSpec(sensorModelMap.getSensorDefinitionSpec());
                userHomeContext.flush();
                userHomeProviderSensorDefinitionList = userHomeSensorDefinitionWrapper.getProviderSensors();
                for (Map.Entry<ProviderType, ProviderSensorModel> sensorsMap : sensorModelMap.getSensors().entrySet()) {
                    ProviderSensorDefinitionWrapper dqoHomeProviderSensorDefinitionWrapper = dqoHomeProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                    ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper = userHomeProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                    if(sensorsMap.getValue().isCustom()){
                        if(dqoHomeProviderSensorDefinitionWrapper.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec()) || dqoHomeProviderSensorDefinitionWrapper.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate())){
                            userHomeProviderSensorDefinitionWrapper.markForDeletion();
                            userHomeContext.flush();
                        } else {
                            userHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            userHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            userHomeContext.flush();
                        }
                    } else {
                        if (dqoHomeProviderSensorDefinitionWrapper == null && userHomeProviderSensorDefinitionWrapper == null) {
                            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = userHomeProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                            providerSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            providerSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            userHomeContext.flush();
                            continue;
                        }
                        if (dqoHomeProviderSensorDefinitionWrapper == null) {
                            userHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            userHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            userHomeContext.flush();
                            continue;
                        }
                        if (!dqoHomeProviderSensorDefinitionWrapper.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec()) || !dqoHomeProviderSensorDefinitionWrapper.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate())) {
                            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = userHomeProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                            providerSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            providerSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            userHomeContext.flush();
                        }
                    }
                }
            } else {
                userHomeSensorDefinitionWrapper.setSpec(sensorModelMap.getSensorDefinitionSpec());
                userHomeContext.flush();
                userHomeProviderSensorDefinitionList = userHomeSensorDefinitionWrapper.getProviderSensors();
                boolean isProvidersSensorsNull = true;
                for (Map.Entry<ProviderType, ProviderSensorModel> sensorsMap : sensorModelMap.getSensors().entrySet()) {
                    ProviderSensorDefinitionWrapper dqoHomeProviderSensorDefinitionWrapper = dqoHomeProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                    ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper = userHomeProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                    if(sensorsMap.getValue().isCustom()){
                        if(dqoHomeProviderSensorDefinitionWrapper.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec()) || dqoHomeProviderSensorDefinitionWrapper.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate())){
                            userHomeProviderSensorDefinitionWrapper.markForDeletion();
                        } else {
                            userHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            userHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                        }
                        userHomeContext.flush();
                        isProvidersSensorsNull = false;
                    } else {
                        if (dqoHomeProviderSensorDefinitionWrapper == null && userHomeProviderSensorDefinitionWrapper == null) {
                            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = userHomeProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                            providerSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            providerSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            userHomeContext.flush();
                            isProvidersSensorsNull = false;
                        } else if (dqoHomeProviderSensorDefinitionWrapper == null) {
                            userHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            userHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            userHomeContext.flush();
                            isProvidersSensorsNull = false;
                        } else if (!dqoHomeProviderSensorDefinitionWrapper.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec()) || !dqoHomeProviderSensorDefinitionWrapper.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate())) {
                            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = userHomeProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                            providerSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            providerSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            userHomeContext.flush();
                            isProvidersSensorsNull = false;
                        }
                    }
                }
                if(isProvidersSensorsNull){
                    userHomeSensorDefinitionWrapper.markForDeletion();
                    userHomeContext.flush();
                }
            }
        }
        else {
            if (!dqoHomeSensorDefinitionWrapper.getSpec().equals(sensorModelMap.getSensorDefinitionSpec())) {
                SensorDefinitionWrapper newUserHomeSensorDefinitionWrapper = userHomeSensorDefinitionList.createAndAddNew(sensorModelMap.getSensorName());
                newUserHomeSensorDefinitionWrapper.setSpec(sensorModelMap.getSensorDefinitionSpec());
                userHomeContext.flush();
                for (Map.Entry<ProviderType, ProviderSensorModel> sensorsMap : sensorModelMap.getSensors().entrySet()) {
                    ProviderSensorDefinitionWrapper dqoHomeProviderSensorDefinitionWrapper = dqoHomeProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                    ProviderSensorDefinitionList newUserProviderSensorDefinitionList = newUserHomeSensorDefinitionWrapper.getProviderSensors();
                    if (dqoHomeProviderSensorDefinitionWrapper == null) {
                        ProviderSensorDefinitionWrapper newUserHomeProviderSensorDefinitionWrapper = newUserProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                        newUserHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                        newUserHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                        userHomeContext.flush();
                    } else if ((!dqoHomeProviderSensorDefinitionWrapper.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec())
                            || !dqoHomeProviderSensorDefinitionWrapper.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate()))
                            && !sensorsMap.getValue().isCustom()) {
                        ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper = newUserProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                        userHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                        userHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                        userHomeContext.flush();
                    }
                }
            } else {
                boolean isProvidersSensorsNull = false;
                for (Map.Entry<ProviderType, ProviderSensorModel> sensorsMap : sensorModelMap.getSensors().entrySet()) {
                    ProviderSensorDefinitionWrapper dqoHomeProviderSensorDefinitionWrapper = dqoHomeProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                    if(sensorsMap.getValue().getProviderSensorDefinitionSpec() == null || sensorsMap.getValue().getSqlTemplate() == null){
                        continue;
                    }
                    if (dqoHomeProviderSensorDefinitionWrapper == null && sensorsMap.getValue().getProviderSensorDefinitionSpec() != null && sensorsMap.getValue().getSqlTemplate() != null) {
                        isProvidersSensorsNull = true;
                        break;
                    }
                    if (!dqoHomeProviderSensorDefinitionWrapper.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec())
                            || !dqoHomeProviderSensorDefinitionWrapper.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate())){
                        isProvidersSensorsNull = true;
                        break;
                    }
                }
                if (isProvidersSensorsNull) {
                    SensorDefinitionWrapper newUserHomeSensorDefinitionWrapper = userHomeSensorDefinitionList.createAndAddNew(sensorModelMap.getSensorName());
                    newUserHomeSensorDefinitionWrapper.setSpec(sensorModelMap.getSensorDefinitionSpec());
                    userHomeContext.flush();
                    for (Map.Entry<ProviderType, ProviderSensorModel> sensorsMap : sensorModelMap.getSensors().entrySet()) {
                        ProviderSensorDefinitionWrapper dqoHomeProviderSensorDefinitionWrapper = dqoHomeProviderSensorDefinitionList.getByObjectName(sensorsMap.getKey(), true);
                        ProviderSensorDefinitionList newUserProviderSensorDefinitionList = newUserHomeSensorDefinitionWrapper.getProviderSensors();
                        if (dqoHomeProviderSensorDefinitionWrapper == null) {
                            ProviderSensorDefinitionWrapper newUserHomeProviderSensorDefinitionWrapper = newUserProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                            newUserHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            newUserHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            userHomeContext.flush();
                            continue;
                        }
                        if ((!dqoHomeProviderSensorDefinitionWrapper.getSpec().equals(sensorsMap.getValue().getProviderSensorDefinitionSpec())
                                || !dqoHomeProviderSensorDefinitionWrapper.getSqlTemplate().equals(sensorsMap.getValue().getSqlTemplate()))
                                && !sensorsMap.getValue().isCustom()) {
                            ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper = newUserProviderSensorDefinitionList.createAndAddNew(sensorsMap.getKey());
                            userHomeProviderSensorDefinitionWrapper.setSpec(sensorsMap.getValue().getProviderSensorDefinitionSpec());
                            userHomeProviderSensorDefinitionWrapper.setSqlTemplate(sensorsMap.getValue().getSqlTemplate());
                            userHomeContext.flush();
                        }
                    }
                }
            }
        }
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a sensor.
     * This method handles the HTTP DELETE requests on the "{/folders}" endpoint to delete a sensor. The folders parameter
     * is an array of strings representing the path to the sensor to be deleted. The method first looks up the sensor in the
     * user's home directory using the provided folders, and then deletes it from the system. If the sensor is not found,
     * the method returns an HTTP NOT_FOUND response. If the deletion is successful, the method returns an HTTP NO_CONTENT
     * response.
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

        StringBuilder sensorName = new StringBuilder();
        sensorName.append(folders[0]);
        for (int i = 1; i < folders.length; i++) {
            sensorName.append("/");
            sensorName.append(folders[i]);
        }

        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName.toString(), true);

        if (sensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();
        providerSensorDefinitionList.forEach(s -> {
            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.getByObjectName(s.getProvider(), true);
            providerSensorDefinitionWrapper.markForDeletion();
            userHomeContext.flush();
        });

        sensorDefinitionWrapper.markForDeletion();
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }
}