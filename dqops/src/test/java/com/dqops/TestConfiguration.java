/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Configuration for the unit tests.
 */
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class, CacheAutoConfiguration.class })
@EnableConfigurationProperties
@EnableWebFlux
@ComponentScan("com.dqops")
@ActiveProfiles("test")
@PropertySource("classpath:application-test.yml")
public class TestConfiguration {
}
