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
package com.dqops.data.storage.parquet;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.util.Progressable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * This is a copy of an old InMemoryFileSystem implementation that was depreciated in Hadoop, but serves the purpose
 * of reading and writing small parquet files in-memory.
 */


/** An implementation of the in-memory filesystem. This implementation assumes
 * that the file lengths are known ahead of time and the total lengths of all
 * the files is below a certain number (like 100 MB, configurable). Use the API
 * reserveSpaceWithCheckSum(Path f, int size) (see below for a description of
 * the API for reserving space in the FS. The uri of this filesystem starts with
 * ramfs:// .
 */
public class DqoInMemoryFileSystem extends ChecksumFileSystem {
    public static class RawInMemoryFileSystem extends FileSystem {
        private URI uri;
        private long fsSize;
        private volatile long totalUsed;
        private Path staticWorkingDir;

        //pathToFileAttribs is the final place where a file is put after it is closed
        private Map<String, FileAttributes> pathToFileAttribs =
                new LinkedHashMap<String, FileAttributes>();

        //tempFileAttribs is a temp place which is updated while reserving memory for
        //files we are going to create. It is read in the createRaw method and the
        //temp key/value is discarded. If the file makes it to "close", then it
        //ends up being in the pathToFileAttribs map.
        private Map<String, FileAttributes> tempFileAttribs =
                new LinkedHashMap<String, FileAttributes>();

        public RawInMemoryFileSystem() {
            setConf(new Configuration());
        }

        public RawInMemoryFileSystem(URI uri, Configuration conf) {
            initialize(uri, conf);
        }

        //inherit javadoc
        public void initialize(URI uri, Configuration conf) {
            setConf(conf);
            int size = Integer.parseInt(conf.get("fs.inmemory.size.mb", "100"));
            this.fsSize = size * 1024L * 1024L;
            this.uri = URI.create(uri.getScheme() + "://" + uri.getAuthority());
            String path = this.uri.getPath();
            if (path.length() == 0) {
                path = Path.CUR_DIR;
            }
            this.staticWorkingDir = new Path(path);
            LOG.info("Initialized InMemoryFileSystem: " + uri.toString() +
                    " of size (in bytes): " + fsSize);
        }

        //inherit javadoc
        public URI getUri() {
            return uri;
        }

        private class InMemoryInputStream extends FSInputStream implements ByteBufferReadable {
            private DataInputBuffer din = new DataInputBuffer();
            private FileAttributes fAttr;

            public InMemoryInputStream(Path f) throws IOException {
                synchronized (RawInMemoryFileSystem.this) {
                    fAttr = pathToFileAttribs.get(getPath(f));
                    if (fAttr == null) {
                        throw new FileNotFoundException("File " + f + " does not exist");
                    }
                    byte[] bytes = fAttr.bytes;
                    din.reset(bytes, 0, bytes.length);
                }
            }

            public long getPos() throws IOException {
                return din.getPosition();
            }

            public void seek(long pos) throws IOException {
                if ((int)pos > fAttr.bytes.length)
                    throw new IOException("Cannot seek after EOF");
                din.reset(fAttr.bytes, (int)pos, fAttr.bytes.length - (int)pos);
            }

            public boolean seekToNewSource(long targetPos) throws IOException {
                return false;
            }

            public int available() throws IOException {
                return din.available();
            }
            public boolean markSupport() { return false; }

            public int read() throws IOException {
                return din.read();
            }

            public int read(byte[] b, int off, int len) throws IOException {
                return din.read(b, off, len);
            }

            public long skip(long n) throws IOException { return din.skip(n); }

            /**
             * Reads up to buf.remaining() bytes into buf. Callers should use
             * buf.limit(..) to control the size of the desired read.
             * <p/>
             * After a successful call, buf.position() will be advanced by the number
             * of bytes read and buf.limit() should be unchanged.
             * <p/>
             * In the case of an exception, the values of buf.position() and buf.limit()
             * are undefined, and callers should be prepared to recover from this
             * eventuality.
             * <p/>
             * Many implementations will throw {@link UnsupportedOperationException}, so
             * callers that are not confident in support for this method from the
             * underlying filesystem should be prepared to handle that exception.
             * <p/>
             * Implementations should treat 0-length requests as legitimate, and must not
             * signal an error upon their receipt.
             *
             * @param buf the ByteBuffer to receive the results of the read operation.
             * @return the number of bytes read, possibly zero, or -1 if
             * reach end-of-stream
             * @throws IOException if there is some error performing the read
             */
            @Override
            public int read(ByteBuffer buf) throws IOException {
                int remainingSpace = buf.remaining();
                byte[] byteArrayBuffer = new byte[remainingSpace];
                int readBytes = this.read(byteArrayBuffer, 0, byteArrayBuffer.length);
                if (readBytes > 0) {
                    buf.put(byteArrayBuffer, 0, readBytes);
                }
                return readBytes;
            }
        }

        public FSDataInputStream open(Path f, int bufferSize) throws IOException {
            return new FSDataInputStream(new InMemoryInputStream(f));
        }

        private class InMemoryOutputStream extends OutputStream {
            private int count;
            private FileAttributes fAttr;
            private Path f;

            public InMemoryOutputStream(Path f, FileAttributes fAttr)
                    throws IOException {
                this.fAttr = fAttr;
                this.f = f;
            }

            public long getPos() throws IOException {
                return count;
            }

            public void close() throws IOException {
                synchronized (RawInMemoryFileSystem.this) {
                    fAttr.bos.trim();
                    fAttr.bytes = fAttr.bos.array;
                    fAttr.bos.close();
                    fAttr.bos = null;
                    pathToFileAttribs.put(getPath(f), fAttr);
                }
            }

            public void write(byte[] b, int off, int len) throws IOException {
                if ((off < 0) || (off > b.length) || (len < 0) ||
                        ((off + len) > b.length) || ((off + len) < 0)) {
                    throw new IndexOutOfBoundsException();
                } else if (len == 0) {
                    return;
                }
                int newcount = count + len;
//                if (newcount > fAttr.size) {
//                    throw new IOException("Insufficient space");
//                }

                fAttr.bos.write(b, off, len);
                count = newcount;
            }

            public void write(int b) throws IOException {
                int newcount = count + 1;
                fAttr.bos.write(b);
                count = newcount;
            }
        }

        /** This optional operation is not yet supported. */
        public FSDataOutputStream append(Path f, int bufferSize,
                                         Progressable progress) throws IOException {
            throw new IOException("Not supported");
        }

        /**
         * @param permission Currently ignored.
         */
        public FSDataOutputStream create(Path f, FsPermission permission,
                                         boolean overwrite, int bufferSize,
                                         short replication, long blockSize, Progressable progress)
                throws IOException {
            synchronized (this) {
                if (exists(f) && !overwrite) {
                    throw new IOException("File already exists:"+f);
                }
                FileAttributes fAttr = tempFileAttribs.remove(getPath(f));
                if (fAttr != null)
                    return create(f, fAttr);
                return create(f, new FileAttributes());
            }
        }

        public FSDataOutputStream create(Path f, FileAttributes fAttr)
                throws IOException {
            // the path is not added into the filesystem (in the pathToFileAttribs
            // map) until close is called on the outputstream that this method is
            // going to return
            // Create an output stream out of data byte array
            return new FSDataOutputStream(new InMemoryOutputStream(f, fAttr),
                    statistics);
        }

        public void close() throws IOException {
            super.close();
            synchronized (this) {
                if (pathToFileAttribs != null) {
                    pathToFileAttribs.clear();
                }
                pathToFileAttribs = null;
                if (tempFileAttribs != null) {
                    tempFileAttribs.clear();
                }
                tempFileAttribs = null;
            }
        }

        public boolean setReplication(Path src, short replication)
                throws IOException {
            return true;
        }

        public boolean rename(Path src, Path dst) throws IOException {
            synchronized (this) {
                if (exists(dst)) {
                    throw new IOException ("Path " + dst + " already exists");
                }
                FileAttributes fAttr = pathToFileAttribs.remove(getPath(src));
                if (fAttr == null) return false;
                pathToFileAttribs.put(getPath(dst), fAttr);
                return true;
            }
        }

        @Deprecated
        public boolean delete(Path f) throws IOException {
            return delete(f, true);
        }

        public boolean delete(Path f, boolean recursive) throws IOException {
            synchronized (this) {
                FileAttributes fAttr = pathToFileAttribs.remove(getPath(f));
                if (fAttr != null) {
                    fAttr.bytes = null;
//                    totalUsed -= fAttr.size;
                    return true;
                }
                return false;
            }
        }

        /**
         * Directory operations are not supported
         */
        public FileStatus[] listStatus(Path f) throws IOException {
            return null;
        }

        public void setWorkingDirectory(Path new_dir) {
            staticWorkingDir = new_dir;
        }

        public Path getWorkingDirectory() {
            return staticWorkingDir;
        }

        /**
         * @param permission Currently ignored.
         */
        public boolean mkdirs(Path f, FsPermission permission) throws IOException {
            return true;
        }

        public FileStatus getFileStatus(Path f) throws IOException {
            synchronized (this) {
                FileAttributes attr = pathToFileAttribs.get(getPath(f));
                if (attr==null) {
                    throw new FileNotFoundException("File " + f + " does not exist.");
                }
                return new InMemoryFileStatus(f.makeQualified(this), attr);
            }
        }

        /**
         * DQOps specific method - retrieves the byte array that was written.
         * @param f File path.
         * @return Byte array of the file content.
         * @throws IOException When file was not found
         */
        public byte[] getInMemoryFileContent(Path f) throws IOException {
            synchronized (this) {
                FileAttributes attr = pathToFileAttribs.get(getPath(f));
                if (attr==null) {
                    throw new FileNotFoundException("File " + f + " does not exist.");
                }
                return attr.bytes;
            }
        }

        /** Some APIs exclusively for InMemoryFileSystem */

        /** Register a path with its size. */
        public boolean reserveSpace(Path f, long size) {
            synchronized (this) {
                if (!canFitInMemory(size))
                    return false;
                FileAttributes fileAttr;
                try {
                    fileAttr = new FileAttributes();
                } catch (OutOfMemoryError o) {
                    return false;
                }
                totalUsed += size;
                tempFileAttribs.put(getPath(f), fileAttr);
                return true;
            }
        }
        public void unreserveSpace(Path f) {
            synchronized (this) {
                FileAttributes fAttr = tempFileAttribs.remove(getPath(f));
//                if (fAttr != null) {
//                    fAttr.data = null;
//                    totalUsed -= fAttr.size;
//                }
            }
        }

        /** This API getClosedFiles could have been implemented over listPathsRaw
         * but it is an overhead to maintain directory structures for this impl of
         * the in-memory fs.
         */
        public Path[] getFiles(PathFilter filter) {
            synchronized (this) {
                List<String> closedFilesList = new ArrayList<String>();
                synchronized (pathToFileAttribs) {
                    Set paths = pathToFileAttribs.keySet();
                    if (paths == null || paths.isEmpty()) {
                        return new Path[0];
                    }
                    Iterator iter = paths.iterator();
                    while (iter.hasNext()) {
                        String f = (String)iter.next();
                        if (filter.accept(new Path(f))) {
                            closedFilesList.add(f);
                        }
                    }
                }
                String [] names =
                        closedFilesList.toArray(new String[closedFilesList.size()]);
                Path [] results = new Path[names.length];
                for (int i = 0; i < names.length; i++) {
                    results[i] = new Path(names[i]);
                }
                return results;
            }
        }

        public int getNumFiles(PathFilter filter) {
            return getFiles(filter).length;
        }

        public long getFSSize() {
            return fsSize;
        }

        public float getPercentUsed() {
            if (fsSize > 0)
                return (float)totalUsed/fsSize;
            else return 0.1f;
        }

        /**
         * @TODO: Fix for Java6?
         * As of Java5 it is safe to assume that if the file can fit
         * in-memory then its file-size is less than Integer.MAX_VALUE.
         */
        private boolean canFitInMemory(long size) {
            if ((size <= Integer.MAX_VALUE) && ((size + totalUsed) < fsSize)) {
                return true;
            }
            return false;
        }

        private String getPath(Path f) {
            return f.toUri().getPath();
        }

        private static class FileAttributes {
            public FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
            private byte[] bytes;

            public FileAttributes() {
            }
        }

        private class InMemoryFileStatus extends FileStatus {
            InMemoryFileStatus(Path f, FileAttributes attr) throws IOException {
                super(attr.bytes != null ? attr.bytes.length : 0, false, 1, getDefaultBlockSize(), 0, f);
            }
        }

        /**
         * Check that a Path belongs to this FileSystem.
         *
         * @param path
         */
        @Override
        protected void checkPath(Path path) {
//        super.checkPath(path);
        }
    }

    public DqoInMemoryFileSystem() {
        super(new RawInMemoryFileSystem());
    }

    public DqoInMemoryFileSystem(URI uri, Configuration conf) {
        super(new RawInMemoryFileSystem(uri, conf));
        this.setWriteChecksum(false);
        this.setVerifyChecksum(false);
    }

    /**
     * Register a file with its size. This will also register a checksum for the
     * file that the user is trying to create. This is required since none of
     * the FileSystem APIs accept the size of the file as argument. But since it
     * is required for us to apriori know the size of the file we are going to
     * create, the user must call this method for each file he wants to create
     * and reserve memory for that file. We either succeed in reserving memory
     * for both the main file and the checksum file and return true, or return
     * false.
     */
    public boolean reserveSpaceWithCheckSum(Path f, long size) {
        RawInMemoryFileSystem mfs = (RawInMemoryFileSystem)getRawFileSystem();
        synchronized(mfs) {
            boolean b = mfs.reserveSpace(f, size);
            if (b) {
                long checksumSize = getChecksumFileLength(f, size);
                b = mfs.reserveSpace(getChecksumFile(f), checksumSize);
                if (!b) {
                    mfs.unreserveSpace(f);
                }
            }
            return b;
        }
    }

    public Path[] getFiles(PathFilter filter) {
        return ((RawInMemoryFileSystem)getRawFileSystem()).getFiles(filter);
    }

    public int getNumFiles(PathFilter filter) {
        return ((RawInMemoryFileSystem)getRawFileSystem()).getNumFiles(filter);
    }

    public long getFSSize() {
        return ((RawInMemoryFileSystem)getRawFileSystem()).getFSSize();
    }

    public float getPercentUsed() {
        return ((RawInMemoryFileSystem)getRawFileSystem()).getPercentUsed();
    }

    /**
     * DQOps specific method - retrieves the byte array that was written.
     * @param f File path.
     * @return Byte array of the file content.
     */
    public byte[] getInMemoryFileContent(Path f) throws IOException {
        synchronized (this) {
            RawInMemoryFileSystem rawFileSystem = (RawInMemoryFileSystem) getRawFileSystem();
            return rawFileSystem.getInMemoryFileContent(f);
        }
    }
}