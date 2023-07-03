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
package com.dqops.rest.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

@Component
public class LocalUrlAddresses {
    public static final String protocol = "http://";
    public static final String serverHost = "localhost";
    public static final String swaggerUiPath = "/swagger-ui";

    private final ServerProperties serverProperties;

    @Autowired
    public LocalUrlAddresses(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public String getDqoUiUrl() {
        return protocol + serverHost + ":" + serverProperties.getPort();
    }

    public String getSwaggerUiUrl() {
        return getDqoUiUrl() + swaggerUiPath + "/";
    }
}
