/*
 * This code is intended for the internal use by the DQOps team for the development purpose only.
 * Using SAP HANA Express requires acceptance of the SAP license.
 */

package com.dqops.connectors.hana;

import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.SelinuxContext;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.shaded.org.apache.commons.lang3.SystemUtils;
import org.testcontainers.utility.MountableFile;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.time.temporal.ChronoUnit.SECONDS;

public class HanaContainer<SELF extends HanaContainer<SELF>> extends JdbcDatabaseContainer<SELF> {
    public static final String IMAGE = "saplabs/hanaexpress";
    public static final String DB_DRIVER = "com.sap.db.jdbc.Driver";
    public static final Integer SYSTEM_PORT = 39017;
    public static final Integer TENANT_PORT = 39041;

    private static final String systemDBName = "SYSTEMDB";
    private static final String tenantDBName = "HXE";
    public static final String USERNAME = "SYSTEM";
    public static final String PASSWORD = "HXEHana1";

    public static final long uLimit = 1_048_576;

    public HanaContainer() {
        super(IMAGE);

        String licenseAcceptedEnvVar = System.getenv("I_HAVE_ACCEPTED_SAP_LICENSE");
        Boolean licenseAccepted = Boolean.parseBoolean(licenseAcceptedEnvVar);
        if (!licenseAccepted){
            throw new RuntimeException("The SAP license has not been accepted.");
        }
        addExposedPorts(39013, 39017, 39041, 39042, 39043, 39044, 39045, 1128, 1129, 59013, 59014);

        // not sure if necessary
//        Ulimit[] ulimits = new Ulimit[] { new Ulimit("nofile", uLimit, uLimit) };
//        Map<String, String> sysctls = new HashMap<>();
//        sysctls.put("kernel.shmmax", "1073741824");
//        sysctls.put("net.ipv4.ip_local_port_range", "40000 60999");
//        sysctls.put("kernel.shmmni", "524288");
//        sysctls.put("kernel.shmall", "8388608");
//        this.withCreateContainerCmdModifier(it -> it.getHostConfig()
//                .withUlimits(ulimits)
//                .withSysctls(sysctls)
//        );

        this.withCommand("--master-password " + PASSWORD + " --agree-to-sap-license");

        this.waitStrategy = new LogMessageWaitStrategy()
                .withRegEx(".*Startup finished!*\\s")
                .withTimes(1)
                .withStartupTimeout(Duration.of(600, SECONDS));
    }

    @Override
    public void addFileSystemBind(String hostPath, String containerPath, BindMode mode, SelinuxContext selinuxContext) {
        if (SystemUtils.IS_OS_WINDOWS && hostPath.startsWith("/")) {
            this.getBinds().add(new Bind(hostPath, new Volume(containerPath), mode.accessMode, selinuxContext.selContext));
        } else {
            MountableFile mountableFile = MountableFile.forHostPath(hostPath);
            this.getBinds().add(new Bind(mountableFile.getResolvedPath(), new Volume(containerPath), mode.accessMode, selinuxContext.selContext));
        }

    }

    @Override
    protected void configure() {
        /*
         * Enforce that the license is accepted - do not remove.
         * License available at: https://www.sap.com/docs/download/cmp/2016/06/sap-hana-express-dev-agmt-and-exhibit.pdf
         */

        Boolean licenseAccepted = Boolean.getBoolean("I_HAVE_ACCEPTED_SAP_LICENSE");
        if (licenseAccepted == null){
            return;
        }

        if (!getEnvMap().containsKey("AGREE_TO_SAP_LICENSE")) {
            acceptLicense();
        }
    }

    /**
     * Accepts the license for the SAP HANA Express container by setting the ACCEPT_EULA=Y
     * Calling this method will automatically accept the license at: https://www.sap.com/docs/download/cmp/2016/06/sap-hana-express-dev-agmt-and-exhibit.pdf
     *
     * @return The container itself with an environment variable accepting the SAP HANA Express license
     */
    public SELF acceptLicense() {
        addEnv("AGREE_TO_SAP_LICENSE", "Y");
        return self();
    }

    @NotNull
    @Override
    protected Set<Integer> getLivenessCheckPorts() {
        return new HashSet<>(Arrays.asList(new Integer[] {getMappedPort(TENANT_PORT), getMappedPort(SYSTEM_PORT)}));
    }

    @Override
    protected void waitUntilContainerStarted() {
        getWaitStrategy().waitUntilReady(this);
    }

    @Override
    public String getDriverClassName() {
        return DB_DRIVER;
    }

    /**
     * Default database name getter. Because of HANAs two databases it will return the tenant database. If you want to get the name of the system database, use 'getSystemDatabaseName'.
     * @return The name of the tenant database.
     */
    @Override
    public String getDatabaseName() {
        return getTenantDatabaseName();
    }

    /**
     * Get the name of the HANA system database.
     * @return The name of the HANA system database.
     */
    public String getSystemDatabaseName() {
        return systemDBName;
    }

    /**
     * Get the name of the HANA tenant database.
     * @return The name of the HANA tenant database.
     */
    public String getTenantDatabaseName() {
        return tenantDBName;
    }

    @Override
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getTestQueryString() {
        return "SELECT 1 FROM sys.dummy";
    }

    /**
     * Get the port for the system database and general connections.
     * @return port of the system database.
     */
    @NotNull
    public Integer getSystemPort() {
        return getMappedPort(SYSTEM_PORT);
    }

    /**
     * Get the port for the tenant database.
     * @return port of the tenant database.
     */
    @NotNull
    public Integer getTenantPort() {
        return getMappedPort(TENANT_PORT);
    }

    /**
     * Get the Jdbc url used to connect to the databases.
     * @return jdbc url
     */
    @Override
    public String getJdbcUrl() {
        return "jdbc:sap://" + getContainerIpAddress() + ":" + getSystemPort() + "/";
    }

    /**
     * Modification of the default connection string because of HANA specific database selection.
     * Query will connect to the tenant database per default. If you want to connect to the system database, supply '?databaseName=SYSTEMDB' as queryString
     *
     * @param queryString your custom query attached to the connection string.
     * @return Connection object.
     */
    @Override
    public Connection createConnection(String queryString) throws SQLException, NoDriverFoundException {
        if(queryString == null || queryString.trim().isEmpty()) {
            queryString = "?databaseName=" + tenantDBName;
        } else {
            // Remove ? from queryString if supplied.
            if(queryString.charAt(0) == '?') {
                queryString = queryString.substring(1);
            }
            queryString = "?databaseName=" + tenantDBName + "&" + queryString;
        }
        return super.createConnection(queryString);
    }

}