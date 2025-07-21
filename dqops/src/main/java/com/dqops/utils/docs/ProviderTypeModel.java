/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs;

import com.dqops.connectors.ProviderType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Provider info model. Creates a simple display model of {@link com.dqops.connectors.ProviderType}.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProviderTypeModel {
    /**
     * Provider type.
     */
    private ProviderType providerType;

    /**
     * Provider type as string.
     */
    private String providerTypeName;

    /**
     * Provider type as a display string.
     */
    private String providerTypeDisplayName;

    public static ProviderTypeModel fromProviderType(ProviderType providerType) {
        return new ProviderTypeModel(providerType, providerType.name(), providerType.getDisplayName());
    }
}
