package ai.dqo.core.synchronization.contract;

import ai.dqo.core.filesystem.metadata.FileMetadata;
import reactor.netty.ByteBufFlux;

/**
 * Response object with a downloaded file. Combines the file metadata (length and hash) with the flux of byte buffers (file content).
 */
public class DownloadFileResponse {
    private final FileMetadata metadata;
    private final ByteBufFlux byteBufFlux;

    /**
     * Creates a download file response object.
     * @param metadata Metadata object with the file length and hash.
     * @param byteBufFlux Flux of byte buffers (the file content).
     */
    public DownloadFileResponse(FileMetadata metadata, ByteBufFlux byteBufFlux) {
        this.metadata = metadata;
        this.byteBufFlux = byteBufFlux;
    }

    /**
     * Returns the file metadata object (file length and file hash).
     * @return File metadata.
     */
    public FileMetadata getMetadata() {
        return metadata;
    }

    /**
     * Returns a flux of file buffers with the file content.
     * @return Flux of file buffers (file content).
     */
    public ByteBufFlux getByteBufFlux() {
        return byteBufFlux;
    }
}
