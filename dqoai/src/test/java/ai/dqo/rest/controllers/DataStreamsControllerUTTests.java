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

import ai.dqo.BaseTest;
import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.groupings.DataStreamLevelSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpecMap;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.rest.models.metadata.DataStreamBasicModel;
import ai.dqo.rest.models.metadata.DataStreamModel;
import ai.dqo.rest.models.metadata.DataStreamTrimmedModel;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
public class DataStreamsControllerUTTests extends BaseTest {
    private DataStreamsController sut;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext userHomeContext;
    private SampleTableMetadata sampleTable;
    private final static String DATASTREAM_NAME_1 = "date_level3";
    private final static String DATASTREAM_NAME_2 = "value_level5";

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.sut = new DataStreamsController(this.userHomeContextFactory);
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.continuous_days_one_row_per_day,
                ProviderType.bigquery);

        DataStreamMappingSpec dsmSpec1 = new DataStreamMappingSpec(){{
            setLevel3(new DataStreamLevelSpec(){{
                setColumn("date");
            }});
        }};
        DataStreamMappingSpec dsmSpec2 = new DataStreamMappingSpec(){{
            setLevel5(new DataStreamLevelSpec(){{
                setColumn("value");
            }});
        }};
        DataStreamMappingSpecMap dataStreamMappingSpecMap = new DataStreamMappingSpecMap(){{
            put(DATASTREAM_NAME_1, dsmSpec1);
            put(DATASTREAM_NAME_2, dsmSpec2);
        }};
        this.sampleTable.getTableSpec().setDataStreams(dataStreamMappingSpecMap);
    }

    @Test
    void getDataStreams_whenSampleTableRequested_thenReturnsListOfDataStreams() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Flux<DataStreamBasicModel>> responseEntity = this.sut.getDataStreams(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<DataStreamBasicModel> result = responseEntity.getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(
                result.get(0).getDataStreamName(),
                this.sampleTable.getTableSpec().getDataStreams().getFirstDataStreamMappingName());
    }


    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void getDataStream_whenDataStreamRequested_thenReturnDataStreamConfiguration(String dataStreamName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<DataStreamModel>> responseEntity = this.sut.getDataStream(
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getTarget().getSchemaName(),
                sampleTableSpec.getTarget().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        DataStreamModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getDataStreamName(), dataStreamName);
        Assertions.assertEquals(result.getSpec(), sampleTableSpec.getDataStreams().get(dataStreamName));
    }

    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void updateDataStream_whenDataStreamUpdateWithRenameRequested_thenUpdateDataStreamAndRename(String dataStreamName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        String newName = "new_stream";
        DataStreamMappingSpec newSpec = new DataStreamMappingSpec() {{
            setLevel1(new DataStreamLevelSpec() {{
                setColumn("date");
            }});
        }};

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateDataStream(
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getTarget().getSchemaName(),
                sampleTableSpec.getTarget().getTableName(),
                dataStreamName,
                new DataStreamTrimmedModel() {{
                    setDataStreamName(newName);
                    setSpec(newSpec);
                }});
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertNull(sampleTableSpec.getDataStreams().get(dataStreamName));

        DataStreamMappingSpec resultNewSpec = sampleTableSpec.getDataStreams().get(newName);
        Assertions.assertNotNull(resultNewSpec);
        Assertions.assertEquals(newSpec, resultNewSpec);

        Assertions.assertEquals(2, sampleTableSpec.getDataStreams().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", DATASTREAM_NAME_1})
    void updateDataStream_whenDataStreamUpdateWithSubstitutableNameRequested_thenUpdateDataStream(String substitutableName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        DataStreamMappingSpec newSpec = new DataStreamMappingSpec() {{
            setLevel1(new DataStreamLevelSpec() {{
                setColumn("date");
            }});
        }};

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateDataStream(
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getTarget().getSchemaName(),
                sampleTableSpec.getTarget().getTableName(),
                DATASTREAM_NAME_1,
                new DataStreamTrimmedModel() {{
                    setDataStreamName(substitutableName);
                    setSpec(newSpec);
                }});
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);

        DataStreamMappingSpec resultSpec = sampleTableSpec.getDataStreams().get(DATASTREAM_NAME_1);
        Assertions.assertNotNull(resultSpec);
        Assertions.assertEquals(newSpec, resultSpec);

        Assertions.assertEquals(2, sampleTableSpec.getDataStreams().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void updateDataStream_whenDataStreamUpdateWithNullNameRequested_thenUpdateDataStream(String dataStreamName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        DataStreamMappingSpec newSpec = new DataStreamMappingSpec() {{
            setLevel1(new DataStreamLevelSpec() {{
                setColumn("date");
            }});
        }};

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateDataStream(
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getTarget().getSchemaName(),
                sampleTableSpec.getTarget().getTableName(),
                dataStreamName,
                new DataStreamTrimmedModel() {{
                    setDataStreamName(null);
                    setSpec(newSpec);
                }});
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);

        DataStreamMappingSpec resultSpec = sampleTableSpec.getDataStreams().get(dataStreamName);
        Assertions.assertNotNull(resultSpec);
        Assertions.assertEquals(newSpec, resultSpec);

        Assertions.assertEquals(2, sampleTableSpec.getDataStreams().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void setDefaultDataStream_whenDataStreamRequested_thenSetDataStreamAsDefault(String dataStreamName) {
        // Setting the already default data stream as default should have no effect.
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();
        DataStreamMappingSpec specBeforeAction = sampleTableSpec.getDataStreams().get(dataStreamName);

        ResponseEntity<Mono<?>> responseEntity = this.sut.setDefaultDataStream(
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getTarget().getSchemaName(),
                sampleTableSpec.getTarget().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);

        Assertions.assertEquals(dataStreamName, sampleTableSpec.getDataStreams().getFirstDataStreamMappingName());
        Assertions.assertEquals(specBeforeAction, sampleTableSpec.getDataStreams().getFirstDataStreamMapping());

        Assertions.assertEquals(2, sampleTableSpec.getDataStreams().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void deleteDataStream_whenDataStreamRequested_thenDeleteDataStream(String dataStreamName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<?>> responseEntity = this.sut.deleteDataStream(
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getTarget().getSchemaName(),
                sampleTableSpec.getTarget().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);

        Assertions.assertNull(sampleTableSpec.getDataStreams().get(dataStreamName));
        Assertions.assertEquals(1, sampleTableSpec.getDataStreams().size());
        Assertions.assertNotNull(sampleTableSpec.getDataStreams().getFirstDataStreamMapping());
    }

    @Test
    void deleteDataStream_whenNonexistentDataStreamRequested_thenSuccessWithNoChanges() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();
        String dataStreamName = "I'm_not_there";

        ResponseEntity<Mono<?>> responseEntity = this.sut.deleteDataStream(
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getTarget().getSchemaName(),
                sampleTableSpec.getTarget().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);

        Assertions.assertNull(sampleTableSpec.getDataStreams().get(dataStreamName));
        Assertions.assertEquals(2, sampleTableSpec.getDataStreams().size());
    }
}