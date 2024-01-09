package com.dqops.metadata.storage.localfiles.defaultschedules;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;

/**
 * File based monitoring schedules spec wrapper. Loads and writes the monitoring schedules information to a yaml file in the user's home folder.
 */
@Slf4j
public class FileMonitoringSchedulesWrapperImpl extends MonitoringSchedulesWrapperImpl {

    @JsonIgnore
    private final FolderTreeNode settingsFolderNode;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a monitoring schedules wrapper for a monitoring schedules specification that uses yaml files for storage.
     * @param settingsFolderNode Folder with yaml files for settings specifications.
     * @param yamlSerializer Yaml serializer.
     */
    public FileMonitoringSchedulesWrapperImpl(FolderTreeNode settingsFolderNode, YamlSerializer yamlSerializer) {
        this.settingsFolderNode = settingsFolderNode;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Loads the default schedules specification from a .yaml file.
     * @return Loaded default schedules specification.
     */
    @Override
    public DefaultSchedulesSpec getSpec() {
        DefaultSchedulesSpec spec = super.getSpec();
        if (spec == null && this.getStatus() == InstanceStatus.LOAD_IN_PROGRESS) {
            FileTreeNode fileNode = this.settingsFolderNode.getChildFileByFileName(SpecFileNames.DEFAULT_MONITORING_SCHEDULES_SPEC_FILE_NAME_YAML);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                DefaultSchedulesSpec deserializedSpec = (DefaultSchedulesSpec) fileContent.getCachedObjectInstance();

                if (deserializedSpec == null) {
                    DefaultSchedulesYaml deserialized = this.yamlSerializer.deserialize(textContent, DefaultSchedulesYaml.class, fileNode.getPhysicalAbsolutePath());
                    deserializedSpec = deserialized.getSpec();
                    if (deserialized.getKind() != SpecificationKind.DEFAULT_SCHEDULES) {
                        log.info("Invalid specification kind, found: " + deserialized.getKind() + ", but expected: " + SpecificationKind.DEFAULT_SCHEDULES);
//                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }
                    if (deserializedSpec != null) {
                        fileContent.setCachedObjectInstance(deserializedSpec.deepClone());
                    }
                } else {
                    deserializedSpec = deserializedSpec.deepClone();
                }
                this.setSpec(deserializedSpec);
                deserializedSpec.clearDirty(true);
                this.clearDirty(false);
                return deserializedSpec;
            } else {
                this.setSpec(null);
            }
        }
        return spec;
    }

    /**
     * Flushes changes to the persistent storage.
     */
    @Override
    public void flush() {
        if (this.getStatus() == InstanceStatus.DELETED || this.getStatus() == InstanceStatus.NOT_TOUCHED) {
            return; // do nothing
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() == null) {
            return; // nothing to do, the instance is empty (no file)
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty() ) {
            super.getSpec().clearDirty(true);
            this.setStatus(InstanceStatus.MODIFIED);
        }

        DefaultSchedulesYaml defaultSchedulesYaml = new DefaultSchedulesYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(defaultSchedulesYaml);
        FileContent newFileContent = new FileContent(specAsYaml);
        String fileNameWithExt = SpecFileNames.DEFAULT_MONITORING_SCHEDULES_SPEC_FILE_NAME_YAML;

        switch (this.getStatus()) {
            case ADDED:
                this.settingsFolderNode.addChildFile(fileNameWithExt, newFileContent);
                this.getSpec().clearDirty(true);
                break;

            case MODIFIED:
                FileTreeNode modifiedFileNode = this.settingsFolderNode.getChildFileByFileName(fileNameWithExt);
                if (modifiedFileNode != null) {
                    modifiedFileNode.changeContent(newFileContent);
                }
                else {
                    this.settingsFolderNode.addChildFile(fileNameWithExt, newFileContent);
                }
                this.getSpec().clearDirty(true);
                break;

            case TO_BE_DELETED:
                this.settingsFolderNode.deleteChildFile(fileNameWithExt);
                break;
        }

        super.flush(); // change the statuses
    }

}
