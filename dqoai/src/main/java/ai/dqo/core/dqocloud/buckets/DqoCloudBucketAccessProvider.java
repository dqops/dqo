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
package ai.dqo.core.dqocloud.buckets;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import com.google.cloud.storage.Storage;

/**
 * DQO Cloud bucket credentials provider. Creates a Google storage client to access the bucket with the tenant's data.
 */
public interface DqoCloudBucketAccessProvider {
    /**
     * Creates a configured Google storage client to access a tenant' folder in a DQO Cloud bucket.
     * @param rootType Bucket type (sensor_readouts, rule_results, etc.)
     * @return Configured bucket access with a {@link Storage} client to access the data with downscoped credentials.
     */
    DqoCloudRemoteBucket getRemoteBucketClientRW(DqoRoot rootType);
}
