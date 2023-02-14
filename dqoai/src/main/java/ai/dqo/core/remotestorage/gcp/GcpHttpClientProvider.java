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

import reactor.netty.http.client.HttpClient;

/**
 * Provides a shared HTTP client instance used to download and upload files to a Google storage bucket.
 */
public interface GcpHttpClientProvider {
    /**
     * Returns a shared HTTP client used to download and upload files from/to a GCP storage bucket.
     *
     * @return Http client.
     */
    HttpClient getHttpClient();
}
