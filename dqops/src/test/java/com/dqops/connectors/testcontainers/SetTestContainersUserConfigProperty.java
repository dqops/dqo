/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
