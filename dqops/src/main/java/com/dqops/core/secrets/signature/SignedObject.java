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
 * Object with a digital signature.
 * @param <T> Source object type.
 */
public class SignedObject<T> {
    private T target;
    private byte[] signedBytes;
    private String signedHex;

    public SignedObject() {
    }

    public SignedObject(T target, byte[] signedBytes, String signedHex) {
        this.target = target;
        this.signedBytes = signedBytes;
        this.signedHex = signedHex;
    }

    /**
     * Returns the original object that was signed.
     * @return Original object.
     */
    public T getTarget() {
        return target;
    }

    /**
     * Sets the original object.
     * @param target Original object.
     */
    public void setTarget(T target) {
        this.target = target;
    }

    /**
     * Returns the object (serialized) with a HMAC signature.
     * @return Signed object as bytes.
     */
    public byte[] getSignedBytes() {
        return signedBytes;
    }

    /**
     * Sets the bytes of the signed object.
     * @param signedBytes Bytes with the payload and the HMAC signature.
     */
    public void setSignedBytes(byte[] signedBytes) {
        this.signedBytes = signedBytes;
    }

    /**
     * Returns the signed payload (serialized target object) and the HMAC signature, all converted to HEX.
     * @return HEX representation of the signed message.
     */
    public String getSignedHex() {
        return signedHex;
    }

    /**
     * Sets the HEX of the signed data.
     * @param signedHex HEX representation of the signed payload+signature bytes.
     */
    public void setSignedHex(String signedHex) {
        this.signedHex = signedHex;
    }
}
