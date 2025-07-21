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
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.rest.models.metadata.ColumnListModel;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "spring.main.web-application-type=reactive")
public class ColumnsControllerITTests extends BaseTest {
    private UserHomeContext userHomeContext;
    private SampleTableMetadata sampleTable;
    private WebClient webClient;

    @BeforeEach
    void setUp() {
        this.userHomeContext = UserHomeContextObjectMother.createDefaultHomeContext(true);
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
        this.webClient = WebClientObjectMother.create();
    }

    @Test
    void getColumns_whenRequestedForSampleTable_thenReturnsColumns() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        this.userHomeContext.flush();
        String uri = String.format("/api/connections/%s/schemas/%s/tables/%s/columns?dataQualityStatus=false", this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        WebClient.ResponseSpec response = webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        List<ColumnListModel> result = response.bodyToFlux(ColumnListModel.class)
                .collectList()
                .block();

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.get(0).getConnectionName());
    }

    @Test
    void getColumnBasic_whenRequestedForSampleTable_thenReturnsColumns() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        this.userHomeContext.flush();

        ColumnSpec firstColumnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();
        String columnName = firstColumnSpec.getColumnName();
        String uri = String.format("/api/connections/%s/schemas/%s/tables/%s/columns/%s/basic", this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnName);

        WebClient.ResponseSpec response = webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        ColumnListModel result = response.bodyToMono(ColumnListModel.class)
                .block();

        response.onStatus(status -> true, r -> {
            Assertions.assertEquals(200, r.statusCode().value());
            return Mono.empty();
        });
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(columnName, result.getColumnName());
    }
}
