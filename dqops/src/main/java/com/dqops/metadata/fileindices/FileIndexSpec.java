/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.fileindices;

import com.dqops.core.filesystem.metadata.FolderMetadata;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * File index specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class FileIndexSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<FileIndexSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Folder file index.")
    private FolderMetadata folder = new FolderMetadata();

    @JsonPropertyDescription("The tenant id to which the files are synchronized.")
    private String tenantId;

    @JsonPropertyDescription("The data domain to which the files are synchronized.")
    private String domain;

    /**
     * Default constructor.
     */
    public FileIndexSpec() {
    }

    /**
     * Returns the folder node.
     * @return Folder node.
     */
    public FolderMetadata getFolder() {
        return folder;
    }

    /**
     * Sets a new folder node.
     * @param folder Folder node.
     */
    public void setFolder(FolderMetadata folder) {
        this.setDirtyIf(this.folder != folder);
        this.folder = folder;
    }

    /**
     * Returns the tenant id to which the files are synchronized.
     * @return Tenant id.
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Sets the tenant id to which the files are synchronized.
     * @param tenantId Tenant id.
     */
    public void setTenantId(String tenantId) {
        this.setDirtyIf(!Objects.equals(this.tenantId, tenantId));
        this.tenantId = tenantId;
    }

    /**
     * Returns the data domain to which the files are synchronized.
     * @return Data domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the data domain to which the files are synchronized.
     * @param domain Data domain.
     */
    public void setDomain(String domain) {
        this.setDirtyIf(!Objects.equals(this.domain, domain));
        this.domain = domain;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Retrieves the file index name from the hierarchy.
     * @return File index name or null for a standalone file index spec object.
     */
    @JsonIgnore
    public String getFileIndexName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.get(hierarchyId.size() - 2).toString();
    }
}
