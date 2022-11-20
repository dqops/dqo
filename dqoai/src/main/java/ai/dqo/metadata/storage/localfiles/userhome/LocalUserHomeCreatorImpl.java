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
package ai.dqo.metadata.storage.localfiles.userhome;

import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindService;
import ai.dqo.core.filesystem.localfiles.LocalFileSystemException;
import ai.dqo.metadata.storage.localfiles.SpecFileNames;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Component that ensures that a DQO local user home was created and the default files were written.
 */
@Component
public class LocalUserHomeCreatorImpl implements LocalUserHomeCreator {
    private HomeLocationFindService homeLocationFindService;
    private TerminalReader terminalReader;
    private TerminalWriter terminalWriter;

    /**
     * Default constructor called by the IoC container.
     * @param homeLocationFindService User home location finder.
     * @param terminalReader Terminal reader - used to prompt the user before the default user home is created.
     * @param terminalWriter Terminal writer - used to notify the user that the default user home will not be created.
     */
    @Autowired
    public LocalUserHomeCreatorImpl(HomeLocationFindService homeLocationFindService,
                                    TerminalReader terminalReader,
                                    TerminalWriter terminalWriter) {
        this.homeLocationFindService = homeLocationFindService;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
    }

    /**
     * Tries to initialize (create) a folder.
     *
     * @param path Path to the folder.
     */
    public void initializeEmptyFolder(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception ex) {
                throw new LocalFileSystemException("Cannot create a folder " + path, ex);
            }
        } else {
            if (!Files.isDirectory(path)) {
                throw new BeanInitializationException("Local home cannot be initialized because " + path + " already exists and it is a file, not a folder.");
            }
        }
    }

    /**
     * Initializes the DQO user home at the default location.
     */
    @Override
    public void initializeDefaultDqoUserHome() {
        String userHomePathString = this.homeLocationFindService.getUserHomePath();

        if (userHomePathString == null) {
            return;
        }

        initializeDqoUserHome(userHomePathString);
    }

    /**
     * Checks the default DQO_USER_HOME path if it points to a valid and initialized DQO user home.
     * @return True when the path points to a valid DQO User home, false otherwise (the user home must be initialized before first use).
     */
    @Override
    public boolean isDefaultDqoUserHomeInitialized() {
        String userHomePathString = this.homeLocationFindService.getUserHomePath();

        if (userHomePathString == null) {
            return false;
        }

        return isDqoUserHomeInitialized(userHomePathString);
    }

    /**
     * Checks the given path if it points to a valid and initialized DQO user home.
     * @param userHomePathString Path to a potential DQO user home.
     * @return True when the path points to a valid DQO User home, false otherwise.
     */
    @Override
    public boolean isDqoUserHomeInitialized(String userHomePathString) {
        Path userHomePath = Path.of(userHomePathString);
        if (!Files.exists(userHomePath)) {
            return false;
        }

        if (!Files.exists(userHomePath.resolve(".gitignore"))) {
            return false;
        }

        if (!Files.exists(userHomePath.resolve(HomeLocationFindService.DQO_USER_HOME_MARKER_NAME))) {
            return false;
        }

        // we are not checking the existence of other folders (like rules, sensors, sources) because they
        // may have been empty when teh code was checked into Git and the user just checked the configuration out
        // those folders will be created on first use anyway, we need to ensure that the "marker" is there

        return true;
    }

    /**
     * Initializes a DQO user home at a given location.
     * @param userHomePathString Path to the DQO user home.
     */
    @Override
    public void initializeDqoUserHome(String userHomePathString) {
        try {
            Path userHomePath = Path.of(userHomePathString);
            initializeEmptyFolder(userHomePath);
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SOURCES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SENSORS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.RULES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.DATA));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.INDEX));
//        initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.CREDENTIALS));

            Path gitIgnorePath = userHomePath.resolve(".gitignore");
            if (!Files.exists(gitIgnorePath)) {
                String gitIgnoreContent =
                        BuiltInFolderNames.CREDENTIALS + "/\n" +
                                BuiltInFolderNames.DATA + "/\n" +
                                BuiltInFolderNames.INDEX + "/\n" +
                                SpecFileNames.SETTINGS_SPEC_FILE_NAME_YAML + "\n";

                Files.writeString(gitIgnorePath, gitIgnoreContent);
            }

            Path userHomeMarkerPath = userHomePath.resolve(HomeLocationFindService.DQO_USER_HOME_MARKER_NAME);
            if (!Files.exists(userHomeMarkerPath)) {
                String userHomeMarkerContent = "This is a marker file to identify a DQO_USER_HOME folder. Please check this file to Git.";

                Files.writeString(userHomeMarkerPath, userHomeMarkerContent);
            }
        }
        catch (Exception ex) {
            throw new LocalFileSystemException("Cannot initialize a DQO User home at " + userHomePathString, ex);
        }
    }

    /**
     * Ensures that the DQO User home is initialized at the default location. Prompts the user before creating the user home to confirm.
     * NOTE: this method may forcibly stop the program execution if the user did not agree to create the DQO User home.
     * @param isHeadless Is headless mode - when true, then the dqo user home is created silently, when false (interactive execution) then the user is asked to confirm.
     */
    @Override
    public void ensureDefaultUserHomeIsInitialized(boolean isHeadless) {
        String userHomePathString = this.homeLocationFindService.getUserHomePath();
        if (userHomePathString == null) {
            return; // the dqo user home is not required for some reason (configurable)
        }

        if (this.isDefaultDqoUserHomeInitialized()) {
            return;
        }

        if (isHeadless) {
            this.initializeDefaultDqoUserHome();
        }
        else {
            if (this.terminalReader.promptBoolean("Initialize a DQO user home at " + userHomePathString, true)) {
                this.initializeDefaultDqoUserHome();
                return;
            }

            this.terminalWriter.writeLine("DQO user home will not be created, exiting.");
            System.exit(100);
        }
    }
}
