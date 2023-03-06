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
package ai.dqo.data.storage.parquet;

import ai.dqo.utils.exceptions.DqoRuntimeException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Custom hadoop path to access files in the in-memory file system.
 */
public class DqoInMemoryPath extends Path {
    private FileSystem fileSystem;

    public DqoInMemoryPath(URI aUri, FileSystem fileSystem) {
        super(aUri);
//        assert Objects.equals(aUri.getScheme(), "ramfs");
        this.fileSystem = fileSystem;
    }

    public DqoInMemoryPath(String path, FileSystem fileSystem) {
        this(makeUri(path), fileSystem);
    }

    private static URI makeUri(String path) {
        try {
            return new URI(path);
        }
        catch (URISyntaxException ex) {
            throw new DqoRuntimeException(ex);
        }
    }

    /**
     * Returns a given in-memory file system instance, to separate in-memory file spaces.
     * @param conf Hadoop configuration, ignored.
     * @return File system instance.
     */
    @Override
    public FileSystem getFileSystem(Configuration conf) {
        return this.fileSystem;
    }
}
