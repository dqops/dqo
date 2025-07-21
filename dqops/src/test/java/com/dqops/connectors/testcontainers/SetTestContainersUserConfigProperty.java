/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.testcontainers;

import org.testcontainers.utility.TestcontainersConfiguration;

/**
 * Command line operation that modifies the ~/.testcontainers.properties file and changes values of properties.
 * It is used to enable or disable the reusable containers.
 */
public class SetTestContainersUserConfigProperty {
    /**
     * Main method of an operation that changes a parameter value in the ~/.testcontainers.properties file.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid command line parameters");
            System.out.println("Accepted syntax:");

            String myOS = System.getProperty("os.name").toLowerCase();
            if (myOS.contains("windows")) {
                System.out.println("set_testcontainers_property.cmd <property_name> <property_value>");
            }
            else {
                System.out.println("./set_testcontainers_property.sh <property_name> <property_value>");
            }

            System.exit(-1);
        }

        TestcontainersConfiguration configuration = TestcontainersConfiguration.getInstance();
        // set a value
        configuration.updateUserConfig(args[0], args[1]);
    }
}
