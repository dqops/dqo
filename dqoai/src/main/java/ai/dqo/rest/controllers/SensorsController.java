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
import ai.dqo.core.jobqueue.DqoQueueJobId;
import ai.dqo.metadata.basespecs.ElementWrapper;
import ai.dqo.metadata.definitions.sensors.*;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.metadata.*;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import autovalue.shaded.com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

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
     * Returns the configuration of a sensor, first checking if it is a custom sensor,
     * then checking if it is a built-in sensor.
     * @param fullSensorName Full sensor name.
     * @return Model of the sensor with specific sensor name.
     */
    @GetMapping("/{fullSensorName}")
    @ApiOperation(value = "getSensor", notes = "Returns a sensor model", response = SensorModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorModel>> getSensor(
            @ApiParam("Full sensor name") @PathVariable String fullSensorName) {

        if (Strings.isNullOrEmpty(fullSensorName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList userHomeSensorDefinitionList = userHome.getSensors();
        Optional<SensorDefinitionWrapper> userHomeSensorDefinitionWrapperOptional =
                Optional.ofNullable(userHomeSensorDefinitionList.getByObjectName(fullSensorName, true));

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();
        Optional<SensorDefinitionWrapper> dqoHomeSensorDefinitionWrapperOptional =
                Optional.ofNullable(dqoHomeSensorDefinitionList.getByObjectName(fullSensorName, true));

        if (userHomeSensorDefinitionWrapperOptional.isEmpty() && dqoHomeSensorDefinitionWrapperOptional.isEmpty()) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        SensorModel sensorModel = new SensorModel();

        dqoHomeSensorDefinitionWrapperOptional.ifPresent(sensorDefinitionWrapper -> {
            sensorModel.setFullSensorName(sensorDefinitionWrapper.getName());
            sensorModel.setSensorDefinitionSpec(sensorDefinitionWrapper.getSpec());
            sensorModel.setBuiltIn(true);
            List<ProviderSensorModel> providerSensorBasicModelList = sensorDefinitionWrapper.getProviderSensors()
                    .toList().stream()
                    .map(providerSensorWrapper -> new ProviderSensorModel(
                            providerSensorWrapper.getProvider(),
                            providerSensorWrapper.getSpec(),
                            providerSensorWrapper.getSqlTemplate(),
                            userHomeSensorDefinitionWrapperOptional.isPresent(),
                            dqoHomeSensorDefinitionWrapperOptional.isPresent()))
                    .collect(Collectors.toList());
            sensorModel.addProviderSensorModel(providerSensorBasicModelList);
        });

        userHomeSensorDefinitionWrapperOptional.ifPresent(sensorDefinitionWrapper -> {
            sensorModel.setFullSensorName(sensorDefinitionWrapper.getName());
            sensorModel.setSensorDefinitionSpec(sensorDefinitionWrapper.getSpec());
            sensorModel.setCustom(true);
            List<ProviderSensorModel> providerSensorBasicModelList = sensorDefinitionWrapper.getProviderSensors()
                    .toList().stream()
                    .map(providerSensorWrapper -> new ProviderSensorModel(
                            providerSensorWrapper.getProvider(),
                            providerSensorWrapper.getSpec(),
                            providerSensorWrapper.getSqlTemplate(),
                            userHomeSensorDefinitionWrapperOptional.isPresent(),
                            dqoHomeSensorDefinitionWrapperOptional.isPresent()))
                    .collect(Collectors.toList());
            sensorModel.addProviderSensorModel(providerSensorBasicModelList);
        });

        return new ResponseEntity<>(Mono.just(sensorModel), HttpStatus.OK);
    }

    /**
     * Create a new sensor given sensor information.
     * @param fullSensorName Full sensor name
     * @param sensorModel sensor model
     */
    @PostMapping("/{fullSensorName}")
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
            @ApiParam("Full sensor name") @PathVariable String fullSensorName,
            @ApiParam("Dictionary of sensor definitions") @RequestBody SensorModel sensorModel) {

        if (Strings.isNullOrEmpty(fullSensorName) || sensorModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList userHomeSensorDefinitionList = userHome.getSensors();

        SensorDefinitionWrapper userHomeSensorDefinitionWrapper = userHomeSensorDefinitionList.getByObjectName(fullSensorName, true);

        if(userHomeSensorDefinitionWrapper != null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        SensorDefinitionWrapper sensorDefinitionWrapper = userHomeSensorDefinitionList.createAndAddNew(fullSensorName);
        userHomeContext.flush();

        sensorDefinitionWrapper.setSpec(sensorModel.getSensorDefinitionSpec());
        ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();
        sensorModel.getProviderSensorList().forEach(n -> {
            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.createAndAddNew(n.getProviderType());
            providerSensorDefinitionWrapper.setSqlTemplate(n.getSqlTemplate());
            providerSensorDefinitionWrapper.setSpec(n.getProviderSensorDefinitionSpec());
            userHomeContext.flush();
        });

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing sensor, creating possibly a custom sensor,
     * or removes sensor if custom definition is same as Dqo Home sensor
     * @param sensorModel sensor model
     */
    @PutMapping("/{fullSensorName}")
    @ApiOperation(value = "updateSensor", notes = "Updates an existing sensor, making a custom sensor definition if it is not present. \n" +
            "Removes sensor if custom definition is same as Dqo Home sensor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sensor model successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateSensor(
            @ApiParam("Full sensor name") @PathVariable String fullSensorName,
            @ApiParam("Dictionary of sensor definitions") @RequestBody SensorModel sensorModel) {

        if (Strings.isNullOrEmpty(fullSensorName) || sensorModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList userHomeSensorDefinitionList = userHome.getSensors();
        SensorDefinitionWrapper userHomeSensorDefinitionWrapper = userHomeSensorDefinitionList.getByObjectName(fullSensorName,true);

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();
        SensorDefinitionWrapper dqoHomeSensorDefinitionWrapper = dqoHomeSensorDefinitionList.getByObjectName(fullSensorName,true);

        if (userHomeSensorDefinitionWrapper == null && dqoHomeSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }
        List<ProviderSensorModel> providerSensorModels = sensorModel.getProviderSensorList();

        ProviderSensorDefinitionList dqoHomeSensorDefinitionWrapperProviderSensors = dqoHomeSensorDefinitionWrapper.getProviderSensors();

        if(userHomeSensorDefinitionWrapper == null){
            if(!sensorModel.equalsSensorDqo(dqoHomeSensorDefinitionWrapper)){
                SensorDefinitionWrapper sensorDefinitionWrapper = userHomeSensorDefinitionList.createAndAddNew(fullSensorName);
                sensorDefinitionWrapper.setSpec(sensorModel.getSensorDefinitionSpec());

                ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();
                for (ProviderSensorModel providerSensorModel: providerSensorModels){
                    ProviderSensorDefinitionWrapper providerSensorDefinition =
                            dqoHomeSensorDefinitionWrapperProviderSensors.getByObjectName(providerSensorModel.getProviderType(), true);
                    if(!providerSensorModel.equalsProviderSensorDqo(providerSensorDefinition)){
                        ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper =
                                providerSensorDefinitionList.createAndAddNew(providerSensorModel.getProviderType());
                        userHomeProviderSensorDefinitionWrapper.setSpec(providerSensorModel.getProviderSensorDefinitionSpec());
                        userHomeProviderSensorDefinitionWrapper.setSqlTemplate(providerSensorModel.getSqlTemplate());
                    }
                }
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
        }

        ProviderSensorDefinitionList providerSensorDefinitionList = userHomeSensorDefinitionWrapper.getProviderSensors();

        if (sensorModel.equalsSensorDqo(dqoHomeSensorDefinitionWrapper)){
            for (ProviderSensorModel providerSensorModel: providerSensorModels){
                ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper =
                        providerSensorDefinitionList.getByObjectName(providerSensorModel.getProviderType(), true);
                if(userHomeProviderSensorDefinitionWrapper != null){
                    userHomeProviderSensorDefinitionWrapper.markForDeletion();
                    userHomeContext.flush();
                }
            }
            userHomeSensorDefinitionWrapper.markForDeletion();
            userHomeContext.flush();
        } else {
            for (ProviderSensorModel providerSensorModel: providerSensorModels) {
                ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper =
                        providerSensorDefinitionList.getByObjectName(providerSensorModel.getProviderType(), true);
                ProviderSensorDefinitionWrapper dqoHomeProviderSensorDefinitionWrapper =
                        dqoHomeSensorDefinitionWrapperProviderSensors.getByObjectName(providerSensorModel.getProviderType(), true);

                if(!providerSensorModel.equalsProviderSensorDqo(dqoHomeProviderSensorDefinitionWrapper) && userHomeProviderSensorDefinitionWrapper == null) {
                    userHomeProviderSensorDefinitionWrapper = providerSensorDefinitionList.createAndAddNew(providerSensorModel.getProviderType());
                    userHomeProviderSensorDefinitionWrapper.setSpec(providerSensorModel.getProviderSensorDefinitionSpec());
                    userHomeProviderSensorDefinitionWrapper.setSqlTemplate(providerSensorModel.getSqlTemplate());
                } else if (!providerSensorModel.equalsProviderSensorDqo(dqoHomeProviderSensorDefinitionWrapper) && userHomeProviderSensorDefinitionWrapper != null) {
                    userHomeProviderSensorDefinitionWrapper.setSpec(providerSensorModel.getProviderSensorDefinitionSpec());
                    userHomeProviderSensorDefinitionWrapper.setSqlTemplate(providerSensorModel.getSqlTemplate());
                } else if (userHomeProviderSensorDefinitionWrapper != null){
                    userHomeProviderSensorDefinitionWrapper.markForDeletion();
                }

                userHomeContext.flush();
            }
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a custom sensor definition.
     * @param fullSensorName  Full sensor name.
     * @return Empty response.
     */
    @DeleteMapping("/{fullSensorName}")
    @ApiOperation(value = "deleteSensor", notes = "Deletes a custom sensor definition")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Custom sensor definition successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Custom sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteSensor(
            @ApiParam("Full sensor name") @PathVariable String fullSensorName) {

        if (Strings.isNullOrEmpty(fullSensorName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList userSensorDefinitionList = userHome.getSensors();
        SensorDefinitionWrapper userSensorDefinitionWrapper = userSensorDefinitionList.getByObjectName(fullSensorName, true);

        if (userSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }
        userSensorDefinitionWrapper.markForDeletion();
        userSensorDefinitionWrapper.getProviderSensors().toList().forEach(ElementWrapper::markForDeletion);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Returns all combined sensor folder model.
     * @return sensor basic folder model.
     */
    @GetMapping
    @ApiOperation(value = "getSensorFolderTree", notes = "Returns a tree of all sensors available in DQO, both built-in sensors and user defined or customized sensors.",
            response = SensorBasicFolderModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorBasicFolderModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorBasicFolderModel>> getSensorFolderTree() {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();

        SensorBasicFolderModel sensorFolderModel = new SensorBasicFolderModel();


        dqoHome.getSensors().forEach(sensorDefinitionWrapper -> {
            SensorDefinitionWrapper dqoHomeSensorDefinitionWrapper =
                    dqoHomeSensorDefinitionList.getByObjectName(sensorDefinitionWrapper.getName(), true);

            List<ProviderSensorBasicModel> providerSensorBasicModelList = new ArrayList<>();
            for (ProviderType providerType : ProviderType.values()) {
                ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                        dqoHomeSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
                if (providerSensorDefinitionWrapper != null) {
                    ProviderSensorBasicModel providerSensorBasicModel = new ProviderSensorBasicModel();
                    providerSensorBasicModel.setProviderType(providerType);
                    providerSensorBasicModel.setSensorSource(SensorDefinitionSource.BUILT_IN);
                    providerSensorBasicModelList.add(providerSensorBasicModel);

                }
            }
            sensorFolderModel.addSensor(sensorDefinitionWrapper.getName(), providerSensorBasicModelList, SensorDefinitionSource.BUILT_IN);
        });

        userHome.getSensors().forEach(sensorDefinitionWrapper -> {
            List<ProviderSensorBasicModel> providerSensorBasicModelList = new ArrayList<>();
            sensorDefinitionWrapper.getProviderSensors().forEach(providerSensorDefinitionWrapper -> {
                ProviderSensorBasicModel providerSensorBasicModel = new ProviderSensorBasicModel();
                if (providerSensorDefinitionWrapper != null) {
                    providerSensorBasicModel.setProviderType(providerSensorDefinitionWrapper.getProvider());
                    providerSensorBasicModel.setSensorSource(SensorDefinitionSource.CUSTOM);
                }
                providerSensorBasicModelList.add(providerSensorBasicModel);
            });

            sensorFolderModel.addSensor(sensorDefinitionWrapper.getName(), providerSensorBasicModelList, SensorDefinitionSource.CUSTOM);
        });

        return new ResponseEntity<>(Mono.just(sensorFolderModel), HttpStatus.OK);
    }
}