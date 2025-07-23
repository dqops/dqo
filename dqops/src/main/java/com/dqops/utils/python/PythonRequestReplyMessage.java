/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.python;

import java.util.concurrent.CompletableFuture;

/**
 * Request-reply message that is sent to a Python process. The input is an object that is serialized to JSON.
 * The output is the class type of the expected result. The result is parsed back from JSON to the target response type.
 * The object also contains a completable future to wait for the response.
 */
public class PythonRequestReplyMessage<I, O> {
    private I request;
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
     * Sets the request
     * @param request Request.
     */
    public void setRequest(I request) {
        this.request = request;
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
