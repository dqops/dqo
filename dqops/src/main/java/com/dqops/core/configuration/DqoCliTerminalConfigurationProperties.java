/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Cli configuration describing properties of the terminal.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.cli.terminal")
@EqualsAndHashCode(callSuper = false)
public class DqoCliTerminalConfigurationProperties implements Cloneable {
    private Integer width;

    /**
     * Get the width of a terminal of applications in one-shot running mode.
     * @return The maximum number of characters in one row for terminals.
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Set the width of a terminal of applications in one-shot running mode.
     * @param width New maximum number of characters in one row for terminals.
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * Clones the current object.
     * @return Cloned instance.
     */
    @Override
    public DqoCliTerminalConfigurationProperties clone() {
        try {
            return (DqoCliTerminalConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
