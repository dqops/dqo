/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient object mother for creating a web client that can call a local rest api.
 */
public class WebClientObjectMother {
    /**
     * Creates a local web client.
     * @return Web client.
     */
    public static WebClient create() {
        HttpClient httpClient = HttpClient
                .create()
                .wiretap(true);  // enable logging for later

        ReactorClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(httpClient);

        WebClient webClient = WebClient.builder()
                .clientConnector(reactorClientHttpConnector)
                .baseUrl("http://localhost:8888")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        return  webClient;
    }
}
