/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

package com.dqops.utils.http;

/**
 * A fire-and-forget service for calling REST API in one way to send notifications (webhook calls).
 */
public interface OutboundHttpCallQueue {
    /**
     * Adds a message to a queue. Messages will be picked by a sending engine and sent to the target URL.
     * In case of errors, the call service will reply these messages.
     *
     * @param outboundHttpMessage Outbound http message.
     */
    void sendMessage(OutboundHttpMessage outboundHttpMessage);
}
