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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * Support methods to detect if testcontainer reuse is enabled or disabled.
 */
public class TestContainersObjectMother {
    /**
     * Check if the testcontainers.reuse.enable parameter for the testcontainers configuation is enabled.
     * @return True - run integration tests on reusable test containers, false - create new test containers for integration tests and stop them after running the tests.
     */
    public static boolean shouldUseReusableTestContainers() {
        TestcontainersConfiguration configuration = TestcontainersConfiguration.getInstance();
        String reuseValue = configuration.getEnvVarOrProperty("testcontainers.reuse.enable", "false");
        return "true".equals(reuseValue);
    }

    /**
     * Checks if the given TCP port is listening (a test container is listening).
     * @param port Port number.
     * @return True when the port is in use (which means that a container with a database is working), false when the port is free - so the database container did not start.
     */
    public static boolean isTcpPortListening(int port) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.setReuseAddress(false);
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port), 1);
            return false;
        } catch (Exception ex) {
            return true;
        }
    }
}
