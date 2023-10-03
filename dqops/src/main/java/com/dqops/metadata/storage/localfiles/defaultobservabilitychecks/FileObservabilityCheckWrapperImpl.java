package com.dqops.metadata.storage.localfiles.defaultobservabilitychecks;

import com.dqops.checks.defaults.DefaultObservabilityChecksSpec;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.settings.defaultchecks.DefaultObservabilityCheckWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;

/**
 * File based observability check settings spec wrapper. Loads and writes the observability check settings to a yaml file in the user's home folder.
 */
@Slf4j
public class FileObservabilityCheckWrapperImpl extends DefaultObservabilityCheckWrapperImpl {

    @JsonIgnore
    private final FolderTreeNode settingsFolderNode;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates an observability check wrapper for its specification that uses yaml files for storage.
     * @param settingsFolderNode Folder with yaml files for settings specifications.
     * @param yamlSerializer Yaml serializer.
     */
    public FileObservabilityCheckWrapperImpl(FolderTreeNode settingsFolderNode, YamlSerializer yamlSerializer) {
        this.settingsFolderNode = settingsFolderNode;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Loads the default observability check setting specification from a .yaml file.
     * @return Loaded default schedules specification.
     */
    @Override
    public DefaultObservabilityChecksSpec getSpec() {
        DefaultObservabilityChecksSpec spec = super.getSpec();
        if (spec == null && this.getStatus() == InstanceStatus.NOT_TOUCHED) {
            FileTreeNode fileNode = this.settingsFolderNode.getChildFileByFileName(SpecFileNames.DEFAULT_OBSERVABILITY_CHECKS_SPEC_FILE_NAME_YAML);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                DefaultObservabilityChecksSpec deserializedSpec = (DefaultObservabilityChecksSpec) fileContent.getCachedObjectInstance();

                if (deserializedSpec == null) {
                    DefaultObservabilityChecksYaml deserialized = this.yamlSerializer.deserialize(textContent, DefaultObservabilityChecksYaml.class, fileNode.getPhysicalAbsolutePath());
                    deserializedSpec = deserialized.getSpec();
                    if (deserialized.getKind() != SpecificationKind.DEFAULT_CHECKS) {
                        log.info("Invalid specification kind, found: " + deserialized.getKind() + ", but expected: " + SpecificationKind.DEFAULT_CHECKS);
//                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }
                    if (deserializedSpec != null) {
                        fileContent.setCachedObjectInstance(deserializedSpec.deepClone());
                    }
                } else {
                    deserializedSpec = (DefaultObservabilityChecksSpec) deserializedSpec.deepClone();
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

        DefaultObservabilityChecksYaml defaultObservabilityChecksYaml = new DefaultObservabilityChecksYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(defaultObservabilityChecksYaml);
        FileContent newFileContent = new FileContent(specAsYaml);
        String fileNameWithExt = SpecFileNames.DEFAULT_OBSERVABILITY_CHECKS_SPEC_FILE_NAME_YAML;

        switch (this.getStatus()) {
            case ADDED:
                this.settingsFolderNode.addChildFile(fileNameWithExt, newFileContent);
                this.getSpec().clearDirty(true);
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
