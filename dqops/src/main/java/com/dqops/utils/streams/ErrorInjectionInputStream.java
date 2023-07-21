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
package com.dqops.utils.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream wrapper that accepts an exception that could be injected. The injected exception is returned on the next call to any method.
 */
public class ErrorInjectionInputStream extends InputStream {
    private volatile Exception injectedException;
    private InputStream sourceStream;

    /**
     * Creates an error injection stream that wraps the given input stream.
     * @param sourceStream Source stream to wrap.
     */
    public ErrorInjectionInputStream(InputStream sourceStream) {
        this.sourceStream = sourceStream;
    }

    /**
     * Injects an exception that will be returned on the next call to any method.
     * @param ex Exception to be returned.
     */
    public void injectException(Exception ex) {
        this.injectedException = ex;
    }

    /**
     * Throws an exception that was forwarded to be raised.
     * @throws IOException Exception to be raised.
     */
    private void throwIfExceptionInjected() throws IOException {
        if (injectedException != null) {
            throw new IOException(injectedException);
        }
    }

    /**
     * Reads some number of bytes from the input stream and stores them into
     * the buffer array {@code b}. The number of bytes actually read is
     * returned as an integer.  This method blocks until input data is
     * available, end of file is detected, or an exception is thrown.
     */
    @Override
    public int read(byte[] b) throws IOException {
        throwIfExceptionInjected();
        return this.sourceStream.read(b);
    }

    /**
     * Reads up to {@code len} bytes of data from the input stream into
     * an array of bytes.  An attempt is made to read as many as
     * {@code len} bytes, but a smaller number may be read.
     * The number of bytes actually read is returned as an integer.
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        throwIfExceptionInjected();
        return this.sourceStream.read(b, off, len);
    }

    /**
     * Skips over and discards {@code n} bytes of data from this input
     * stream. The {@code skip} method may, for a variety of reasons, end
     * up skipping over some smaller number of bytes, possibly {@code 0}.
     * This may result from any of a number of conditions; reaching end of file
     * before {@code n} bytes have been skipped is only one possibility.
     * The actual number of bytes skipped is returned. If {@code n} is
     * negative, the {@code skip} method for class {@code InputStream} always
     * returns 0, and no bytes are skipped. Subclasses may handle the negative
     * value differently.
     */
    @Override
    public long skip(long n) throws IOException {
        throwIfExceptionInjected();
        return this.sourceStream.skip(n);
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or skipped
     * over) from this input stream without blocking, which may be 0, or 0 when
     * end of stream is detected.  The read might be on the same thread or
     * another thread.  A single read or skip of this many bytes will not block,
     * but may read or skip fewer bytes.
     */
    @Override
    public int available() throws IOException {
        throwIfExceptionInjected();
        return this.sourceStream.available();
    }

    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     *
     * <p> The {@code close} method of {@code InputStream} does
     * nothing.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        throwIfExceptionInjected();
        this.sourceStream.close();
    }

    /**
     * Marks the current position in this input stream. A subsequent call to
     * the {@code reset} method repositions this stream at the last marked
     * position so that subsequent reads re-read the same bytes.
     */
    @Override
    public synchronized void mark(int readlimit) {
        this.sourceStream.mark(readlimit);
    }

    /**
     * Repositions this stream to the position at the time the
     * {@code mark} method was last called on this input stream.
     */
    @Override
    public synchronized void reset() throws IOException {
        throwIfExceptionInjected();
        this.sourceStream.reset();
    }

    /**
     * Tests if this input stream supports the {@code mark} and
     * {@code reset} methods. Whether or not {@code mark} and
     * {@code reset} are supported is an invariant property of a
     * particular input stream instance. The {@code markSupported} method
     * of {@code InputStream} returns {@code false}.
     *
     * @return {@code true} if this stream instance supports the mark
     * and reset methods; {@code false} otherwise.
     * @see InputStream#mark(int)
     * @see InputStream#reset()
     */
    @Override
    public boolean markSupported() {
        return this.sourceStream.markSupported();
    }

    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an {@code int} in the range {@code 0} to
     * {@code 255}. If no byte is available because the end of the stream
     * has been reached, the value {@code -1} is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     *
     * <p> A subclass must provide an implementation of this method.
     *
     * @return the next byte of data, or {@code -1} if the end of the
     * stream is reached.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public int read() throws IOException {
        throwIfExceptionInjected();
        return this.sourceStream.read();
    }
}
