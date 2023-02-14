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
package ai.dqo.core.remotestorage.gcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Async (flux) GCP storage bucket client that uses HTTP(2) to download and upload files in parallel.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GcpBucketClientImpl {
    private GcpHttpClientProvider gcpHttpClientProvider;

    @Autowired
    public GcpBucketClientImpl(GcpHttpClientProvider gcpHttpClientProvider) {
        this.gcpHttpClientProvider = gcpHttpClientProvider;
    }


}
