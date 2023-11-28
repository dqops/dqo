package com.dqops.connectors.spark;

import org.junit.Rule;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Paths;

public class SparkContainer extends GenericContainer<SparkContainer> {

    public static final String NAME = "spark";
//    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("dqops/" + NAME);
    public GenericContainer container;

    public SparkContainer(String dockerfilePath){
        container = new GenericContainer(
                new ImageFromDockerfile()
                        .withDockerfile(Paths.get(dockerfilePath)));
    }

//    @Override
//    public String getDriverClassName() {
//        return "com.simba.spark.jdbc41.Driver";
//    }
//
//    @Override
//    public String getJdbcUrl() {
//        return "jdbc:hive2://" + this.getHost() + ":" + this.getSparkPort() + "/" + this.getDatabaseName();  //";<sessionConfs>?<hiveConfs>#<hiveVars>";
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
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
