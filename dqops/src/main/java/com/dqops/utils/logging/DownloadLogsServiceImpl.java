/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.logging;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeCreatorImpl;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.exceptions.RunSilently;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Service that ZIPs all log files and returns it as an output stream. Zipping is performed in the background
 * because we need to expose an InputStream, and the Zip classes make an OutputStream.
 */
@Component
public class DownloadLogsServiceImpl implements DownloadLogsService {
    private static final int BUFFER_SIZE = 65536;

    private final HomeLocationFindService homeLocationFindService;

    @Autowired
    public DownloadLogsServiceImpl(HomeLocationFindService homeLocationFindService) {
        this.homeLocationFindService = homeLocationFindService;
    }

    /**
     * Creates an input stream that is generated on-the-fly by zipping all log files in the .log folder.
     * This method starts a background thread that will generate a zip output stream and reverse it to an input stream
     * that is usable for streaming.
     * @return Zip file as an input stream, to be forwarded to the user.
     */
    @Override
    public InputStream zipLogsOnTheFly() {
        try {
            PipedInputStream pipedInputStream = new PipedInputStream(BUFFER_SIZE);
            final PipedOutputStream pipedOutputStream = new PipedOutputStream();
            pipedInputStream.connect(pipedOutputStream);

            final ZipOutputStream zipOutputStream = new ZipOutputStream(pipedOutputStream);
            zipOutputStream.setLevel(Deflater.DEFAULT_COMPRESSION);

            String rootUserHomePath = this.homeLocationFindService.getRootUserHomePath();
            Path logsFolderPath = Path.of(rootUserHomePath).resolve(BuiltInFolderNames.LOGS);

            final File[] logFiles = logsFolderPath.toFile()
                    .listFiles((File dir, String name) -> name.startsWith(LocalUserHomeCreatorImpl.LOG_FILES_BASE_NAME));

            Thread zippingThread = new Thread(() -> {
                try {
                    if (logFiles == null) {
                        return;
                    }

                    byte[] buffer = new byte[BUFFER_SIZE];

                    for (File logFile : logFiles) {
                        try {
                            try (InputStream logFileInputStream = Files.newInputStream(logFile.toPath(), StandardOpenOption.READ)) {
                                zipOutputStream.putNextEntry(new ZipEntry(logFile.getName()));

                                int len = 0;
                                while (len != -1) {
                                    try {
                                        len = logFileInputStream.read(buffer, 0, buffer.length);
                                    }
                                    catch (Exception ex) {
                                        // no access to the log file, the file is in use, skipping the file
                                        break;
                                    }

                                    if (len <= 0) {
                                        break;
                                    }

                                    try {
                                        zipOutputStream.write(buffer, 0, len);
                                    }
                                    catch (Exception ex) {
                                        // the user cancelled the file download, stopping
                                        return;
                                    }
                                }
                            }
                        }
                        catch (Exception ex) {
                            // skip a file, probably it is in use
                        }
                    }
                }
                catch (Exception ex) {
                    // ignore, probably the user discarded the download dialog
                }
                finally {
                    RunSilently.run(() -> zipOutputStream.close());
                    RunSilently.run(() -> pipedOutputStream.close());
                }
            });

            zippingThread.setDaemon(true);
            zippingThread.start();

            return pipedInputStream;
        }
        catch (Exception ex) {
            throw new DqoRuntimeException("Failed to return a ZIP input stream, error: " + ex.getMessage(), ex);
        }
    }
}
