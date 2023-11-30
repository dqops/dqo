package com.dqops.connectors.presto;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.shaded.com.google.common.base.Strings;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * Custom presto container used in tests.
 */
public class PrestoTestContainer extends JdbcDatabaseContainer<PrestoTestContainer> {
    public static final String NAME = "presto";
    public static final String IMAGE = "ghcr.io/trinodb/presto";
    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse(IMAGE);
    public static final String DEFAULT_TAG = "344";
    public static final Integer PRESTO_PORT = 8080;
    private String username;
    private String catalog;

    public PrestoTestContainer() {
        this(DEFAULT_IMAGE_NAME.withTag(DEFAULT_TAG));
    }

    public PrestoTestContainer(String dockerImageName) {
        this(DockerImageName.parse(dockerImageName));
    }

    public PrestoTestContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        this.username = "test";
        this.catalog = null;
        dockerImageName.assertCompatibleWith(DEFAULT_IMAGE_NAME);
        this.addExposedPort(PRESTO_PORT);
    }

    protected @NotNull Set<Integer> getLivenessCheckPorts() {
        return super.getLivenessCheckPorts();
    }

    public String getDriverClassName() {
        return "com.facebook.presto.jdbc.PrestoDriver";
    }

    public String getJdbcUrl() {
        return String.format("jdbc:presto://%s:%s/%s", this.getHost(), this.getMappedPort(PRESTO_PORT), Strings.nullToEmpty(this.catalog));
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return "";
    }

    public String getDatabaseName() {
        return this.catalog;
    }

    public String getTestQueryString() {
        return "SELECT count(*) FROM tpch.tiny.nation";
    }

    public PrestoTestContainer withUsername(String username) {
        this.username = username;
        return this.self();
    }

    public PrestoTestContainer withPassword(String password) {
        return this.self();
    }

    public PrestoTestContainer withDatabaseName(String dbName) {
        this.catalog = dbName;
        return this.self();
    }

    public Connection createConnection() throws SQLException, JdbcDatabaseContainer.NoDriverFoundException {
        return this.createConnection("");
    }
}
