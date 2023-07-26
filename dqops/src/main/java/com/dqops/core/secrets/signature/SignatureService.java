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
