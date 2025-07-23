/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.serialization;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoLoggingUserErrorsConfigurationProperties;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.logging.UserErrorLoggerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class YamlSerializerImplTests extends BaseTest {
    private YamlSerializerImpl sut;
    private DqoConfigurationProperties configurationProperties;

    @BeforeEach
    void setUp() {
		configurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        UserErrorLoggerImpl userErrorLogger = new UserErrorLoggerImpl(new DqoLoggingUserErrorsConfigurationProperties());
        this.sut = new YamlSerializerImpl(configurationProperties, userErrorLogger);
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
    void deserialize_whenYamlGivenAndObjectImplementsDeserializableAware_thenObjectIsNotifiedOfDeserialization() {
        YamlTestable deserialized = this.sut.deserialize("field1: abc\nint1: 10\n", YamlTestable.class);
        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals("abc", deserialized.getField1());
        Assertions.assertEquals(10, deserialized.getInt1());
        Assertions.assertTrue(deserialized.wasOnDeserializedCalled);
    }

    @Test
    void deserialize_whenUndeclaredPropertyInYaml_thenIgnoresIssue() {
        YamlTestable deserialized = this.sut.deserialize("field1: abc\nint1: 10\nmissing: 10\n", YamlTestable.class);
        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals("abc", deserialized.getField1());
        Assertions.assertEquals(10, deserialized.getInt1());
    }

    @Test
    void deserialize_whenUndeclaredPropertyInObjectExtendingAbstractSpec_thenIgnoredPropertiesAddedToMap() {
        TableSpec deserialized = this.sut.deserialize("missing_field1: abc\nmissing_field2: 10\n", TableSpec.class);
        Assertions.assertNotNull(deserialized);
        Assertions.assertNotNull(deserialized.getAdditionalProperties());
        Assertions.assertEquals(2, deserialized.getAdditionalProperties().size());
        Assertions.assertTrue(deserialized.getAdditionalProperties().containsKey("missing_field1"));
        Assertions.assertTrue(deserialized.getAdditionalProperties().containsKey("missing_field2"));
    }
}
