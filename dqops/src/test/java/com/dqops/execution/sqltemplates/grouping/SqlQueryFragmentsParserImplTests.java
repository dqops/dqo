package com.dqops.execution.sqltemplates.grouping;

import com.dqops.BaseTest;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class SqlQueryFragmentsParserImplTests extends BaseTest {
    private SqlQueryFragmentsParserImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new SqlQueryFragmentsParserImpl();
    }

    @Test
    void parseQueryToComponents_whenNoActualValueColumn_thenReturnsOneStaticSqlFragment() {
        FragmentedSqlQuery fragments = this.sut.parseQueryToComponents("""
                SELECT
                    count(*) as something
                FROM
                    schema.table""", SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);

        Assertions.assertNotNull(fragments);
        Assertions.assertEquals(1, fragments.getComponents().size());
        ArrayList<SqlQueryFragment> fragmentsList = new ArrayList<>(fragments.getComponents());
        Assertions.assertEquals(SqlQueryFragmentType.STATIC_FRAGMENT, fragmentsList.get(0).getFragmentType());
        Assertions.assertEquals("""
                SELECT
                    count(*) as something
                FROM
                    schema.table""", fragmentsList.get(0).getText());
    }

    @Test
    void parseQueryToComponents_whenActualValueColumnFound_thenReturnsThreeFragments() {
        FragmentedSqlQuery fragments = this.sut.parseQueryToComponents("""
                SELECT
                    count(*) as actual_value
                FROM
                    schema.table""", SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);

        Assertions.assertNotNull(fragments);
        Assertions.assertEquals(3, fragments.getComponents().size());
        ArrayList<SqlQueryFragment> fragmentsList = new ArrayList<>(fragments.getComponents());
        Assertions.assertEquals(SqlQueryFragmentType.STATIC_FRAGMENT, fragmentsList.get(0).getFragmentType());
        Assertions.assertEquals("""
                SELECT
                """, fragmentsList.get(0).getText());

        Assertions.assertEquals(SqlQueryFragmentType.ACTUAL_VALUE, fragmentsList.get(1).getFragmentType());
        Assertions.assertEquals("""
                    count(*) as actual_value
                """, fragmentsList.get(1).getText());

        Assertions.assertEquals(SqlQueryFragmentType.STATIC_FRAGMENT, fragmentsList.get(2).getFragmentType());
        Assertions.assertEquals("""
                FROM
                    schema.table""", fragmentsList.get(2).getText());
    }
}
