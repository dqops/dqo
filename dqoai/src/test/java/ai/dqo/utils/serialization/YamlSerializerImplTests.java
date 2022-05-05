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
package ai.dqo.utils.serialization;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class YamlSerializerImplTests extends BaseTest {
    private YamlSerializerImpl sut;
    private DqoConfigurationProperties configurationProperties;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		configurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
		this.sut = new YamlSerializerImpl(configurationProperties);
    }

    @Test
    void serialize_whenObjectGiven_thenIsSerialized() throws Exception {
        YamlTestable yamlTestable = new YamlTestable();
        yamlTestable.setField1("abc");
        yamlTestable.setInt1(10);

        String serialized = this.sut.serialize(yamlTestable);

        String expected = "# yaml-language-server: $schema=" + this.configurationProperties.getYamlSchemaServer() + "YamlTestable-schema.json\n" +
                "field1: abc\nint1: 10\n";
        Assertions.assertEquals(expected, serialized);
    }

    @Test
    void deserialize_whenObjectGiven_thenIsSerialized() throws Exception {
        YamlTestable deserialized = this.sut.deserialize("field1: abc\nint1: 10\n", YamlTestable.class);

        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals("abc", deserialized.getField1());
        Assertions.assertEquals(10, deserialized.getInt1());
    }

    @Test
    void deserialize_whenUndeclaredPropertyInYaml_thenThrowsException() throws Exception {
        YamlSerializationException yamlSerializationException = Assertions.assertThrows(YamlSerializationException.class, () -> {
            YamlTestable deserialized = this.sut.deserialize("field1: abc\nint1: 10\nmissing: 10\n", YamlTestable.class);
        });

        Assertions.assertEquals("Unrecognized field \"missing\" (class ai.dqo.utils.serialization.YamlTestable), not marked as ignorable at line 3, column 12", yamlSerializationException.getMessage());
    }
}
