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
