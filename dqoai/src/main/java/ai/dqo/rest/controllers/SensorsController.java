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
import ai.dqo.rest.models.metadata.ProviderSensorModel;
import ai.dqo.rest.models.metadata.SensorModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
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

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param dqoHomeContextFactory      Dqo home context factory.
     */
    @Autowired
    public SensorsController(DqoHomeContextFactory dqoHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
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
        List<SensorDefinitionWrapper> SensorDefinitionWrapperList = sensorDefinitionList.toList();

        Stream<SensorModel> sensorModel = SensorDefinitionWrapperList.stream().map(s -> new SensorModel(){{
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
        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorName, false);

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
    ) {

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
    ) {

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

}