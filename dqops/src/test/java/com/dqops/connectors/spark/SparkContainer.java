package com.dqops.connectors.spark;

import org.junit.Rule;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SparkContainer<SELF extends SparkContainer<SELF>> extends JdbcDatabaseContainer<SELF> {

    public static final String NAME = "spark";
//    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("dqops/" + NAME);

    public static final Integer SPARK_SQL_THRIFTSERVER_PORT = 10000;

    private String databaseName;

    public GenericContainer container;

    public SparkContainer(Path dockerfilePath){
        super(new DockerImageName(NAME));

        this.waitStrategy = (new LogMessageWaitStrategy()).withRegEx(".*HiveThriftServer2: HiveThriftServer2 started.*\\s").withTimes(2).withStartupTimeout(Duration.of(20L, ChronoUnit.SECONDS));
        this.withExposedPorts(SPARK_SQL_THRIFTSERVER_PORT);
        this.databaseName = "default";
//
//        this.container = new GenericContainer(
//                new ImageFromDockerfile("spark", false)
////                        .withDockerfile(dockerfilePath, false)
//        )
//                .withExposedPorts(SPARK_SQL_THRIFTSERVER_PORT)
//                ;


//        this.addExposedPort(SPARK_SQL_THRIFTSERVER_PORT);
    }
    public String getTestQueryString() {
        return "SELECT 1";
    }

    @Override
    public String getDriverClassName() {
        return "org.apache.hive.jdbc.HiveDriver";
    }

    public String getJdbcUrl() {
        return "jdbc:hive2://" + this.getHost() + ":" + this.getMappedPort(SPARK_SQL_THRIFTSERVER_PORT);// + "/" + this.getDatabaseName();  //";<sessionConfs>?<hiveConfs>#<hiveVars>";
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public String getPassword() {
        return "";
    }

//    @Override
//    protected String getTestQueryString() {
//        return null;
//    }

//    public Integer getSparkPort() {
//        return this.getMappedPort(10000);
//    }
//
//    public GenericContainer getContainer(){
//        return container;
//    }

}
