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
 * Configuration POJO with the configuration for DQOps storage on DQOps Cloud (SaaS) provided GCP storage buckets. Properties are mapped to the "dqo.storage.gcp." prefix
 * that are responsible the configuration of a Google Storage file system.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.storage.gcp")
@EqualsAndHashCode(callSuper = false)
public class DqoStorageGcpConfigurationProperties implements Cloneable {
    private int uploadBufferSize = 15000000;
    private boolean http2;
    private Integer http2MaxConcurrentStreams = 5000;
    private int parallelDeleteOperations = 1000;

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
     * Returns true if HTTP/2 should be used to download and upload files to a google bucket.
     * @return True when http/2 is used for file operations on the google bucket.
     */
    public boolean isHttp2() {
        return http2;
    }

    /**
     * Sets the flag to enable (true) or disable (false) asynchronous GCP storage bucket operations using HTTP/2.
     * @param http2 True when http/2 should be used in asynchronous mode, false when http/1.1 is used.
     */
    public void setHttp2(boolean http2) {
        this.http2 = http2;
    }

    /**
     * Returns the maximum number of concurrent HTTP/2 streams used to concurrently upload or download files to the data quality data warehouse stored in the DQOps Cloud.
     * @return The number of concurrent http/2 streams.
     */
    public Integer getHttp2MaxConcurrentStreams() {
        return http2MaxConcurrentStreams;
    }

    /**
     * Sets the number of concurrent streams used to upload and download files to the GCP storage bucket used for storing the data quality data warehouse.
     * @param http2MaxConcurrentStreams The number of concurrent http/2 streams.
     */
    public void setHttp2MaxConcurrentStreams(Integer http2MaxConcurrentStreams) {
        this.http2MaxConcurrentStreams = http2MaxConcurrentStreams;
    }

    /**
     * Returns a number of parallel file delete operations that are executed when a whole folder is deleted.
     * @return Number of parallel file delete operations in fight.
     */
    public int getParallelDeleteOperations() {
        return parallelDeleteOperations;
    }

    /**
     * Sets the number of parallel file delete operations in fight.
     * @param parallelDeleteOperations Parallel delete calls.
     */
    public void setParallelDeleteOperations(int parallelDeleteOperations) {
        this.parallelDeleteOperations = parallelDeleteOperations;
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
