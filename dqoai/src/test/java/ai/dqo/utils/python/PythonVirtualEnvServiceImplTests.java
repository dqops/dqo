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
package ai.dqo.utils.python;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.*;
import ai.dqo.testutils.TestFolderUtilities;
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
		pythonConfigurationProperties = DqoPythonConfigurationPropertiesObjectMother.getDefault();
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
