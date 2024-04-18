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
package com.dqops.cli.edit;

import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.sources.FileConnectionWrapperImpl;
import com.dqops.metadata.storage.localfiles.sources.FileTableWrapperImpl;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.browser.OpenBrowserFailedException;
import com.google.api.client.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;

/**
 * Service called from "edit" command line commands to open an editor for editing .yaml or .py files.
 */
@Service
public class EditorLaunchServiceImpl implements EditorLaunchService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final TerminalFactory terminalFactory;
    private final DqoUserPrincipalProvider userPrincipalProvider;

    /**
     * User home context factory.
     * @param userHomeContextFactory User home context factory.
     * @param terminalFactory Terminal factory.
     * @param userPrincipalProvider User principal provider.
     */
    @Autowired
    public EditorLaunchServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                   TerminalFactory terminalFactory,
                                   DqoUserPrincipalProvider userPrincipalProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.terminalFactory = terminalFactory;
        this.userPrincipalProvider = userPrincipalProvider;
    }

    /**
     * Opens an editor for a file that contains the given target object.
     * @param targetObjectId Target object id.
     */
    public void openEditorFor(HierarchyId targetObjectId) {
        DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();

        TableWrapper tableWrapper = userHome.findTableFor(targetObjectId);
        if (tableWrapper != null) {
			openEditorForTable(tableWrapper);
            return;
        }

        ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetObjectId);
        if (connectionWrapper != null) {
			openEditorForConnection(connectionWrapper);
            return;
        }

        throw new NoSuchElementException("Unknown object, no editor available for " + targetObjectId.toString());
    }

    /**
     * Opens an editor for a connection specification yaml.
     * @param connectionWrapper Connection wrapper.
     */
    public void openEditorForConnection(ConnectionWrapper connectionWrapper) {
        FileConnectionWrapperImpl fileConnectionWrapper = (FileConnectionWrapperImpl)connectionWrapper;
        FolderTreeNode connectionFolderNode = fileConnectionWrapper.getConnectionFolderNode();
        FileTreeNode fileNode = connectionFolderNode.getChildFileByFileName(SpecFileNames.CONNECTION_SPEC_FILE_NAME_YAML);

        Path physicalAbsolutePath = fileNode.getPhysicalAbsolutePath();
        String filePath = physicalAbsolutePath.toString();
		launchEditorForFile(filePath);
    }

    /**
     * Opens an editor for a table specification yaml.
     * @param tableWrapper Table wrapper.
     */
    public void openEditorForTable(TableWrapper tableWrapper) {
        FileTableWrapperImpl fileTableWrapper = (FileTableWrapperImpl)tableWrapper;
        FolderTreeNode connectionFolderNode = fileTableWrapper.getConnectionFolderNode();
        String fileNameWithExt = fileTableWrapper.getRealBaseFileName() + SpecFileNames.TABLE_SPEC_FILE_EXT_YAML;
        FileTreeNode fileNode = connectionFolderNode.getChildFileByFileName(fileNameWithExt);

        Path physicalAbsolutePath = fileNode.getPhysicalAbsolutePath();
        String filePath = physicalAbsolutePath.toString();
		launchEditorForFile(filePath);
    }

    private void openFileInPyCharm(String filePath, String editorPath) {
        String myOS = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        this.terminalFactory.getWriter().writeLine("Launching PyCharm");
        try {
            if (myOS.contains("windows")) {
                runtime.exec(new String[]{"cmd", "/c", "start", "\"" + editorPath + "/bin/pycharm64.exe\"", filePath});
            } else if (myOS.contains("mac")) { // Apples
                runtime.exec("./" + editorPath + "/pycharm " + filePath);
            } else if (myOS.contains("nix") || myOS.contains("nux")) { // Linux flavours
                runtime.exec("./" + editorPath + "/pycharm.sh " + filePath);
            }
        } catch(IOException ex) {
            throw new OpenBrowserFailedException("Cannot open PyCharm, reason: " + ex.getMessage(), ex);
        }
    }


    private void openFileInIntelliJ(String filePath, String editorPath) {
        String myOS = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        this.terminalFactory.getWriter().writeLine("Launching IntelliJ");
        try {
            if (myOS.contains("windows")) {
                runtime.exec(new String[]{"cmd", "/c", editorPath + "bin/idea64.exe", filePath});
            } else if (myOS.contains("mac")) { // Apples
                runtime.exec("./" + editorPath + "/idea " + filePath);
            } else if (myOS.contains("nix") || myOS.contains("nux")) { // Linux flavours
                runtime.exec("./" + editorPath + "/idea.sh " + filePath);
            }
        } catch(IOException ex) {
            throw new OpenBrowserFailedException("Cannot open IntelliJ, reason: " + ex.getMessage(), ex);
        }
    }

    private void openFileInEclipse(String filePath, String editorPath) {
        String myOS = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        this.terminalFactory.getWriter().writeLine("Launching Eclipse");
        try {
            if (myOS.contains("windows")) {
                runtime.exec(new String[]{"cmd", "/c", editorPath + "bin/eclipse.exe", filePath});
            } else if (myOS.contains("mac")) { // Apples
                runtime.exec("./" + editorPath + "/eclipse " + filePath);
            } else if (myOS.contains("nix") || myOS.contains("nux")) { // Linux flavours
                runtime.exec("./" + editorPath + "/eclipse.sh " + filePath);
            }
        } catch(IOException ex) {
            throw new OpenBrowserFailedException("Cannot open Eclipse, reason: " + ex.getMessage(), ex);
        }
    }

    private void openFileInVSC(String filePath) {
        String myOS = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        this.terminalFactory.getWriter().writeLine("Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin");
        try {
            if (myOS.contains("windows")) {
                runtime.exec(new String[]{"cmd", "/c", "code.cmd", filePath});
            } else { // unix-similar
                runtime.exec(new String[]{"code", filePath});
            }
        } catch(IOException ex) {
            throw new OpenBrowserFailedException("Cannot open VSC, reason: " + ex.getMessage(), ex);
        }
    }

    private void defaultOpenFile(String pathToTextFile) {
        try {
            openFileInVSC(pathToTextFile);
            return;
        } catch (Exception ex) {
            throw new EditorLaunchException(String.format("You need to install and set default editor, try 'settings' command"));
        }
    }

    /**
     * Launches a text editor for a file given.
     * @param pathToTextFile Path to the text file that will be edited (it could be .yaml, .py)
     */
    public void launchEditorForFile(String pathToTextFile) {
        DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();
        LocalSettingsSpec localSettingsSpec = userHome.getSettings().getSpec();
        if (localSettingsSpec == null) {
            defaultOpenFile(pathToTextFile);
            return;
        }
        String editorName = localSettingsSpec.getEditorName();
        String editorPath = localSettingsSpec.getEditorPath();

        if (Strings.isNullOrEmpty(editorName)) {
            defaultOpenFile(pathToTextFile);
            return;
        }

        try {
            switch (editorName) {
                case "IntelliJ":
                    openFileInIntelliJ(pathToTextFile, editorPath);
                    break;
                case "PyCharm":
                    openFileInPyCharm(pathToTextFile, editorPath);
                    break;
                case "Eclipse":
                    openFileInEclipse(pathToTextFile, editorPath);
                    break;
                default:
                    openFileInVSC(pathToTextFile);
                    break;
            }
        }
        catch (Exception ex) {
            throw new EditorLaunchException(String.format("Failed to start the editor %s for %s, error: %s", editorName, pathToTextFile, ex.getMessage()));
        }
    }
}
