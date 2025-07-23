/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.localfiles;

import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.storage.localfiles.HomeType;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Simple service that returns the location of the user home or the DQO_HOME system home.
 */
@Component
public class HomeLocationFindServiceImpl implements HomeLocationFindService {
    private final DqoUserConfigurationProperties userConfigurationProperties;
    private final DqoConfigurationProperties dqoConfigurationProperties;
    private String userHomePath;

    /**
     * Dependency injection constructor that receives dependencies to the configuration parameters.
     * @param userConfigurationProperties Configuration parameters for the user home.
     * @param dqoConfigurationProperties Configuration parameters for the DQO_HOME.
     */
    @Autowired
    public HomeLocationFindServiceImpl(DqoUserConfigurationProperties userConfigurationProperties,
									   DqoConfigurationProperties dqoConfigurationProperties) {
        this.userConfigurationProperties = userConfigurationProperties;
        this.dqoConfigurationProperties = dqoConfigurationProperties;
    }

    /**
     * Detects a user home by checking parent folders.
     * @param userHomePath User home path. Should be an absolute path.
     * @return Proposed user home path or it's parent folder if the user called the tool in a subpath.
     */
    private Path detectParentDqoUserHome(Path userHomePath) {
        assert userHomePath.isAbsolute();

        int nameCount = userHomePath.getNameCount();
        for (int i = nameCount; i > 0; i--) {
            Path subPath = userHomePath.getRoot().resolve(userHomePath.subpath(0, i));

            Path pathToDqoUserHomeMarker = subPath.resolve(DQO_USER_HOME_MARKER_NAME);
            if (Files.exists(pathToDqoUserHomeMarker)) {
                return subPath; // DQOPS USER HOME found in a parent folder, the user started dqo.sh/dqo.cmd when the current working folder was a nested path
            }
        }

        return userHomePath;
    }

    /**
     * Returns an absolute path to the user home.
     * @param userDomainIdentity User domain identity to identify a data domain.
     * @return Absolute path to the user home. May return null if the user home is not enabled.
     */
    @Override
    public String getUserHomePath(UserDomainIdentity userDomainIdentity) {
        String rootUserHomePath = this.getRootUserHomePath();

        if (rootUserHomePath == null) {
            return null;
        }

        if (Objects.equals(userDomainIdentity.getDataDomainFolder(), UserDomainIdentity.ROOT_DATA_DOMAIN)) {
            return rootUserHomePath;
        }

        String nestedDataDomainUserHomePath = Path.of(rootUserHomePath)
                .resolve(BuiltInFolderNames.DATA_DOMAINS)
                .resolve(userDomainIdentity.getDataDomainFolder())
                .toString();

        return nestedDataDomainUserHomePath;
    }

    /**
     * Returns an absolute path to the root user home.
     *
     * @return Absolute path to the user home. May return null if the user home is not enabled.
     */
    @Override
    public String getRootUserHomePath() {
        if (this.userConfigurationProperties.isHasLocalHome()) {
            if (this.userHomePath != null) {
                return this.userHomePath;
            }

            String userHomePathString = this.userConfigurationProperties.getHome();
            if (Strings.isNullOrEmpty(userHomePathString)) {
                userHomePathString = Path.of(".").toAbsolutePath().normalize().toString();
            }

            Path candidatePathToUserHome = Path.of(userHomePathString).toAbsolutePath().normalize();
            Path pathToUserHome = detectParentDqoUserHome(candidatePathToUserHome);

            if (Files.exists(pathToUserHome) && !Files.isDirectory(pathToUserHome)) {
                throw new LocalFileSystemException("User home path is not accessible or is not a directory: " + pathToUserHome);
            }

            if (!Files.exists(pathToUserHome)) {
                try {
                    Files.createDirectories(pathToUserHome);
                }
                catch (IOException ex) {
                    throw new LocalFileSystemException("Cannot create a DQOps user home at " + pathToUserHome.toString(), ex);
                }
            }

            this.userHomePath = pathToUserHome.toString();
            return this.userHomePath;
        }
        else {
            return null; // no home.
        }
    }

    /**
     * Returns an absolute path to the dqo home.
     * @return Absolute path to the DQO_HOME.
     */
    @Override
    public String getDqoHomePath() {
        if (Strings.isNullOrEmpty(this.dqoConfigurationProperties.getHome())) {
            throw new LocalFileSystemException("DQO_HOME is not specified");
        }

        Path pathToDqoHome = Path.of(this.dqoConfigurationProperties.getHome());
        if (Files.isDirectory(pathToDqoHome)) {
            return pathToDqoHome.toAbsolutePath().normalize().toString();
        } else {
            throw new LocalFileSystemException("DQO_HOME is not accessible or is not a directory: " + pathToDqoHome);
        }
    }

    /**
     * Returns the absolute path to a home of choice (user home or DQO_HOME).
     * @param homeType Home type (user home or dqo system home).
     * @param userDomainIdentity User domain identity.
     * @return Absolute path to home.
     */
    @Override
    public String getHomePath(HomeType homeType, UserDomainIdentity userDomainIdentity) {
        switch (homeType) {
            case USER_HOME:
                return getUserHomePath(userDomainIdentity);
            case DQO_HOME:
                return getDqoHomePath();
            default:
                throw new IllegalArgumentException("Unknown home type: " + homeType);
        }
    }
}
