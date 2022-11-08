package ai.dqo.rest.controllers;

import ai.dqo.BaseTest;
import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import ai.dqo.rest.models.checks.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.rest.models.checks.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.rest.models.metadata.ColumnBasicModel;
import ai.dqo.rest.models.metadata.ColumnModel;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
public class ColumnsControllerUTTests extends BaseTest {
    private ColumnsController sut;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext userHomeContext;
    private SampleTableMetadata sampleTable;

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
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(reflectionService);
        UiToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new UiToSpecCheckMappingServiceImpl(reflectionService);
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.sut = new ColumnsController(this.userHomeContextFactory, specToUiCheckMappingService, uiToSpecCheckMappingService);
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
    }

    @Test
    void getColumns_whenSampleTableRequested_thenReturnsListOfColumns() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Flux<ColumnBasicModel>> responseEntity = this.sut.getColumns(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName());

        List<ColumnBasicModel> result = responseEntity.getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void getColumn_whenColumnFromSampleTableRequested_thenReturnsRequestedColumn() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<ColumnModel>> responseEntity = this.sut.getColumn(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName());

        ColumnModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(columnSpec.getColumnName(), result.getColumnName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(this.sampleTable.getTableSpec().getTarget().toPhysicalTableName(), result.getTable());
        Assertions.assertEquals(columnSpec.getHierarchyId().hashCode64(), result.getColumnHash());
        Assertions.assertSame(columnSpec, result.getSpec());
    }

    @Test
    void getColumnBasic_whenColumnFromSampleTableRequested_thenReturnsRequestedColumn() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<ColumnBasicModel>> responseEntity = this.sut.getColumnBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName());

        ColumnBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(columnSpec.getColumnName(), result.getColumnName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(this.sampleTable.getTableSpec().getTarget().toPhysicalTableName(), result.getTable());
        Assertions.assertEquals(columnSpec.getHierarchyId().hashCode64(), result.getColumnHash());
        Assertions.assertEquals(columnSpec.getTypeSnapshot(), result.getTypeSnapshot());
    }

    @Test
    void getColumnChecksUI_whenColumnFromSampleTableRequested_thenReturnsCheckUi() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UIAllChecksModel>> responseEntity = this.sut.getColumnChecksUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName());

        UIAllChecksModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getQualityDimensions().size());
    }
}
