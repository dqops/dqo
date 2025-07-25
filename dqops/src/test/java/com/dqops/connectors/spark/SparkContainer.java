/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

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
        return "jdbc:hive2://" + this.getHost() + ":" + this.getMappedPort(SPARK_SQL_THRIFTSERVER_PORT); // + "/" + this.getSchemaName();  //";<sessionConfs>?<hiveConfs>#<hiveVars>";
    }

    /**
     * The default username
     */
    @Override
    public String getUsername() {
        return "";
    }

    /**
     * The default password
     */
    @Override
    public String getPassword() {
        return "";
    }

    /**
     * The default schema
     */
    public static String getDefaultSchemaName(){
        return "default";
    }

}
