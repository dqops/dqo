package com.dqops.metadata.storage.localfiles.webhooks;

import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Default incident webhook notification spec wrapper.
 */
public class FileDefaultIncidentWebhookNotificationsWrapperImpl extends DefaultIncidentWebhookNotificationsWrapperImpl {

    @JsonIgnore
    private final FolderTreeNode settingsFolderNode;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a default notification webhooks wrapper for its specification that uses yaml files for storage.
     * @param settingsFolderNode Folder with yaml files for webhooks specifications.
     * @param yamlSerializer Yaml serializer.
     */
    public FileDefaultIncidentWebhookNotificationsWrapperImpl(FolderTreeNode settingsFolderNode, YamlSerializer yamlSerializer) {
        this.settingsFolderNode = settingsFolderNode;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Loads the default notification webhooks specification from a .yaml file.
     * @return Loaded default notification webhooks specification.
     */
    @Override
    public IncidentWebhookNotificationsSpec getSpec() {
        IncidentWebhookNotificationsSpec spec = super.getSpec();
        if (spec == null && this.getStatus() == InstanceStatus.NOT_TOUCHED) {
            FileTreeNode fileNode = this.settingsFolderNode.getChildFileByFileName(SpecFileNames.DEFAULT_NOTIFICATION_WEBHOOKS_FILE_NAME_YAML);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                IncidentWebhookNotificationsSpec deserializedSpec = (IncidentWebhookNotificationsSpec) fileContent.getCachedObjectInstance();

                if (deserializedSpec == null) {
                    DefaultIncidentWebhookNotificationsYaml deserialized = this.yamlSerializer.deserialize(textContent, DefaultIncidentWebhookNotificationsYaml.class, fileNode.getPhysicalAbsolutePath());
                    deserializedSpec = deserialized.getSpec();
                    if (deserialized.getKind() != SpecificationKind.NOTIFICATION_WEBHOOKS) {
                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }
                    if (deserializedSpec != null) {
                        fileContent.setCachedObjectInstance(deserializedSpec.deepClone());
                    }
                } else {
                    deserializedSpec = (IncidentWebhookNotificationsSpec) deserializedSpec.deepClone();
                }
                this.setSpec(deserializedSpec);
                deserializedSpec.clearDirty(true);
                this.clearDirty(false);
                return deserializedSpec;
            } else {
                setSpec(null);
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

        DefaultIncidentWebhookNotificationsYaml defaultIncidentWebhookNotificationsYaml
                = new DefaultIncidentWebhookNotificationsYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(defaultIncidentWebhookNotificationsYaml);
        FileContent newFileContent = new FileContent(specAsYaml);
        String fileNameWithExt = SpecFileNames.DEFAULT_NOTIFICATION_WEBHOOKS_FILE_NAME_YAML;

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
