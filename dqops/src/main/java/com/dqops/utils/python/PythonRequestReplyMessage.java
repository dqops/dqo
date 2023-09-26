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
package com.dqops.utils.python;

import java.util.concurrent.CompletableFuture;

/**
 * Request-reply message that is sent to a Python process. The input is an object that is serialized to JSON.
 * The output is the class type of the expected result. The result is parsed back from JSON to the target response type.
 * The object also contains a completable future to wait for the response.
 */
public class PythonRequestReplyMessage<I, O> {
    private final I request;
    private final Class<O> responseType;
    private final CompletableFuture<O> responseFuture;

    /**
     * Creates a request object to hold the input object to send to a python processor and a response type to parse the result.
     * @param request Request object.
     * @param responseType Response type.
     */
    public PythonRequestReplyMessage(I request, Class<O> responseType) {
        this.request = request;
        this.responseType = responseType;
        if (request != null && responseType != null) {
            this.responseFuture = new CompletableFuture<>();
        }
        else {
            this.responseFuture = null;
        }
    }

    /**
     * Creates an empty object that identifies an end of stream.
     * @return End of stream message.
     */
    public static PythonRequestReplyMessage createEmpty() {
        return new PythonRequestReplyMessage(null, null);
    }

    /**
     * Returns the request object, an object that will be serialized to JSON.
     * @return Request object to send to Python.
     */
    public I getRequest() {
        return request;
    }

    /**
     * Response class type used to create the object of the requested type and fill it with the result received from parsing the result.
     * @return Response type.
     */
    public Class<O> getResponseType() {
        return responseType;
    }

    /**
     * Completable future used to wait for the response or receive an error.
     * @return Completable future.
     */
    public CompletableFuture<O> getResponseFuture() {
        return responseFuture;
    }

    /**
     * Checks if the object is empty, which means it is a closing message before the process is closed.
     * @return Is empty.
     */
    public boolean isEmpty() {
        return this.request == null && this.responseType == null && this.responseFuture == null;
    }
}
