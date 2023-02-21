package ai.dqo.core.filesystem.synchronization;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data synchronization direction between a local DQO Home and DQO Cloud data quality data warehouse.
 */
public enum FileSynchronizationDirection {
    /**
     * Full synchronization that both uploads local changes to the DQO Cloud and downloads changes from DQO Cloud.
     */
    @JsonProperty("full")
    full,

    /**
     * Only download new changes from DQO Cloud.
     */
    @JsonProperty("download")
    download,

    /**
     * Only upload new local changes to DQO Cloud.
     */
    @JsonProperty("upload")
    upload
}
