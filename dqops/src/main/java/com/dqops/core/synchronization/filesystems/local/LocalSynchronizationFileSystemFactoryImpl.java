/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.filesystems.local;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.SynchronizationRoot;
import com.dqops.data.local.LocalDqoUserHomePathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * DQOps file system that accesses local files (selected folders) in the DQO_USER_HOME.
 */
@Component
public class LocalSynchronizationFileSystemFactoryImpl implements LocalSynchronizationFileSystemFactory {
    private LocalFileSystemSynchronizationOperations localFileSystemService;
    private LocalDqoUserHomePathProvider localDqoUserHomePathProvider;

    /**
     * Default injection constructor.
     * @param localFileSystemService Local file system service.
     * @param localDqoUserHomePathProvider Local DQOps User Home path provider.
     */
    @Autowired
    public LocalSynchronizationFileSystemFactoryImpl(LocalFileSystemSynchronizationOperations localFileSystemService,
                                                     LocalDqoUserHomePathProvider localDqoUserHomePathProvider) {
        this.localFileSystemService = localFileSystemService;
        this.localDqoUserHomePathProvider = localDqoUserHomePathProvider;
    }

    /**
     * Creates a DQOps file system that accesses physical files on the local file system.
     * @param rootType Root type (folder type).
     * @param userIdentity User identity that identifies the data domain.
     * @return DQOps file system that can manage local files in a selected folder.
     */
    @Override
    public SynchronizationRoot createUserHomeFolderFileSystem(DqoRoot rootType, UserDomainIdentity userIdentity) {
        Path absoluteLocalPathToFolder = getAbsoluteLocalPathToFolder(rootType, userIdentity);
        UserHomeFileSystemSynchronizationRoot userHomeFileSystemRoot = new UserHomeFileSystemSynchronizationRoot(absoluteLocalPathToFolder, rootType);
        return new SynchronizationRoot(userHomeFileSystemRoot, this.localFileSystemService);
    }

    /**
     * Returns an absolute path to a selected folder inside the DQO_USER_HOME.
     * @param rootType Root type (folder type).
     * @param userIdentity User identity that identifies the data domain.
     * @return Absolute file system path to a requested folder.
     */
    public Path getAbsoluteLocalPathToFolder(DqoRoot rootType, UserDomainIdentity userIdentity) {
        Path localUserHomePath = this.localDqoUserHomePathProvider.getLocalUserHomePath(userIdentity);

        switch (rootType) {
            case data_sensor_readouts:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.SENSOR_READOUTS);

            case data_check_results:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.CHECK_RESULTS);

            case data_errors:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.ERRORS);

            case data_error_samples:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.ERROR_SAMPLES);

            case data_statistics:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.STATISTICS);

            case data_incidents:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.INCIDENTS);

            case sources:
                return localUserHomePath.resolve(BuiltInFolderNames.SOURCES);

            case sensors:
                return localUserHomePath.resolve(BuiltInFolderNames.SENSORS);

            case rules:
                return localUserHomePath.resolve(BuiltInFolderNames.RULES);

            case checks:
                return localUserHomePath.resolve(BuiltInFolderNames.CHECKS);

            case settings:
                return localUserHomePath.resolve(BuiltInFolderNames.SETTINGS);

            case credentials:
                return localUserHomePath.resolve(BuiltInFolderNames.CREDENTIALS);

            case dictionaries:
                return localUserHomePath.resolve(BuiltInFolderNames.DICTIONARIES);

            case patterns:
                return localUserHomePath.resolve(BuiltInFolderNames.PATTERNS);

            default:
                throw new IllegalArgumentException("Unsupported root: " +  rootType.toString());
        }
    }
}
