/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.core.configuration;

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
