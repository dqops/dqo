package com.dqops.connectors.spark;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class SparkContainer<SELF extends SparkContainer<SELF>> extends JdbcDatabaseContainer<SELF> {

    public static final Integer SPARK_SQL_THRIFTSERVER_PORT = 10000;
    private static final String dockerImageName = "docker.io/dqops/spark:latest";

    public SparkContainer(){
        super(dockerImageName);
        super.waitingFor(Wait.forLogMessage(".*HiveThriftServer2 started.*\\s", 1));
        this.withExposedPorts(SPARK_SQL_THRIFTSERVER_PORT);
    }

    public String getTestQueryString() {
        return "SELECT 1";
    }

    @Override
    public String getDriverClassName() {
        return "org.apache.hive.jdbc.HiveDriver";
    }

    public String getJdbcUrl() {
        return "jdbc:hive2://" + this.getHost() + ":" + this.getMappedPort(SPARK_SQL_THRIFTSERVER_PORT);// + "/" + this.getSchemaName();  //";<sessionConfs>?<hiveConfs>#<hiveVars>";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public String getPassword() {
        return "";
    }

    public static String getDefaultSchemaName(){
        return "default";
    }

}
