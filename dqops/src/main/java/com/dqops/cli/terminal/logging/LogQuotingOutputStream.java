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

package com.dqops.cli.terminal.logging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream wrapper over a standard output that is used to encode characters for docker logs.
 * Docker log engine captures all lines from the standard output and wraps them in a json message, but some characters such as a double quote " must be quoted.
 */
public class LogQuotingOutputStream extends OutputStream {
    /**
     * Double quote " character that must be replaced to a single quote.
     */
    public static final byte DOUBLE_QUOTE_CHARACTER = (byte) '"';

    /**
     * Singe quote ' character.
     */
    public static final byte SINGLE_QUOTE_CHARACTER = (byte) '\'';
    private final OutputStream wrappedStream;

    /**
     * Creates an output stream wrapper that encodes characters that cannot be properly logged.
     * @param wrappedStream Wrapped output stream.
     */
    public LogQuotingOutputStream(OutputStream wrappedStream) {
        this.wrappedStream = wrappedStream;
    }

    /**
     * Writes {@code b.length} bytes from the specified byte array
     * to this output stream.
     *
     * @param b the data.
     * @throws IOException if an I/O error occurs.
     * @see OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(@NotNull byte[] b) throws IOException {
        for (int i = 0; i < b.length; i++) {
            if (b[i] == DOUBLE_QUOTE_CHARACTER) {
                b[i] = SINGLE_QUOTE_CHARACTER;
            }
        }
        this.wrappedStream.write(b);
    }

    /**
     * Writes {@code len} bytes from the specified byte array
     * starting at offset {@code off} to this output stream.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an {@code IOException} is thrown if the output
     *                     stream is closed.
     */
    @Override
    public void write(@NotNull byte[] b, int off, int len) throws IOException {
        for (int i = off; i < off + len && i < b.length; i++) {
            if (b[i] == DOUBLE_QUOTE_CHARACTER) {
                b[i] = SINGLE_QUOTE_CHARACTER;
            }
        }
        this.wrappedStream.write(b, off, len);
    }

    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void flush() throws IOException {
        this.wrappedStream.flush();
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with this stream.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        this.wrappedStream.close();
    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param b the {@code byte}.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an {@code IOException} may be thrown if the
     *                     output stream has been closed.
     */
    @Override
    public void write(int b) throws IOException {
        if ((byte)b == DOUBLE_QUOTE_CHARACTER) {
            this.wrappedStream.write((int)SINGLE_QUOTE_CHARACTER);
        } else {
            this.wrappedStream.write(b);
        }
    }
}
