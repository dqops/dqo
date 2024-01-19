package com.dqops.connectors.mysql.singlestore;

import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.mysql.MysqlParametersSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;

import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Single Store source connection.
 */
public class SingleStoreSourceConnection {

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    public static HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext,
                                                  MysqlParametersSpec mysqlParametersSpec,
                                                  SecretValueProvider secretValueProvider) {

//            jdbc:singlestore:[loadbalance:|sequential:]//<hostDescription>[,<hostDescription>...]/[database][?<key1>=<value1>[&<key2>=<value2>]]
//
//            hostDescription:
//                <host>[:port] | address=(host=<host>)[(port=<port>)][(type=(master|slave))]
//

        HikariConfig hikariConfig = new HikariConfig();
        SingleStoreParametersSpec singleStoreParametersSpec = mysqlParametersSpec.getSingleStoreParametersSpec();

        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:singlestore:");

        switch (singleStoreParametersSpec.getLoadBalancingMode()){
            case loadbalance:
                jdbcConnectionBuilder.append("loadbalance:");
                break;
            case sequential:
                jdbcConnectionBuilder.append("sequential:");
                break;
            case none: break;
            default:
                new RuntimeException("Given enum is not supported : " + singleStoreParametersSpec.getLoadBalancingMode());
        }
        jdbcConnectionBuilder.append("//");

        String hostDescriptionsString = secretValueProvider
                .expandList(singleStoreParametersSpec.getHostDescriptions(), secretValueLookupContext)
                .stream()
                .collect(Collectors.joining(","));

        if (!Strings.isNullOrEmpty(hostDescriptionsString)) {
            jdbcConnectionBuilder.append(hostDescriptionsString);
        } else {
            throw new ConnectorOperationFailedException("Cannot create a connection to MySQL, the host descriptions are not provided.");
        }

        jdbcConnectionBuilder.append("/");

        String database = secretValueProvider.expandValue(mysqlParametersSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        } else {
            throw new ConnectorOperationFailedException("Cannot create a connection to MySQL, the database name is not provided");
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (mysqlParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(mysqlParametersSpec.getProperties());
        }


        String user =  secretValueProvider.expandValue(mysqlParametersSpec.getUser(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(user)) {
            dataSourceProperties.put("user", user);
        }

        String password =  secretValueProvider.expandValue(mysqlParametersSpec.getPassword(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(password)) {
            dataSourceProperties.put("password", password);
        }

//        String userName = secretValueProvider.expandValue(mysqlParametersSpec.getUser(), secretValueLookupContext);
//        hikariConfig.setUsername(userName);
//
//        String password = secretValueProvider.expandValue(mysqlParametersSpec.getPassword(), secretValueLookupContext);
//        hikariConfig.setPassword(password);

        Boolean useSsl = singleStoreParametersSpec.isUseSsl();
        if (useSsl) {
            dataSourceProperties.put("useSsl", useSsl);
        }

        String options =  secretValueProvider.expandValue(mysqlParametersSpec.getOptions(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

}
