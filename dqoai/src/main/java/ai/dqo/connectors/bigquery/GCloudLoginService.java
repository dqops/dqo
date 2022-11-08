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
package ai.dqo.connectors.bigquery;

/**
 * Runs 'gcloud auth application-default login' to log in the user to GCP.
 */
public interface GCloudLoginService {
    /**
     * Starts gcloud auth application-default login to log in the user to GCP and generate a local credentials.
     * This operations halts the execution and waits until the "gcloud auth application-default login" finishes.
     *
     * @return True when it was a success, false otherwise.
     */
    boolean authenticateUserForApplicationDefaultCredentials();
}
