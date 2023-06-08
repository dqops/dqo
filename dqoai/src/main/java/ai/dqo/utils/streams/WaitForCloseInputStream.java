/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

/**
 * Delegating input stream that has a wait method to wait until a file was closed (a background thread has fully read the file).
 */
public class WaitForCloseInputStream extends InputStream {
    private InputStream sourceStream;
    private CompletableFuture<InputStream> finishedFuture = new CompletableFuture<>();

    public WaitForCloseInputStream(InputStream sourceStream) {
        this.sourceStream = sourceStream;
    }

    /**
     * Awaitable future that is completed when the file is closed. The client can wait for this future.
     * @return Completable future, finished when the stream was closed.
     */
    public CompletableFuture<InputStream> getFinishedFuture() {
        return finishedFuture;
    }

    @Override
    public void close() throws IOException {
        sourceStream.close();
        this.finishedFuture.complete(this);
    }

    @Override
    public int read() throws IOException {
        try {
            int readResult = sourceStream.read();
            if (readResult < 0) {
                this.close();
            }
            return readResult;
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

    @Override
    public int read(byte[] b) throws IOException {
        try {
            int readCount = sourceStream.read(b);
            if (readCount == 0) {
                this.close();
            }
            return readCount;
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        try {
            int readCount = sourceStream.read(b, off, len);
            if (readCount == 0) {
                this.close();
            }
            return readCount;
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

    @Override
    public byte[] readAllBytes() throws IOException {
        try {
            byte[] bytes = sourceStream.readAllBytes();
            if (bytes == null || bytes.length == 0) {
                this.close();
            }
            return bytes;
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        try {
            byte[] bytes = sourceStream.readNBytes(len);
            if (bytes == null || bytes.length == 0) {
                this.close();
            }
            return bytes;
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        try {
            int readCount = sourceStream.readNBytes(b, off, len);
            if (readCount == 0) {
                this.close();
            }
            return readCount;
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

    @Override
    public long skip(long n) throws IOException {
        try {
            return sourceStream.skip(n);
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

//    @Override
//    public void skipNBytes(long n) throws IOException {
//        try {
//            sourceStream.skipNBytes(n);
//        }
//        catch (IOException ex) {
//            this.finishedFuture.completeExceptionally(ex);
//            throw ex;
//        }
//    }

    @Override
    public int available() throws IOException {
        try {
            return sourceStream.available();
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

    @Override
    public void mark(int readlimit) {
        sourceStream.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        try {
            sourceStream.reset();
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }

    @Override
    public boolean markSupported() {
        return sourceStream.markSupported();
    }

    @Override
    public long transferTo(OutputStream out) throws IOException {
        try {
            long trasferredCount = sourceStream.transferTo(out);
            this.close();
            return trasferredCount;
        }
        catch (IOException ex) {
            this.finishedFuture.completeExceptionally(ex);
            throw ex;
        }
    }
}
