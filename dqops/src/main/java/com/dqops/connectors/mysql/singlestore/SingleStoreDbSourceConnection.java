package com.dqops.connectors.mysql.singlestore;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.mysql.MysqlParametersSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Single Store DB source connection.
 */
public class SingleStoreDbSourceConnection {

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
        SingleStoreDbParametersSpec singleStoreDbParametersSpec = mysqlParametersSpec.getSingleStoreDbParametersSpec();

        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:singlestore:");

        SingleStoreDbLoadBalancingMode loadBalancingMode = singleStoreDbParametersSpec.getLoadBalancingMode() == null
                ? SingleStoreDbLoadBalancingMode.none : singleStoreDbParametersSpec.getLoadBalancingMode();
        switch (loadBalancingMode){
            case loadbalance:
                jdbcConnectionBuilder.append("loadbalance:");
                break;
            case sequential:
                jdbcConnectionBuilder.append("sequential:");
                break;
            case none: break;
            default:
                throw new RuntimeException("Given enum is not supported : " + loadBalancingMode);
        }
        jdbcConnectionBuilder.append("//");

        String hostDescriptionsString = secretValueProvider
                .expandList(singleStoreDbParametersSpec.getHostDescriptions(), secretValueLookupContext)
                .stream()
                .collect(Collectors.joining(","));

        if (!Strings.isNullOrEmpty(hostDescriptionsString)) {
            jdbcConnectionBuilder.append(hostDescriptionsString);
        } else {
            throw new ConnectorOperationFailedException("Cannot create a connection to MySQL, the host descriptions are not provided.");
        }

        jdbcConnectionBuilder.append("/");

        String schema = secretValueProvider.expandValue(singleStoreDbParametersSpec.getSchema(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(schema)) {
            jdbcConnectionBuilder.append(schema);
        } else {
            throw new ConnectorOperationFailedException("Cannot create a connection to MySQL, the database/schema name is not provided");
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

        Boolean useSsl = singleStoreDbParametersSpec.isUseSsl();
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

    /**
     * Creates an SQL for listing columns in the given tables.
     * @param schemaName Schema name (bigquery dataset name).
     * @param tableNames Table names to list.
     * @return SQL of the INFORMATION_SCHEMA query.
     */
    public static String buildListColumnsSql(ConnectionSpec connectionSpec, String schemaName, List<String> tableNames, String informationSchemaName) {

        ConnectionProviderSpecificParameters providerSpecificConfiguration = connectionSpec.getProviderSpecificConfiguration();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT ");
        sqlBuilder.append("TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,IS_SPARSE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,CHARACTER_SET_NAME,COLLATION_NAME,CAST(COLUMN_TYPE as CHAR(8192)),COLUMN_KEY,EXTRA,PRIVILEGES,COLUMN_COMMENT,DATETIME_PRECISION");
        sqlBuilder.append(" FROM ");

        String databaseName = providerSpecificConfiguration.getDatabase();
        sqlBuilder.append(informationSchemaName);
        sqlBuilder.append(".COLUMNS ");
        sqlBuilder.append("WHERE TABLE_SCHEMA='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");

        if (!Strings.isNullOrEmpty(databaseName)) {
            sqlBuilder.append(" AND TABLE_CATALOG='");
            sqlBuilder.append(databaseName.replace("'", "''"));
            sqlBuilder.append("'");
        }

        if (tableNames != null && tableNames.size() > 0) {
            sqlBuilder.append(" AND TABLE_NAME IN (");
            for (int ti = 0; ti < tableNames.size(); ti++) {
                String tableName = tableNames.get(ti);
                if (ti > 0) {
                    sqlBuilder.append(",");
                }
                sqlBuilder.append('\'');
                sqlBuilder.append(tableName.replace("'", "''"));
                sqlBuilder.append('\'');
            }
            sqlBuilder.append(") ");
        }
        sqlBuilder.append("ORDER BY TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, ORDINAL_POSITION");
        String sql = sqlBuilder.toString();
        return sql;
    }

}
