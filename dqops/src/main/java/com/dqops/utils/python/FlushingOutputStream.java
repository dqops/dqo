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

import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream that is flushing all writes instantly in the write method.
 */
public class FlushingOutputStream extends OutputStream {
    private final OutputStream nestedStream;

    public FlushingOutputStream(OutputStream nestedStream) {
        this.nestedStream = nestedStream;
    }

    /**
     * Writes {@code b.length} bytes from the specified byte array
     * to this output stream. The general contract for {@code write(b)}
     * is that it should have exactly the same effect as the call
     * {@code write(b, 0, b.length)}.
     *
     * @param b the data.
     * @throws IOException if an I/O error occurs.
     * @see OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte[] b) throws IOException {
		this.nestedStream.write(b);
		this.nestedStream.flush();
    }

    /**
     * Writes {@code len} bytes from the specified byte array
     * starting at offset {@code off} to this output stream.
     * The general contract for {@code write(b, off, len)} is that
     * some of the bytes in the array {@code b} are written to the
     * output stream in order; element {@code b[off]} is the first
     * byte written and {@code b[off+len-1]} is the last byte written
     * by this operation.
     * <p>
     * The {@code write} method of {@code OutputStream} calls
     * the write method of one argument on each of the bytes to be
     * written out. Subclasses are encouraged to override this method and
     * provide a more efficient implementation.
     * <p>
     * If {@code b} is {@code null}, a
     * {@code NullPointerException} is thrown.
     * <p>
     * If {@code off} is negative, or {@code len} is negative, or
     * {@code off+len} is greater than the length of the array
     * {@code b}, then an {@code IndexOutOfBoundsException} is thrown.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an {@code IOException} is thrown if the output
     *                     stream is closed.
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
		this.nestedStream.write(b, off, len);
		this.nestedStream.flush();
    }

    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out. The general contract of {@code flush} is
     * that calling it is an indication that, if any bytes previously
     * written have been buffered by the implementation of the output
     * stream, such bytes should immediately be written to their
     * intended destination.
     * <p>
     * If the intended destination of this stream is an abstraction provided by
     * the underlying operating system, for example a file, then flushing the
     * stream guarantees only that bytes previously written to the stream are
     * passed to the operating system for writing; it does not guarantee that
     * they are actually written to a physical device such as a disk drive.
     * <p>
     * The {@code flush} method of {@code OutputStream} does nothing.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void flush() throws IOException {
		this.nestedStream.flush();
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with this stream. The general contract of {@code close}
     * is that it closes the output stream. A closed stream cannot perform
     * output operations and cannot be reopened.
     * <p>
     * The {@code close} method of {@code OutputStream} does nothing.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
		this.nestedStream.close();
    }

    /**
     * Writes the specified byte to this output stream. The general
     * contract for {@code write} is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument {@code b}. The 24
     * high-order bits of {@code b} are ignored.
     * <p>
     * Subclasses of {@code OutputStream} must provide an
     * implementation for this method.
     *
     * @param b the {@code byte}.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an {@code IOException} may be thrown if the
     *                     output stream has been closed.
     */
    @Override
    public void write(int b) throws IOException {
		this.nestedStream.write(b);
		this.nestedStream.flush();
    }
}
