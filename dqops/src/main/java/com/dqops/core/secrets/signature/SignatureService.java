/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.secrets.signature;

/**
 * Service that creates and validates signed messages: API keys, etc.
 */
public interface SignatureService {
    /**
     * Signs the given payload object with an HMAC signature.
     * @param payload Payload object that will be serialized to JSON and signed.
     * @param <T> Payload type.
     * @return Signed object as a byte array and a hex string.
     */
    <T> SignedObject<T> createSigned(T payload);

    /**
     * Signs the given payload object with an HMAC signature using a custom key.
     * @param payload Payload object that will be serialized to JSON and signed.
     * @param keyBytes Key bytes.
     * @param <T> Payload type.
     * @return Signed object as a byte array and a hex string.
     */
    <T> SignedObject<T> createSigned(T payload, byte[] keyBytes);

    /**
     * Decodes and validates a signed message.
     * @param payloadType Payload class type.
     * @param messageHex Signed message (payload + signature) as a hex string.
     * @param <T> Payload type.
     * @return Deserialized payload.
     * @throws SignatureNotMatchException when the signature is invalid.
     */
    <T> SignedObject<T> decodeAndValidateSignedMessageHex(Class<T> payloadType, String messageHex);

    /**
     * Decodes a signed message, without validation.
     * @param payloadType Payload class type.
     * @param messageHex Signed message (payload + signature) as a hex string.
     * @param <T> Payload type.
     * @return Deserialized payload.
     */
    <T> SignedObject<T> decodeSignedMessageHexNoValidate(Class<T> payloadType, String messageHex);
}
