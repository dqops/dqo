package com.dqops.connectors.mysql.singlestore;

import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.connectors.mysql.MysqlParametersSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import org.apache.parquet.Strings;

public class SingleStoreConnectionProvider {

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
        SingleStoreParametersSpec singleStoreParametersSpec = mysqlParametersSpec.getSingleStoreParametersSpec();

        if (singleStoreParametersSpec.getLoadBalancingMode() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--single-store-load-balancing-mode");
            }
            singleStoreParametersSpec.setLoadBalancingMode(terminalReader.promptEnum("MySQL host name (--mysql-host)", SingleStoreLoadBalancingMode.class, null, false));
        }

        if (singleStoreParametersSpec.getLoadBalancingMode() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--single-store-load-balancing-mode");
            }
            singleStoreParametersSpec.setLoadBalancingMode(terminalReader.promptEnum("MySQL host name (--mysql-host)", SingleStoreLoadBalancingMode.class, null, false));
        }

        if (singleStoreParametersSpec.getHostDescriptions().isEmpty()) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--single-store-host-descriptions");
            }
            singleStoreParametersSpec.setHostDescriptions(terminalReader.prompt("Single Store schema name (--single-store-host-descriptions)", "${SINGLE_STORE_HOST_DESCRIPTIONS}", false));
        }

        if (Strings.isNullOrEmpty(singleStoreParametersSpec.getSchema())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--single-store-schema");
            }
            singleStoreParametersSpec.setSchema(terminalReader.prompt("Single Store schema name (--single-store-schema)", "${SINGLE_STORE_SCHEMA}", false));
        }

        singleStoreParametersSpec.setUseSsl(true);
    }
}
