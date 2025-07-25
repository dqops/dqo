/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.testutils;

import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.jobqueue.DqoJobQueueObjectMother;
import com.dqops.data.storage.TablesawParquetSupportFix;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeCreatorObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * JUnit 5 extension with a setup method called before all test methods. The test class must extend the {@link com.dqops.BaseTest}
 */
public class BeforeAllTestExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    /**
     * Called before each test run.
     * @param extensionContext
     * @throws Exception
     */
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        AbstractSpec.VALIDATE_FIELD_MAP_ON_WRITE = true;
        BeanFactory beanFactory = SpringExtension.getApplicationContext(extensionContext);
        BeanFactoryObjectMother.setBeanFactory(beanFactory); // let object mothers use the bean factory without propagating too many object instances
        TablesawParquetSupportFix.ensureInitialized();
        LocalFileSystemCacheObjectMother.getRealCache().start();
        LocalUserHomeCreatorObjectMother.initializeDefaultDqoUserHomeSilentlyOnce();
        DqoJobQueueObjectMother.ensureJobQueueIsStarted();

        // to be extended in the future when the need appears
    }

    /**
     * Called after running all tests. Supports closing/disposing resources.
     * @throws Throwable
     */
    @Override
    public void close() throws Throwable {
    }
}
