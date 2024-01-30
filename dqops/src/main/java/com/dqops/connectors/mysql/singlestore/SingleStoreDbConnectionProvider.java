package com.dqops.connectors.mysql.singlestore;

import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.connectors.mysql.MysqlParametersSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import org.apache.parquet.Strings;

public class SingleStoreDbConnectionProvider {

    /**
     * Delegates the connection configuration to the provider.
     *
     * @param connectionSpec Connection specification to fill.
     * @param isHeadless     When true and some required parameters are missing then throws an exception {@link CliRequiredParameterMissingException},
     *                       otherwise prompts the user to fill the answer.
     * @param terminalReader Terminal reader that may be used to prompt the user.
     */
    public static void promptForConnectionParameters(ConnectionSpec connectionSpec, boolean isHeadless, TerminalReader terminalReader) {
        MysqlParametersSpec mysqlParametersSpec = connectionSpec.getMysql();
        SingleStoreDbParametersSpec singleStoreDbParametersSpec;
        if(mysqlParametersSpec.getSingleStoreDbParametersSpec() == null){
            singleStoreDbParametersSpec = new SingleStoreDbParametersSpec();
            mysqlParametersSpec.setSingleStoreDbParametersSpec(singleStoreDbParametersSpec);
        } else {
            singleStoreDbParametersSpec = mysqlParametersSpec.getSingleStoreDbParametersSpec();
        }

        if (singleStoreDbParametersSpec.getLoadBalancingMode() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--single-store-load-balancing-mode");
            }
            singleStoreDbParametersSpec.setLoadBalancingMode(terminalReader.promptEnum("Single Store DB balancing mode (--single-store-load-balancing-mode)", SingleStoreDbLoadBalancingMode.class, null, false));
        }

        if (singleStoreDbParametersSpec.getHostDescriptions().isEmpty()) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--single-store-host-descriptions");
            }
            singleStoreDbParametersSpec.setHostDescriptions(terminalReader.prompt("Single Store DB host descriptions (--single-store-host-descriptions)", "${SINGLE_STORE_HOST_DESCRIPTIONS}", false));
        }

        if (Strings.isNullOrEmpty(singleStoreDbParametersSpec.getSchema())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--single-store-schema");
            }
            singleStoreDbParametersSpec.setSchema(terminalReader.prompt("Single Store DB schema name (--single-store-schema)", "${SINGLE_STORE_SCHEMA}", false));
        }

        singleStoreDbParametersSpec.setUseSsl(true);
    }
}
