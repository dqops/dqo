/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.controllers;

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderType;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.rest.models.metadata.DataGroupingConfigurationListModel;
import com.dqops.rest.models.metadata.DataGroupingConfigurationModel;
import com.dqops.rest.models.metadata.DataGroupingConfigurationTrimmedModel;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.services.locking.RestApiLockServiceImpl;
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
public class DataGroupingConfigurationsControllerUTTests extends BaseTest {
    private DataGroupingConfigurationsController sut;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext userHomeContext;
    private SampleTableMetadata sampleTable;
    private UserDomainIdentity userDomainIdentity;
    private final static String DATASTREAM_NAME_1 = "date_level3";
    private final static String DATASTREAM_NAME_2 = "value_level5";

    @BeforeEach
    void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.sut = new DataGroupingConfigurationsController(this.userHomeContextFactory, new RestApiLockServiceImpl());
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity, false);
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.continuous_days_one_row_per_day,
                ProviderType.bigquery);

        DataGroupingConfigurationSpec dsmSpec1 = new DataGroupingConfigurationSpec(){{
            setLevel3(new DataGroupingDimensionSpec(){{
                setColumn("date");
            }});
        }};
        DataGroupingConfigurationSpec dsmSpec2 = new DataGroupingConfigurationSpec(){{
            setLevel5(new DataGroupingDimensionSpec(){{
                setColumn("value");
            }});
        }};
        DataGroupingConfigurationSpecMap dataGroupingConfigurationSpecMap = new DataGroupingConfigurationSpecMap(){{
            put(DATASTREAM_NAME_1, dsmSpec1);
            put(DATASTREAM_NAME_2, dsmSpec2);
        }};
        this.sampleTable.getTableSpec().setDefaultGroupingName(DATASTREAM_NAME_1);
        this.sampleTable.getTableSpec().setGroupings(dataGroupingConfigurationSpecMap);
    }

    @Test
    void getDataStreams_whenSampleTableRequested_thenReturnsListOfDataStreams() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        Mono<ResponseEntity<Flux<DataGroupingConfigurationListModel>>> responseEntity = this.sut.getTableGroupingConfigurations(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.block().getStatusCode());

        List<DataGroupingConfigurationListModel> result = responseEntity.block().getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(
                result.get(0).getDataGroupingConfigurationName(),
                this.sampleTable.getTableSpec().getDefaultDataGroupingConfiguration().getDataGroupingConfigurationName());
    }


    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void getDataStream_whenDataStreamRequested_thenReturnDataStreamConfiguration(String dataStreamName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        Mono<ResponseEntity<Mono<DataGroupingConfigurationModel>>> responseEntity = this.sut.getTableGroupingConfiguration(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.block().getStatusCode());

        DataGroupingConfigurationModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getDataGroupingConfigurationName(), dataStreamName);
        Assertions.assertEquals(result.getSpec(), sampleTableSpec.getGroupings().get(dataStreamName));
    }

    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void updateDataStream_whenDataStreamUpdateWithRenameRequested_thenUpdateDataStreamAndRename(String dataStreamName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        String newName = "new_stream";
        DataGroupingConfigurationSpec newSpec = new DataGroupingConfigurationSpec() {{
            setLevel1(new DataGroupingDimensionSpec() {{
                setColumn("date");
            }});
        }};

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTableGroupingConfiguration(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                dataStreamName,
                new DataGroupingConfigurationTrimmedModel() {{
                    setDataGroupingConfigurationName(newName);
                    setSpec(newSpec);
                }});
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
        Assertions.assertNull(sampleTableSpec.getGroupings().get(dataStreamName));

        DataGroupingConfigurationSpec resultNewSpec = sampleTableSpec.getGroupings().get(newName);
        Assertions.assertNotNull(resultNewSpec);
        Assertions.assertEquals(newSpec, resultNewSpec);

        Assertions.assertEquals(2, sampleTableSpec.getGroupings().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", DATASTREAM_NAME_1})
    void updateDataStream_whenDataStreamUpdateWithSubstitutableNameRequested_thenUpdateDataStream(String substitutableName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        DataGroupingConfigurationSpec newSpec = new DataGroupingConfigurationSpec() {{
            setLevel1(new DataGroupingDimensionSpec() {{
                setColumn("date");
            }});
        }};

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTableGroupingConfiguration(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                DATASTREAM_NAME_1,
                new DataGroupingConfigurationTrimmedModel() {{
                    setDataGroupingConfigurationName(substitutableName);
                    setSpec(newSpec);
                }});
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);

        DataGroupingConfigurationSpec resultSpec = sampleTableSpec.getGroupings().get(DATASTREAM_NAME_1);
        Assertions.assertNotNull(resultSpec);
        Assertions.assertEquals(newSpec, resultSpec);

        Assertions.assertEquals(2, sampleTableSpec.getGroupings().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void updateDataStream_whenDataStreamUpdateWithNullNameRequested_thenUpdateDataStream(String dataStreamName) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        DataGroupingConfigurationSpec newSpec = new DataGroupingConfigurationSpec() {{
            setLevel1(new DataGroupingDimensionSpec() {{
                setColumn("date");
            }});
        }};

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTableGroupingConfiguration(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                dataStreamName,
                new DataGroupingConfigurationTrimmedModel() {{
                    setDataGroupingConfigurationName(null);
                    setSpec(newSpec);
                }});
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);

        DataGroupingConfigurationSpec resultSpec = sampleTableSpec.getGroupings().get(dataStreamName);
        Assertions.assertNotNull(resultSpec);
        Assertions.assertEquals(newSpec, resultSpec);

        Assertions.assertEquals(2, sampleTableSpec.getGroupings().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {DATASTREAM_NAME_1, DATASTREAM_NAME_2})
    void setDefaultDataStream_whenDataStreamRequested_thenSetDataStreamAsDefault(String dataStreamName) {
        // Setting the already default data stream as default should have no effect.
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();
        DataGroupingConfigurationSpec specBeforeAction = sampleTableSpec.getGroupings().get(dataStreamName);

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.setTableDefaultGroupingConfiguration(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);

        Assertions.assertEquals(dataStreamName, sampleTableSpec.getDefaultGroupingName());
//        Assertions.assertEquals(specBeforeAction, sampleTableSpec.getGroupings().getFirstDataGroupingConfiguration());

        Assertions.assertEquals(2, sampleTableSpec.getGroupings().size());
    }

    @Test
    void deleteTableGroupingConfiguration_whenDeletingDefaultDataGrouping_thenDeleteDataGroupingAndDisablesDefault() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();
        String dataStreamName = DATASTREAM_NAME_1;

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.deleteTableGroupingConfiguration(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);

        Assertions.assertNull(sampleTableSpec.getGroupings().get(dataStreamName));
        Assertions.assertEquals(1, sampleTableSpec.getGroupings().size());
        Assertions.assertNull(sampleTableSpec.getDefaultDataGroupingConfiguration());
    }

    @Test
    void deleteTableGroupingConfiguration_whenDeletingNonDefaultDataGrouping_thenDeleteDataGroupingAndPreservesDefault() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();
        String dataStreamName = DATASTREAM_NAME_2;

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.deleteTableGroupingConfiguration(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);

        Assertions.assertNull(sampleTableSpec.getGroupings().get(dataStreamName));
        Assertions.assertEquals(1, sampleTableSpec.getGroupings().size());
        Assertions.assertNotNull(sampleTableSpec.getDefaultDataGroupingConfiguration());
    }

    @Test
    void deleteDataStream_whenNonexistentDataStreamRequested_thenSuccessWithNoChanges() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();
        String dataStreamName = "I'm_not_there";

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.deleteTableGroupingConfiguration(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                dataStreamName);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);

        Assertions.assertNull(sampleTableSpec.getGroupings().get(dataStreamName));
        Assertions.assertEquals(2, sampleTableSpec.getGroupings().size());
    }
}