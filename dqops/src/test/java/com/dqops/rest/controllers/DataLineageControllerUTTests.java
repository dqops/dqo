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

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderType;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.data.checkresults.statuscache.TableStatusCacheStub;
import com.dqops.metadata.lineage.ColumnLineageSourceSpec;
import com.dqops.metadata.lineage.TableLineageSource;
import com.dqops.metadata.lineage.TableLineageSourceSpec;
import com.dqops.metadata.lineage.TableLineageSourceSpecList;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.rest.models.metadata.TableLineageTableListModel;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.services.locking.RestApiLockServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class DataLineageControllerUTTests extends BaseTest {
    private DataLineageController sut;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext userHomeContext;
    private SampleTableMetadata sampleTable;
    private UserDomainIdentity userDomainIdentity;
    private final static TableLineageSource TABLE_LINEAGE_SOURCE_1 = new TableLineageSource(
            "source_connection_name", "source_schema_name", "source_table_name");
    private final static TableLineageSource TABLE_LINEAGE_SOURCE_2 = new TableLineageSource(
            "another_source_connection_name", "another_source_schema_name", "another_source_table_name");

    private final static String DATA_LINEAGE_SOURCE_TOOL_1 = "lineage_tool_name_1";
    private final static String DATA_LINEAGE_SOURCE_TOOL_2 = "lineage_tool_name_2";

    @BeforeEach
    void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.sut = new DataLineageController(this.userHomeContextFactory, new RestApiLockServiceImpl(), new TableStatusCacheStub(), null, null);
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity, false);
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.continuous_days_one_row_per_day,
                ProviderType.bigquery);

        TableLineageSourceSpec sourceTable1 = new TableLineageSourceSpec(){{
            setSourceConnection(TABLE_LINEAGE_SOURCE_1.getConnection());
            setSourceSchema(TABLE_LINEAGE_SOURCE_1.getSchema());
            setSourceTable(TABLE_LINEAGE_SOURCE_1.getTable());
            setDataLineageSourceTool(DATA_LINEAGE_SOURCE_TOOL_1);
            setProperties(Map.of("table_id_in_lineage_tool", "1"));
        }};
        ColumnLineageSourceSpec columnLineageSourceSpec = new ColumnLineageSourceSpec();
        columnLineageSourceSpec.getSourceColumns().add("root_name_col");
        columnLineageSourceSpec.setProperties(Map.of("prop_key", "prop_value"));
        sourceTable1.getColumns().put("name_col", columnLineageSourceSpec);

        TableLineageSourceSpec sourceTable2 = new TableLineageSourceSpec(){{
            setSourceConnection(TABLE_LINEAGE_SOURCE_2.getConnection());
            setSourceSchema(TABLE_LINEAGE_SOURCE_2.getSchema());
            setSourceTable(TABLE_LINEAGE_SOURCE_2.getTable());
            setDataLineageSourceTool(DATA_LINEAGE_SOURCE_TOOL_2);
            setProperties(Map.of("table_id_in_lineage_tool", "2"));
        }};

        TableLineageSourceSpecList tableLineageSourceSpecList = new TableLineageSourceSpecList(){{
            add(sourceTable1);
            add(sourceTable2);
        }};
        this.sampleTable.getTableSpec().setSourceTables(tableLineageSourceSpecList);
    }

    @Test
    void getTableSourceTables_whenSampleTableRequested_thenReturnsListOfSourceTables() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        Mono<ResponseEntity<Flux<TableLineageTableListModel>>> responseEntity = this.sut.getTableSourceTables(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                Optional.empty());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.block().getStatusCode());

        List<TableLineageTableListModel> result = responseEntity.block().getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(
                result.get(0).getSourceTable(),
                TABLE_LINEAGE_SOURCE_1.getTable());
    }

    @Test
    void getTableSourceTables_whenSourceTableNotExist_thenTableCurrentDataQualityStatusTablePartiallySetAndExistIsFalse() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        Mono<ResponseEntity<Flux<TableLineageTableListModel>>> responseEntity = this.sut.getTableSourceTables(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                Optional.empty());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.block().getStatusCode());

        List<TableLineageTableListModel> result = responseEntity.block().getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TABLE_LINEAGE_SOURCE_1.getConnection(), result.get(0).getTableDataQualityStatus().getConnectionName());
        Assertions.assertEquals(TABLE_LINEAGE_SOURCE_1.getSchema(), result.get(0).getTableDataQualityStatus().getSchemaName());
        Assertions.assertEquals(TABLE_LINEAGE_SOURCE_1.getTable(), result.get(0).getTableDataQualityStatus().getTableName());
        Assertions.assertFalse(result.get(0).getTableDataQualityStatus().isTableExist());

    }

    @Test
    void updateTableSourceTable_whenUpdateAllExceptForTableLineageSourceKey_thenUpdate() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        TableLineageSourceSpec newSourceTable = new TableLineageSourceSpec(){{
            setSourceConnection(TABLE_LINEAGE_SOURCE_1.getConnection());
            setSourceSchema(TABLE_LINEAGE_SOURCE_1.getSchema());
            setSourceTable(TABLE_LINEAGE_SOURCE_1.getTable());
            setDataLineageSourceTool("updated_tool_name");
            setProperties(Map.of("updated_table_id_in_lineage_tool", "99"));
        }};

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                TABLE_LINEAGE_SOURCE_1.getConnection(),
                TABLE_LINEAGE_SOURCE_1.getSchema(),
                TABLE_LINEAGE_SOURCE_1.getTable(),
                newSourceTable);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());
        Assertions.assertNull(responseEntity.block().getBody().block());

        Assertions.assertEquals(2, this.sampleTable.getTableSpec().getSourceTables().size());

        Assertions.assertEquals(TABLE_LINEAGE_SOURCE_1.getConnection(), sampleTable.getTableSpec().getSourceTables().get(0).getSourceConnection());
        Assertions.assertEquals("updated_tool_name", sampleTable.getTableSpec().getSourceTables().get(0).getDataLineageSourceTool());
        Assertions.assertEquals("99", sampleTable.getTableSpec().getSourceTables().get(0).getProperties().get("updated_table_id_in_lineage_tool"));
    }

    @Test
    void updateTableSourceTable_whenModelGotDifferentTableLineageSourceKey_thenStatusNotAccepted() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        TableLineageSource tableLineageSource = new TableLineageSource(){{
            setConnection("a_new_connection_name");
            setSchema("a_new_schema_name");
            setTable("a_new_table_name");
        }};

        TableLineageSourceSpec newSourceTable = new TableLineageSourceSpec(){{
            setSourceConnection(tableLineageSource.getConnection());
            setSourceSchema(tableLineageSource.getSchema());
            setSourceTable(tableLineageSource.getTable());
            setDataLineageSourceTool("a_new_lineage_tool_name");
            setProperties(Map.of("table_id_in_lineage_tool", "99"));
        }};

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                TABLE_LINEAGE_SOURCE_1.getConnection(),
                TABLE_LINEAGE_SOURCE_1.getSchema(),
                TABLE_LINEAGE_SOURCE_1.getTable(),
                newSourceTable);
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.block().getStatusCode());
        Assertions.assertNull(responseEntity.block().getBody().block());
    }

    @Test
    void updateTableSourceTable_whenUpdateWithMissingTableNameRequested_thenUpdate() {

        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        TableLineageSource tableLineageSource = new TableLineageSource(){{
            setConnection("a_new_connection_name");
            setSchema("a_new_schema_name");
        }};

        TableLineageSourceSpec newSourceTable = new TableLineageSourceSpec(){{
            setSourceConnection(tableLineageSource.getConnection());
            setSourceSchema(tableLineageSource.getSchema());
            setDataLineageSourceTool("a_new_lineage_tool_name");
            setProperties(Map.of("table_id_in_lineage_tool", "99"));
        }};

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                TABLE_LINEAGE_SOURCE_1.getConnection(),
                TABLE_LINEAGE_SOURCE_1.getSchema(),
                TABLE_LINEAGE_SOURCE_1.getTable(),
                newSourceTable);
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.block().getStatusCode());
        Assertions.assertNull(responseEntity.block().getBody().block());
    }

    @Test
    void createTableSourceTable_aNewInstance_thenCreates() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        String newSourceConnection = "new_source_connection";
        String newSourceSchema = "new_source_schema";
        String newSourceTable = "new_source_table";

        TableLineageSourceSpec newTableLineageSourceListModel = new TableLineageSourceSpec(){{
            setSourceConnection(newSourceConnection);
            setSourceSchema(newSourceSchema);
            setSourceTable(newSourceTable);
            setDataLineageSourceTool("new_tool");
        }};

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.createTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                newSourceConnection,
                newSourceSchema,
                newSourceTable,
                newTableLineageSourceListModel);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.block().getStatusCode());

        Assertions.assertEquals(3, this.sampleTable.getTableSpec().getSourceTables().size());

        Assertions.assertEquals(newSourceConnection, sampleTable.getTableSpec().getSourceTables().get(2).getSourceConnection());
        Assertions.assertEquals("new_tool", sampleTable.getTableSpec().getSourceTables().get(2).getDataLineageSourceTool());
    }

    @Test
    void createTableSourceTable_withExistingKey_thenStatusConflict() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        TableLineageSourceSpec newSourceTable = new TableLineageSourceSpec(){{
            setSourceConnection(TABLE_LINEAGE_SOURCE_1.getConnection());
            setSourceSchema(TABLE_LINEAGE_SOURCE_1.getSchema());
            setSourceTable(TABLE_LINEAGE_SOURCE_1.getTable());
            setDataLineageSourceTool("updated_tool_name");
            setProperties(Map.of("updated_table_id_in_lineage_tool", "99"));
        }};

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.createTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                TABLE_LINEAGE_SOURCE_1.getConnection(),
                TABLE_LINEAGE_SOURCE_1.getSchema(),
                TABLE_LINEAGE_SOURCE_1.getTable(),
                newSourceTable);
        Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.block().getStatusCode());
        Assertions.assertEquals(2, this.sampleTable.getTableSpec().getSourceTables().size());
    }

    @Test
    void createTableSourceTable_whenAddedTwice_thenSecondTimeReturnsConflict() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        String newSourceConnection = "new_source_connection";
        String newSourceSchema = "new_source_schema";
        String newSourceTable = "new_source_table";

        TableLineageSourceSpec newTableLineageSourceListModel = new TableLineageSourceSpec(){{
            setSourceConnection(newSourceConnection);
            setSourceSchema(newSourceSchema);
            setSourceTable(newSourceTable);
            setDataLineageSourceTool("new_tool");
        }};

        Assertions.assertEquals(2, this.sampleTable.getTableSpec().getSourceTables().size());

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.createTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                newSourceConnection,
                newSourceSchema,
                newSourceTable,
                newTableLineageSourceListModel);

        Mono<ResponseEntity<Mono<Void>>> responseEntity2 = this.sut.createTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                newSourceConnection,
                newSourceSchema,
                newSourceTable,
                newTableLineageSourceListModel);

        Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity2.block().getStatusCode());
        Assertions.assertEquals(3, this.sampleTable.getTableSpec().getSourceTables().size());
    }

    @Test
    void deleteTableSourceTable_whenObjectExist_thenDeletesIt() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.deleteTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                TABLE_LINEAGE_SOURCE_1.getConnection(),
                TABLE_LINEAGE_SOURCE_1.getSchema(),
                TABLE_LINEAGE_SOURCE_1.getTable());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.block().getStatusCode());
        Assertions.assertEquals(1, this.sampleTable.getTableSpec().getSourceTables().size());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
    }

    @Test
    void deleteTableSourceTable_whenObjectNotExist_thenNotFound() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec sampleTableSpec = this.sampleTable.getTableSpec();

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.deleteTableSourceTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                sampleTableSpec.getPhysicalTableName().getSchemaName(),
                sampleTableSpec.getPhysicalTableName().getTableName(),
                TABLE_LINEAGE_SOURCE_1.getConnection(),
                TABLE_LINEAGE_SOURCE_1.getSchema(),
                "not_existing_table");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.block().getStatusCode());
        Assertions.assertEquals(2, this.sampleTable.getTableSpec().getSourceTables().size());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
    }
}