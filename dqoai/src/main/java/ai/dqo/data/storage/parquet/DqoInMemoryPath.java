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
    private InMemoryFileSystem fileSystem;

    public DqoInMemoryPath(URI aUri, InMemoryFileSystem fileSystem) {
        super(aUri);
        assert Objects.equals(aUri.getScheme(), "ramfs");
        this.fileSystem = fileSystem;
    }

    public DqoInMemoryPath(String path, InMemoryFileSystem fileSystem) {
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
