/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for dqo.ai. Properties are mapped to the "dqo.storage.gcp." prefix
 * that are responsible the configuration of a Google Storage file system.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.storage.gcp")
@EqualsAndHashCode(callSuper = false)
public class DqoStorageGcpConfigurationProperties implements Cloneable {
    private int uploadBufferSize = 15000000;

    /**
     * Upload buffer size. This is the block size (batch) used to upload files to GCP.
     * @return Buffer size.
     */
    public int getUploadBufferSize() {
        return uploadBufferSize;
    }

    /**
     * Sets the buffer size. Files that are smaller than the buffer are uploaded in one call.
     * @param uploadBufferSize Upload buffer size.
     */
    public void setUploadBufferSize(int uploadBufferSize) {
        this.uploadBufferSize = uploadBufferSize;
    }

    /**
     * Clones the current object.
     * @return Cloned instance.
     */
    @Override
    public DqoStorageGcpConfigurationProperties clone() {
        try {
            return (DqoStorageGcpConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
