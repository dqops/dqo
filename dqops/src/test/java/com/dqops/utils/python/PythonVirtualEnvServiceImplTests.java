/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.python;

import com.dqops.BaseTest;
import com.dqops.core.configuration.*;
import com.dqops.testutils.TestFolderUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
public class PythonVirtualEnvServiceImplTests extends BaseTest {
    private PythonVirtualEnvServiceImpl sut;
    private PythonVirtualEnvService realSut;
    private DqoConfigurationProperties dqoConfigurationProperties;
    private DqoPythonConfigurationProperties pythonConfigurationProperties;

    @BeforeEach
    void setUp() {
		dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
		pythonConfigurationProperties = DqoPythonConfigurationPropertiesObjectMother.getDefaultCloned();
        String testableHome = TestFolderUtilities.createEmptyTestableFolder("PythonVirtualEnvServiceImplTests");
		dqoConfigurationProperties.setHome(testableHome);

		this.sut = new PythonVirtualEnvServiceImpl(
				dqoConfigurationProperties,
				pythonConfigurationProperties,
                DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration());
		this.realSut = PythonVirtualEnvServiceObjectMother.getDefault();
    }

    @Test
    void getVEnvPath_whenCalled_thenReturnsFolderInsideDqoHome() {
        Path vEnvPath = this.sut.getVEnvPath();

        Assertions.assertNotNull(vEnvPath);

        Assertions.assertTrue(vEnvPath.startsWith(Path.of(this.dqoConfigurationProperties.getHome())));
    }

    @Test
    void isVirtualEnvInitialized_whenEmptyFolder_thenReturnsFalse() {
        Assertions.assertFalse(this.sut.isVirtualEnvInitialized());
    }

    @Test
    void getVirtualEnv_wheRealEnvironment_thenReturnsInitializedEnvironment() {
        PythonVirtualEnv virtualEnv = this.realSut.getVirtualEnv();
        Assertions.assertTrue(Files.exists(Path.of(virtualEnv.getPythonInterpreterPath())));
        Assertions.assertTrue(this.realSut.isVirtualEnvInitialized());
    }

//    @Test
//    void getVirtualEnv_whenFolderHasNoPython_thenInitializesPython() {
//        // this test is lengthy, we are ignoring it...
//        Assertions.assertFalse(this.sut.isVirtualEnvInitialized());
//        PythonVirtualEnv virtualEnv = this.sut.getVirtualEnv();
//        Assertions.assertTrue(this.sut.isVirtualEnvInitialized());
//        Assertions.assertTrue(Files.exists(Path.of(virtualEnv.getPythonInterpreterPath())));
//    }
}
