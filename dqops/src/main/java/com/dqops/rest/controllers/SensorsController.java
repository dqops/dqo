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
package com.dqops.rest.controllers;

import com.dqops.connectors.ProviderType;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionList;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.*;
import com.dqops.rest.models.platform.SpringErrorPayload;
import autovalue.shaded.com.google.common.base.Strings;
import com.dqops.core.principal.DqoUserPrincipal;
import io.swagger.annotations.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST api controller to manage the list of sensors.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Sensors", description = "Sensors definition management")
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
     * Returns a flat list of all sensors.
     * @return List of all sensors.
     */
    @GetMapping(value = "/sensors", produces = "application/json")
    @ApiOperation(value = "getAllSensors", notes = "Returns a flat list of all sensors available in DQO, both built-in sensors and user defined or customized sensors.",
            response = SensorListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<SensorListModel>> getAllSensors(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        SensorFolderModel sensorFolderModel = createSensorTreeModel(principal);
        List<SensorListModel> allSensors = sensorFolderModel.getAllSensors();
        allSensors.sort(Comparator.comparing(model -> model.getFullSensorName()));

        return new ResponseEntity<>(Flux.fromStream(allSensors.stream()), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a sensor, first checking if it is a custom sensor,
     * then checking if it is a built-in sensor.
     * @param fullSensorName Full sensor name.
     * @return Model of the sensor with specific sensor name.
     */
    @GetMapping(value = "/sensors/{fullSensorName}", produces = "application/json")
    @ApiOperation(value = "getSensor", notes = "Returns a sensor model", response = SensorModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<SensorModel>> getSensor(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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

        boolean canEditDefinitions = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

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
                            dqoHomeSensorDefinitionWrapperOptional.isPresent(),
                            canEditDefinitions))
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
                            dqoHomeSensorDefinitionWrapperOptional.isPresent(),
                            canEditDefinitions))
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
    @PostMapping(value = "/sensors/{fullSensorName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createSensor", notes = "Creates (adds) a new sensor given sensor information.",
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New sensor successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Sensor with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> createSensor(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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

        sensorDefinitionWrapper.setSpec(sensorModel.getSensorDefinitionSpec());
        ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();
        sensorModel.getProviderSensorList().forEach(n -> {
            ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.createAndAddNew(n.getProviderType());
            providerSensorDefinitionWrapper.setSqlTemplate(n.getSqlTemplate());
            providerSensorDefinitionWrapper.setSpec(n.getProviderSensorDefinitionSpec());

        });

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing sensor, creating possibly a custom sensor,
     * or removes sensor if custom definition is same as Dqo Home sensor
     * @param sensorModel sensor model
     */
    @PutMapping(value = "/sensors/{fullSensorName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateSensor", notes = "Updates an existing sensor, making a custom sensor definition if it is not present. \n" +
            "Removes sensor if custom definition is same as Dqo Home sensor",
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sensor model successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> updateSensor(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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

        ProviderSensorDefinitionList dqoHomeSensorDefinitionWrapperProviderSensors =
                dqoHomeSensorDefinitionWrapper != null ? dqoHomeSensorDefinitionWrapper.getProviderSensors() : null;

        if (userHomeSensorDefinitionWrapper == null) {
            if (!sensorModel.equalsSensorDqo(dqoHomeSensorDefinitionWrapper)) {
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

        ProviderSensorDefinitionList userProviderSensorDefinitionList = userHomeSensorDefinitionWrapper.getProviderSensors();

        if (sensorModel.equalsSensorDqo(dqoHomeSensorDefinitionWrapper)) {
            for (ProviderSensorModel providerSensorModel: providerSensorModels){
                ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper =
                        userProviderSensorDefinitionList.getByObjectName(providerSensorModel.getProviderType(), true);
                if (userHomeProviderSensorDefinitionWrapper != null){
                    userHomeProviderSensorDefinitionWrapper.markForDeletion();
                }
            }
            userHomeSensorDefinitionWrapper.markForDeletion();
        } else {
            userHomeSensorDefinitionWrapper.setSpec(sensorModel.getSensorDefinitionSpec());

            for (ProviderSensorModel providerSensorModel: providerSensorModels) {
                ProviderSensorDefinitionWrapper userHomeProviderSensorDefinitionWrapper =
                        userProviderSensorDefinitionList.getByObjectName(providerSensorModel.getProviderType(), true);

                ProviderSensorDefinitionWrapper dqoHomeProviderSensorDefinitionWrapper =
                        dqoHomeSensorDefinitionWrapperProviderSensors != null ?
                        dqoHomeSensorDefinitionWrapperProviderSensors.getByObjectName(providerSensorModel.getProviderType(), true) : null;

                if (!providerSensorModel.equalsProviderSensorDqo(dqoHomeProviderSensorDefinitionWrapper) && userHomeProviderSensorDefinitionWrapper == null) {
                    userHomeProviderSensorDefinitionWrapper = userProviderSensorDefinitionList.createAndAddNew(providerSensorModel.getProviderType());
                    userHomeProviderSensorDefinitionWrapper.setSpec(providerSensorModel.getProviderSensorDefinitionSpec());
                    userHomeProviderSensorDefinitionWrapper.setSqlTemplate(providerSensorModel.getSqlTemplate());
                } else if (!providerSensorModel.equalsProviderSensorDqo(dqoHomeProviderSensorDefinitionWrapper) && userHomeProviderSensorDefinitionWrapper != null) {
                    userHomeProviderSensorDefinitionWrapper.setSpec(providerSensorModel.getProviderSensorDefinitionSpec());
                    userHomeProviderSensorDefinitionWrapper.setSqlTemplate(providerSensorModel.getSqlTemplate());
                } else if (userHomeProviderSensorDefinitionWrapper != null){
                    userHomeProviderSensorDefinitionWrapper.markForDeletion();
                }
            }
        }

        userHomeContext.flush();


        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a custom sensor definition.
     * @param fullSensorName  Full sensor name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/sensors/{fullSensorName}", produces = "application/json")
    @ApiOperation(value = "deleteSensor", notes = "Deletes a custom sensor definition",
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom sensor definition successfully deleted"),
            @ApiResponse(code = 404, message = "Custom sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> deleteSensor(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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
    @GetMapping(value = "/definitions/sensors", produces = "application/json")
    @ApiOperation(value = "getSensorFolderTree", notes = "Returns a tree of all sensors available in DQO, both built-in sensors and user defined or customized sensors.",
            response = SensorFolderModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorFolderModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<SensorFolderModel>> getSensorFolderTree(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        SensorFolderModel sensorFolderModel = createSensorTreeModel(principal);

        return new ResponseEntity<>(Mono.just(sensorFolderModel), HttpStatus.OK);
    }

    /**
     * Creates a tree with all defined sensors.
     * @return A tree with all defined sensors.
     */
    @NotNull
    private SensorFolderModel createSensorTreeModel(DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();

        SensorFolderModel sensorFolderModel = new SensorFolderModel();
        boolean canEditDefinitions = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        dqoHome.getSensors().forEach(sensorDefinitionWrapper -> {
            SensorDefinitionWrapper dqoHomeSensorDefinitionWrapper =
                    dqoHomeSensorDefinitionList.getByObjectName(sensorDefinitionWrapper.getName(), true);

            List<ProviderSensorListModel> providerSensorListModelList = new ArrayList<>();
            for (ProviderType providerType : ProviderType.values()) {
                ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                        dqoHomeSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
                if (providerSensorDefinitionWrapper != null) {
                    ProviderSensorListModel providerSensorListModel = new ProviderSensorListModel();
                    providerSensorListModel.setProviderType(providerType);
                    providerSensorListModel.setSensorSource(SensorDefinitionSource.BUILT_IN);
                    providerSensorListModel.setCanEdit(canEditDefinitions);
                    providerSensorListModelList.add(providerSensorListModel);
                }
            }
            sensorFolderModel.addSensor(sensorDefinitionWrapper.getName(), providerSensorListModelList, SensorDefinitionSource.BUILT_IN, canEditDefinitions);
        });

        userHome.getSensors().forEach(sensorDefinitionWrapper -> {
            List<ProviderSensorListModel> providerSensorListModelList = new ArrayList<>();
            sensorDefinitionWrapper.getProviderSensors().forEach(providerSensorDefinitionWrapper -> {
                ProviderSensorListModel providerSensorListModel = new ProviderSensorListModel();
                if (providerSensorDefinitionWrapper != null) {
                    providerSensorListModel.setProviderType(providerSensorDefinitionWrapper.getProvider());
                    providerSensorListModel.setSensorSource(SensorDefinitionSource.CUSTOM);
                    providerSensorListModel.setCanEdit(canEditDefinitions);
                }
                providerSensorListModelList.add(providerSensorListModel);
            });

            sensorFolderModel.addSensor(sensorDefinitionWrapper.getName(), providerSensorListModelList, SensorDefinitionSource.CUSTOM, canEditDefinitions);
        });

        return sensorFolderModel;
    }
}