package com.dqops.core.incidents;

import com.dqops.BaseTest;
import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.core.dqocloud.login.InstanceCloudLoginServiceObjectMother;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.incidents.factory.IncidentsTableFactoryObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IncidentNotificationMessageTextCreatorTest extends BaseTest {

    private InstanceCloudLoginService instanceCloudLoginService;

    @BeforeEach
    void setUp() {
        this.instanceCloudLoginService = InstanceCloudLoginServiceObjectMother.getDefault();
    }

    @Test
    void prepareText_rowDetailsXXXXXXXXXX_generatesMessage() {
        Table table = IncidentsTableFactoryObjectMother.createEmptyNormalizedTable("test_table_name");
        Row row = table.appendRow();

        row.setString(IncidentsColumnNames.ID_COLUMN_NAME, "1");
        row.setString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, "schema_here");
        row.setString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME, "table_name_here");
        row.setInt(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME, 2);
        row.setLong(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME, 3);
        row.setInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME, Instant.ofEpochSecond(569433790839639L));
        row.setInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME, Instant.ofEpochSecond(569433790877935L));
        row.setInstant(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME, Instant.ofEpochSecond(569435567107895L));
        row.setString(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, "Reasonableness");
        row.setString(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME, "column_name");
        row.setString(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME, "");
        row.setString(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME, "");
        row.setString(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME, "");
        row.setInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME, 3);
        row.setInt(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME, 10);
        row.setString(IncidentsColumnNames.STATUS_COLUMN_NAME, "acknowledged");

        IncidentNotificationMessageParameters messageParameters = IncidentNotificationMessageParameters
                .builder()
                .incidentRow(row)
                .connectionName("connection_name")
                .baseUrlOfDqoInstance(instanceCloudLoginService.getReturnBaseUrl())
                .build();

        IncidentNotificationMessage newIncidentNotificationMessage =
                IncidentNotificationMessage.fromIncidentRow(messageParameters);

        assertNotNull(newIncidentNotificationMessage);

        assertEquals("""
                               *New data quality issue detected*\s
                               > *Connection name*: connection_name
                               > *Full table name*: <http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail|schema_here.table_name_here>
                               > *Table priority*: 2
                               > *Failed checks count*: 10
                               > *First seen*: +24208-06-30T21:38:55Z
                               > *Last seen*: null
                               > *Quality dimension*: Reasonableness
                               > *Check category*: column_name
                               > *Highest severity*: 3
                               \s
                               >>> <http://localhost:8888/incidents/connection_name/24208/6/1| *View issue in DQOps*>\s
                        """,
                newIncidentNotificationMessage.getText()
        );

    }

}
