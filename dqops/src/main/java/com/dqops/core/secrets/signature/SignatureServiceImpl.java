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

import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.serialization.JsonSerializer;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Service that creates and validates signed messages: API keys, api key requests.
 */
@Component
public class SignatureServiceImpl implements SignatureService {
    private static final String HASH_ALGORITHM = "HmacSHA256";
    private static final int HASH_BIT_LENGTH = 256;

    private InstanceSignatureKeyProvider instanceSignatureKeyProvider;
    private JsonSerializer jsonSerializer;

    /**
     * Default injection constructor.
     * @param instanceSignatureKeyProvider Signature key provider.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public SignatureServiceImpl(
            InstanceSignatureKeyProvider instanceSignatureKeyProvider,
            JsonSerializer jsonSerializer) {
        this.instanceSignatureKeyProvider = instanceSignatureKeyProvider;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Signs the given byte with a key.
     * @param bytes Bytes to sign.
     * @param keyBytes Key to use.
     * @return Signature.
     */
    private byte[] signWithKey(byte[] bytes, byte[] keyBytes) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, HASH_ALGORITHM);
            Mac mac = Mac.getInstance(HASH_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] signature = mac.doFinal(bytes);
            return signature;
        }
        catch (Exception ex) {
            throw new DqoRuntimeException(ex);
        }
    }

    /**
     * Signs the given payload object with an HMAC signature using a custom key.
     * @param payload Payload object that will be serialized to JSON and signed.
     * @param keyBytes Key bytes.
     * @param <T> Payload type.
     * @return Signed object as a byte array and a hex string.
     */
    public <T> SignedObject<T> createSigned(T payload, byte[] keyBytes) {
        String jsonString = this.jsonSerializer.serialize(payload);
        byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);
        byte[] signature = signWithKey(jsonBytes, keyBytes);

        byte[] fullMessageBytes = new byte[jsonBytes.length + signature.length];
        System.arraycopy(jsonBytes, 0, fullMessageBytes, 0, jsonBytes.length);
        System.arraycopy(signature, 0, fullMessageBytes, jsonBytes.length, signature.length);
        String fullMessageHex = Hex.encodeHexString(fullMessageBytes);

        SignedObject<T> signedObject = new SignedObject<>()
        {{
            setTarget(payload);
            setSignedBytes(fullMessageBytes);
            setSignedHex(fullMessageHex);
        }};

        return signedObject;
    }

    /**
     * Signs the given payload object with an HMAC signature.
     * @param payload Payload object that will be serialized to JSON and signed.
     * @param <T> Payload type.
     * @return Signed object as a byte array and a hex string.
     */
    public <T> SignedObject<T> createSigned(T payload) {
        byte[] keyBytes = this.instanceSignatureKeyProvider.getInstanceSignatureKey();
        return createSigned(payload, keyBytes);
    }

    /**
     * Decodes and validates a signed message.
     * @param payloadType Payload class type.
     * @param messageHex Signed message (payload + signature) as a hex string.
     * @param <T> Payload type.
     * @return Deserialized payload.
     * @throws SignatureNotMatchException when the signature is invalid.
     */
    public <T> SignedObject<T> decodeSignedMessageHex(Class<T> payloadType, String messageHex) {
        try {
            byte[] messageBytes = Hex.decodeHex(messageHex);
            byte[] payloadBytes = new byte[messageBytes.length - HASH_BIT_LENGTH / 8];
            byte[] signatureBytes = new byte[HASH_BIT_LENGTH / 8];

            System.arraycopy(messageBytes, 0, payloadBytes, 0, payloadBytes.length);
            System.arraycopy(messageBytes, payloadBytes.length, signatureBytes, 0, signatureBytes.length);

            byte[] instanceSignatureKey = this.instanceSignatureKeyProvider.getInstanceSignatureKey();
            byte[] digitalSignatureBytes = signWithKey(payloadBytes, instanceSignatureKey);
            if (Arrays.compare(digitalSignatureBytes, signatureBytes) != 0) {
                throw new SignatureNotMatchException("Signature for the object does not match.");
            }

            String payloadJsonString = new String(payloadBytes, StandardCharsets.UTF_8);
            T deserializedPayload = this.jsonSerializer.deserialize(payloadJsonString, payloadType);

            SignedObject<T> signedObject = new SignedObject<>(deserializedPayload, messageBytes, messageHex);
            return signedObject;
        }
        catch (SignatureNotMatchException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new DqoRuntimeException(ex);
        }
    }
}

