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
import ai.dqo.rest.models.metadata.ProviderSensorModel;
import ai.dqo.rest.models.metadata.SensorBasicModel;
import ai.dqo.rest.models.metadata.SensorModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of sensors and provider sensors.
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
     * @param userHomeContextFactory      User home context factory.
     */
    @Autowired
    public SensorsController(DqoHomeContextFactory dqoHomeContextFactory,UserHomeContextFactory userHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of builtin sensors.
     * @return List of sensor model.
     */
    @GetMapping("/builtin")
    @ApiOperation(value = "getAllBuiltInSensors", notes = "Returns a list of builtin sensors", response = SensorModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<SensorModel>> getAllBuiltInSensors() {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        SensorDefinitionList sensorDefinitionList= dqoHome.getSensors();
        List<SensorDefinitionWrapper> sensorDefinitionWrapperList = sensorDefinitionList.toList();

        Stream<SensorModel> sensorModel = sensorDefinitionWrapperList.stream().map(s -> new SensorModel(){{
            setSensorName(s.getName());
            setSensorDefinitionSpec(s.getSpec());
        }});

        return new ResponseEntity<>(Flux.fromStream(sensorModel), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a builtin sensor.
     * @param sensorName Sensor name.
     * @return Model of the sensor with specific sensor name.
     */
    @GetMapping("/builtin/{sensorName}")
    @ApiOperation(value = "getBuiltInSensor", notes = "Returns a builtin sensor", response = SensorModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorModel>> getBuiltInSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName
    ) {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        SensorDefinitionList sensorDefinitionList = dqoHome.getSensors();
        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName, true);

        if(sensorDefinitionWrapper==null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        SensorModel sensorModel = new SensorModel();
        sensorModel.setSensorName(sensorName);
        sensorModel.setSensorDefinitionSpec(sensorDefinitionWrapper.getSpec());

        return new ResponseEntity<>(Mono.just(sensorModel), HttpStatus.OK);
    }

    /**
     * Returns a list of builtin provider sensors.
     * @param providerType Provider type.
     * @return Models of provider sensors containing all configurations.
     */
    @GetMapping("/builtin/provider/{providerType}")
    @ApiOperation(value = "getAllProviderBuiltInSensor", notes = "Returns a list of builtin provider sensors", response = ProviderSensorModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProviderSensorModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<ProviderSensorModel>> getAllProviderBuiltInSensor(
            @ApiParam("Provider type") @PathVariable ProviderType providerType
    ){

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList sensorDefinitionList= dqoHome.getSensors();

        Stream<ProviderSensorModel> providerSensorModel = sensorDefinitionList.toList().stream()
                .map(sp -> Pair.of(sp, sp.getProviderSensors().getByObjectName(providerType,true)))
                .filter(pair -> pair.getRight() != null)
                .map(pair -> new ProviderSensorModel(){{
                    setSensorName(pair.getLeft().getName());
                    setProviderType(providerType);
                    setProviderSensorDefinitionSpec(pair.getRight().getSpec());
                    setSqlTemplate(pair.getRight().getSqlTemplate());
                }});
        return new ResponseEntity<>(Flux.fromStream(providerSensorModel), HttpStatus.OK);
    }

    /**
     * Returns the configuration of a builtin provider sensor.
     * @param providerType Provider type.
     * @param sensorName Sensor name.
     * @return Model of provider sensors containing all configurations for specific sensor name.
     */
    @GetMapping("/builtin/provider/{providerType}/{sensorName}")
    @ApiOperation(value = "getProviderBuiltInSensor", notes = "Returns a builtin provider sensor", response = ProviderSensorModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProviderSensorModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<ProviderSensorModel>> getProviderBuiltInSensor(
            @ApiParam("Provider type") @PathVariable ProviderType providerType,
            @ApiParam("Sensor name") @PathVariable String sensorName
    ){

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        SensorDefinitionList sensorDefinitionList = dqoHome.getSensors();

        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName, true);

        if(sensorDefinitionWrapper==null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = sensorDefinitionWrapper
                .getProviderSensors()
                .getByObjectName(providerType,true);

        ProviderSensorDefinitionSpec providerSensorDefinitionSpec = providerSensorDefinitionWrapper.getSpec();

        if(providerSensorDefinitionSpec==null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorModel providerSensorModel = new ProviderSensorModel();
        providerSensorModel.setProviderType(providerType);
        providerSensorModel.setSensorName(sensorName);
        providerSensorModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
        providerSensorModel.setProviderType(providerType);
        providerSensorModel.setProviderSensorDefinitionSpec(providerSensorDefinitionWrapper.getSpec());

        return new ResponseEntity<>(Mono.just(providerSensorModel), HttpStatus.OK);
    }

    /**
     * Returns a list of custom sensors.
     * @return List of custom sensor model.
     */
    @GetMapping("/custom")
    @ApiOperation(value = "getAllCustomSensors", notes = "Returns a list of custom sensors", response = SensorModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<SensorModel>> getAllCustomSensors() {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        List<SensorDefinitionWrapper> sensorDefinitionWrapperList = sensorDefinitionList.toList();
        Stream<SensorModel> sensorModel = sensorDefinitionWrapperList.stream().map(s -> new SensorModel(){{
            setSensorName(s.getName());
            setSensorDefinitionSpec(s.getSpec());
        }});

        return new ResponseEntity<>(Flux.fromStream(sensorModel), HttpStatus.OK);
    }

    /**
     * Returns a configuration of custom sensors.
     * @return Configuration of custom sensor model.
     */
    @GetMapping("/custom/{sensorName}")
    @ApiOperation(value = "getCustomSensor", notes = "Returns a custom sensors", response = SensorModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorModel>> getCustomSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName
    ){

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorDefinitionWrapper existingSensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName,true);
        if (existingSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }
        SensorModel sensorModel = new SensorModel();
        sensorModel.setSensorName(existingSensorDefinitionWrapper.getName());
        sensorModel.setSensorDefinitionSpec(existingSensorDefinitionWrapper.getSpec());

        return new ResponseEntity<>(Mono.just(sensorModel), HttpStatus.OK);
    }

    /**
     * Creates (adds) a new custom sensor given sensor information.
     * @param sensorName Sensor name.
     * @param sensorDefinitionSpec List of sensor definitions.
     * @return Empty response.
     */
    @PostMapping("/custom/{sensorName}")
    @ApiOperation(value = "createSensor", notes = "Creates (adds) a new custom sensor given sensor information.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New custom sensor successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Custom sensor with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createCustomSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName,
            @ApiParam("List of sensor definitions") @RequestBody SensorDefinitionSpec sensorDefinitionSpec) {
        if (Strings.isNullOrEmpty(sensorName) || sensorDefinitionSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorDefinitionWrapper existingSensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName,true);
        if (existingSensorDefinitionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.createAndAddNew(sensorName);
        sensorDefinitionWrapper.setSpec(sensorDefinitionSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing custom sensor.
     * @param sensorName Sensor name.
     * @param sensorDefinitionSpec List of sensor definitions.
     * @return Empty response.
     */
    @PutMapping("/custom/{sensorName}")
    @ApiOperation(value = "updateCustomSensor", notes = "Updates an existing custom sensor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom sensor successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateCustomSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName,
            @ApiParam("List of sensor definitions") @RequestBody SensorDefinitionSpec sensorDefinitionSpec) {
        if (Strings.isNullOrEmpty(sensorName) || sensorDefinitionSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorDefinitionWrapper existingSensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName,true);
        if (existingSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        existingSensorDefinitionWrapper.setSpec(sensorDefinitionSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a custom sensor.
     * @param sensorName Sensor name to delete.
     * @return Empty response.
     */
    @DeleteMapping("/custom/{sensorName}")
    @ApiOperation(value = "deleteCustomSensor", notes = "Deletes an existing custom sensor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom sensor successfully deleted"),
            @ApiResponse(code = 404, message = "Custom sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteCustomSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName) {
        if (Strings.isNullOrEmpty(sensorName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList = userHome.getSensors();

        SensorDefinitionWrapper existingSensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName,true);
        if (existingSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        existingSensorDefinitionWrapper.markForDeletion();
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Returns a custom provider sensor.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @return Custom provider sensor model.
     */
    @GetMapping("/custom/provider/{sensorName}/{providerType}")
    @ApiOperation(value = "getCustomProviderSensor", notes = "Returns a custom provider sensor", response = ProviderSensorModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProviderSensorModel.class),
            @ApiResponse(code = 404, message = "Provider sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<ProviderSensorModel>> getCustomProviderSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName,
            @ApiParam("Provider type") @PathVariable ProviderType providerType
    ) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList = userHome.getSensors();
        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName, true);

        if(sensorDefinitionWrapper == null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = sensorDefinitionWrapper
                .getProviderSensors()
                .getByObjectName(providerType, true);

        if(providerSensorDefinitionWrapper == null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionSpec providerSensorDefinitionSpec = providerSensorDefinitionWrapper.getSpec();

        ProviderSensorModel providerSensorModel = new ProviderSensorModel();
        providerSensorModel.setSensorName(sensorName);
        providerSensorModel.setProviderType(providerType);
        providerSensorModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
        providerSensorModel.setProviderSensorDefinitionSpec(providerSensorDefinitionSpec);

        return new ResponseEntity<>(Mono.just(providerSensorModel), HttpStatus.OK);
    }

    /**
     * Returns a list of custom provider sensors.
     * @param providerType Provider type.
     * @return List of custom provider sensor model.
     */
    @GetMapping("/custom/provider/{providerType}")
    @ApiOperation(value = "getAllCustomProviderSensors", notes = "Returns a list of custom provider sensors", response = ProviderSensorModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProviderSensorModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<ProviderSensorModel>> getAllCustomProviderSensors(
            @ApiParam("Provider type") @PathVariable ProviderType providerType
    ) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        Stream<ProviderSensorModel> providerSensorModel = sensorDefinitionList.toList().stream()
                .map(sp -> Pair.of(sp, sp.getProviderSensors().getByObjectName(providerType,true)))
                .filter(pair -> pair.getRight() != null)
                .map(pair -> new ProviderSensorModel(){{
                    setSensorName(pair.getLeft().getName());
                    setProviderType(providerType);
                    setProviderSensorDefinitionSpec(pair.getRight().getSpec());
                    setSqlTemplate(pair.getRight().getSqlTemplate());
                }});

        return new ResponseEntity<>(Flux.fromStream(providerSensorModel), HttpStatus.OK);
    }

    /**
     * Creates (adds) a new custom provider sensor given sensor information.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @param providerSensorModel Provider sensor model.
     * @return Empty response.
     */
    @PostMapping("/custom/provider/{sensorName}/{providerType}")
    @ApiOperation(value = "createProviderSensor", notes = "Creates (adds) a new custom provider sensor given sensor information.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New custom provider sensor successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Provider sensor with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createCustomProviderSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName,
            @ApiParam("ProviderType") @PathVariable ProviderType providerType,
            @ApiParam("Provider sensor model") @RequestBody ProviderSensorModel providerSensorModel) {
        if (Strings.isNullOrEmpty(sensorName) || providerSensorModel == null || providerType == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName,true);

        if (sensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionList providerSensorDefinitionList = sensorDefinitionWrapper.getProviderSensors();
        ProviderSensorDefinitionWrapper existingProviderSensorDefinitionWrapper = providerSensorDefinitionList.getByObjectName(providerType, true);
        if (existingProviderSensorDefinitionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = providerSensorDefinitionList.createAndAddNew(providerType);
        providerSensorDefinitionWrapper.setSqlTemplate(providerSensorModel.getSqlTemplate());
        providerSensorDefinitionWrapper.setSpec(providerSensorModel.getProviderSensorDefinitionSpec());
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates a SQL template for existing custom provider sensor.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @param providerSensorModel Provider Sensor model.
     * @return Empty response.
     */
    @PutMapping("/custom/provider/{sensorName}/{providerType}/sqltemplate")
    @ApiOperation(value = "updateCustomProviderSensorSqlTemplate", notes = "Updates an existing custom provider sensor SQL templates")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom provider sensor SQL template successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Provider sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateCustomProviderSensorSqlTemplate(
            @ApiParam("Sensor name") @PathVariable String sensorName,
            @ApiParam("Provider type") @PathVariable ProviderType providerType,
            @ApiParam("Provider sensor model") @RequestBody ProviderSensorModel providerSensorModel) {
        if (Strings.isNullOrEmpty(sensorName) ||  providerSensorModel.getSqlTemplate() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorDefinitionWrapper existingSensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName,true);
        if (existingSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = existingSensorDefinitionWrapper
                .getProviderSensors().getByObjectName(providerType, true);

        if (providerSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        providerSensorDefinitionWrapper.setSqlTemplate(providerSensorModel.getSqlTemplate());

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Updates a providerSensorSpec for existing custom provider sensor.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @param providerSensorDefinitionSpec List of provider sensor definitions.
     * @return Empty response.
     */
    @PutMapping("/custom/provider/{sensorName}/{providerType}/spec")
    @ApiOperation(value = "updateCustomProviderSensorSpec", notes = "Updates an existing custom provider sensor spec")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom provider sensor spec successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Provider sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateCustomProviderSensorSpec(
            @ApiParam("Sensor name") @PathVariable String sensorName,
            @ApiParam("Provider type") @PathVariable ProviderType providerType,
            @ApiParam("Provider sensor model") @RequestBody ProviderSensorDefinitionSpec providerSensorDefinitionSpec) {
        if (Strings.isNullOrEmpty(sensorName) || providerType == null || providerSensorDefinitionSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorDefinitionWrapper existingSensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName,true);
        if (existingSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = existingSensorDefinitionWrapper
                .getProviderSensors().getByObjectName(providerType, true);

        if (providerSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        providerSensorDefinitionWrapper.setSpec(providerSensorDefinitionSpec);

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a custom provider sensor.
     * @param sensorName Sensor name.
     * @param providerType Provider type to delete.
     * @return Empty response.
     */
    @DeleteMapping("/custom/provider/{sensorName}/{providerType}")
    @ApiOperation(value = "deleteCustomProviderSensor", notes = "Deletes an existing custom provider sensor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Custom provider sensor successfully deleted"),
            @ApiResponse(code = 404, message = "Custom sensor not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteCustomProviderSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName,
            @ApiParam("Provider type") @PathVariable ProviderType providerType) {
        if (Strings.isNullOrEmpty(sensorName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList sensorDefinitionList= userHome.getSensors();

        SensorDefinitionWrapper existingSensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName,true);
        if (existingSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = existingSensorDefinitionWrapper
                .getProviderSensors().getByObjectName(providerType, true);

        if (providerSensorDefinitionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        providerSensorDefinitionWrapper.markForDeletion();
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Returns a list of combined sensors.
     * @return List of sensor model.
     */
    @GetMapping("/combined")
    @ApiOperation(value = "getAllCombinedSensors", notes = "Returns a list of combined sensors", response = SensorModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<SensorModel>> getAllCombinedSensors() {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        SensorDefinitionList sensorDefinitionList= dqoHome.getSensors();
        List<SensorDefinitionWrapper> sensorDefinitionWrapperList = sensorDefinitionList.toList();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList userHomeSensors = userHome.getSensors();
        List<SensorDefinitionWrapper> userSensorDefinitionWrapperList = userHomeSensors.toList();

        /*
         * Create a Hashmap to store data on combined sensors.
         * The key is sensor name and the value is SensorDefinitionWrapper.
         */
        Map<String, SensorDefinitionWrapper> sensorDefinitionMap = new HashMap<>();

        /*
         * All custom sensors (in user home) stored in user home are added first.
         */
        for (SensorDefinitionWrapper sensorDefinitionWrapper: userSensorDefinitionWrapperList) {
            sensorDefinitionMap.put(sensorDefinitionWrapper.getName(), sensorDefinitionWrapper);
        }

        /*
         * If the same sensor is defined both as custom (in user home)
         * and as builtin (in dqo home), we return the custom definition.
         */
        for (SensorDefinitionWrapper sensorDefinitionWrapper: sensorDefinitionWrapperList) {
            if(!sensorDefinitionMap.containsKey(sensorDefinitionWrapper.getName())){
                sensorDefinitionMap.put(sensorDefinitionWrapper.getName(), sensorDefinitionWrapper);
            }
        }
        Stream<SensorModel> sensorModelStream = sensorDefinitionMap.values().stream().map(s -> new SensorModel(){{
                    setSensorName(s.getName());
                    setSensorDefinitionSpec(s.getSpec());
        }});

        return new ResponseEntity<>(Flux.fromStream(sensorModelStream), HttpStatus.OK);
    }

    /**
     * Returns a combined sensor.
     * @return Sensor model.
     */
    @GetMapping("/combined/{sensorName}")
    @ApiOperation(value = "getCombinedSensor", notes = "Returns a combined sensor", response = SensorModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorModel>> getCombinedSensor(
            @ApiParam("Sensor name") @PathVariable String sensorName
    ) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        SensorDefinitionList userHomeSensors = userHome.getSensors();
        SensorDefinitionList sensorDefinitionList= dqoHome.getSensors();

        SensorDefinitionWrapper sensorDefinitionWrapper = userHomeSensors.getByObjectName(sensorName, true);

        /*
         * Check if the sensor definition exists in User home.
         * If not exists, find sensor definition in DQO Home.
         */
        if(sensorDefinitionWrapper == null){
            sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName, true); //check if sensor definition exists in DQO Home
        }

        if(sensorDefinitionWrapper == null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        SensorModel sensorModel = new SensorModel();
        sensorModel.setSensorName(sensorName);
        sensorModel.setSensorDefinitionSpec(sensorDefinitionWrapper.getSpec());


        return new ResponseEntity<>(Mono.just(sensorModel), HttpStatus.OK);
    }
    /**
     * Returns a list of combined provider sensors.
     * @return List of provider sensor model.
     */
    @GetMapping("/combined/provider/{providerType}")
    @ApiOperation(value = "getAllCombinedProviderSensors", notes = "Returns a list of combined provider sensors", response = ProviderSensorModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProviderSensorModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<ProviderSensorModel>> getAllCombinedProviderSensors(
            @ApiParam("Provider type") @PathVariable ProviderType providerType
    ) {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList sensorDefinitionList= dqoHome.getSensors(); //Get sensors definitions from DQO Home

        List<SensorDefinitionWrapper> homeSensorDefinitionWrapper = sensorDefinitionList.toList().stream()
                .map(sp -> Pair.of(sp, sp.getProviderSensors().getByObjectName(providerType,true)))
                .filter(pair -> pair.getRight() != null).map(Pair::getLeft).collect(Collectors.toList()); //Check if the builtin sensor definition exists for the specified provider

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorDefinitionList userHomeSensorsDefinitionList = userHome.getSensors(); //Get sensors definitions from UserHome

        List<SensorDefinitionWrapper> userSensorDefinitionWrapper = userHomeSensorsDefinitionList.toList().stream()
                .map(sp -> Pair.of(sp, sp.getProviderSensors().getByObjectName(providerType,true)))
                .filter(pair -> pair.getRight() != null).map(Pair::getLeft).collect(Collectors.toList()); //Check if the custom sensor definition exists for the specified provider

        /*
         * Create a Hashmap to store data on combined sensors.
         * The key is sensor name and the value is SensorDefinitionWrapper.
         */
        Map<String, SensorDefinitionWrapper> providerSensorDefinitionMap = new HashMap<>();

        /*
         * All custom provider sensors (in user home) stored in user home are added first.
         */
        for (SensorDefinitionWrapper sensorDefinitionWrapper: userSensorDefinitionWrapper) {
            providerSensorDefinitionMap.put(sensorDefinitionWrapper.getName(), sensorDefinitionWrapper);
        }

        /*
         * If the same provider sensor is defined both as custom (in user home)
         * and as builtin (in dqo home), we return the custom definition.
         */
        for (SensorDefinitionWrapper sensorDefinitionWrapper: homeSensorDefinitionWrapper) {
            if(!providerSensorDefinitionMap.containsKey(sensorDefinitionWrapper.getName())){
                providerSensorDefinitionMap.put(sensorDefinitionWrapper.getName(), sensorDefinitionWrapper);
            }
        }

        Stream<ProviderSensorModel> providerSensorModelStream = providerSensorDefinitionMap.values().stream().map(s -> new ProviderSensorModel(){{
                    setSensorName(s.getName());
                    setProviderType(providerType);
                    setProviderSensorDefinitionSpec(s.getProviderSensors().getByObjectName(providerType, true).getSpec());
                    setSqlTemplate(s.getProviderSensors().getByObjectName(providerType, true).getSqlTemplate());
                }});

        return new ResponseEntity<>(Flux.fromStream(providerSensorModelStream), HttpStatus.OK);
    }

    /**
     * Returns a provider combined sensor.
     * @return Provider sensor model.
     */
    @GetMapping("/combined/provider/{providerType}/{sensorName}")
    @ApiOperation(value = "getCombinedProviderSensor", notes = "Returns a combined provider sensor", response = ProviderSensorModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProviderSensorModel.class),
            @ApiResponse(code = 404, message = "Sensor name not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<ProviderSensorModel>> getCombinedProviderSensor(
            @ApiParam("Provider type") @PathVariable ProviderType providerType,
            @ApiParam("Sensor name") @PathVariable String sensorName
    ) {

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        SensorDefinitionList userHomeSensors = userHome.getSensors();
        SensorDefinitionWrapper sensorDefinitionWrapper = userHomeSensors.getByObjectName(sensorName, true);

        /*
         * Check if the provider sensor definition exists in User home.
         * If not exists, find provider sensor definition in DQO Home.
         */
        if(sensorDefinitionWrapper == null){
            DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
            DqoHome dqoHome = dqoHomeContext.getDqoHome();
            SensorDefinitionList sensorDefinitionList= dqoHome.getSensors();
            sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName, true);
        }

        if(sensorDefinitionWrapper == null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = sensorDefinitionWrapper
                .getProviderSensors()
                .getByObjectName(providerType, true);

        if(providerSensorDefinitionWrapper == null){
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        ProviderSensorDefinitionSpec providerSensorDefinitionSpec = providerSensorDefinitionWrapper.getSpec();

        ProviderSensorModel providerSensorModel = new ProviderSensorModel();
        providerSensorModel.setSensorName(sensorName);
        providerSensorModel.setProviderType(providerType);
        providerSensorModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
        providerSensorModel.setProviderSensorDefinitionSpec(providerSensorDefinitionSpec);

        return new ResponseEntity<>(Mono.just(providerSensorModel), HttpStatus.OK);
    }

    /**
     * Returns all combined sensor basic model.
     * @return sensor basic model.
     */
    @GetMapping("/combinedbasic")
    @ApiOperation(value = "getAllSensorsBasic", notes = "Returns a list of combined basic sensors", response = SensorBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SensorBasicModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<SensorBasicModel>> getAllSensorsBasic() {

        SensorBasicModel sensorBasicModel = new SensorBasicModel();

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        List<SensorDefinitionWrapper> sensorDefinitionList= dqoHome.getSensors().toList();

        for (SensorDefinitionWrapper sensorDefinitionWrapper : sensorDefinitionList) {
            String sensorName = sensorDefinitionWrapper.getName();
            sensorBasicModel.addChild(sensorName, false);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<SensorDefinitionWrapper> userHomeSensors = userHome.getSensors().toList();

        for (SensorDefinitionWrapper sensorDefinitionWrapper : userHomeSensors) {
            String sensorName = sensorDefinitionWrapper.getName();
            sensorBasicModel.addChild(sensorName, true);
        }

        return new ResponseEntity<>(Mono.just(sensorBasicModel), HttpStatus.OK);
    }

}