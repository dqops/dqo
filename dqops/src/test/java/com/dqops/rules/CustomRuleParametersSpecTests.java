/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rules;

import com.dqops.BaseTest;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomRuleParametersSpecTests extends BaseTest {
    private CustomRuleParametersSpec sut;
    private YamlSerializer yamlSerializer;

    @BeforeEach
    void setUp() {
        this.sut = new CustomRuleParametersSpec();
        this.yamlSerializer = YamlSerializerObjectMother.getDefault();
    }

    @Test
    void getParameter_whenParameterNotGiven_thenReturnsNull() {
        Assertions.assertNull(this.sut.getParameter("param"));
    }

    @Test
    void getParameter_whenParameterWasSet_thenReturnsValue() {
        this.sut.setParameter("param", "x");
        Assertions.assertEquals("x", this.sut.getParameter("param"));
    }

    @Test
    void setParameter_whenCalledWithValueTwice_thenSetsSensorValue() {
        this.sut.setParameter("param", 2);
        this.sut.setParameter("param", 4);
        Assertions.assertEquals(4, this.sut.getParameter("param"));
    }

    @Test
    void setParameter_whenCalledWithNullValueAndParameterHadValue_thenRemovesValue() {
        this.sut.setParameter("param", 2);
        this.sut.setParameter("param", null);
        Assertions.assertEquals(null, this.sut.getParameter("param"));
        Assertions.assertEquals(0, this.sut.getAdditionalProperties().size());
    }

    @Test
    void deserializeCustomRule_whenPropertiesGiven_thenTheyAreVisibleAsAdditionalProperties() {
        String yaml = "field_1: abc\nfield_2: 20";
        CustomRuleParametersSpec deserialized = this.yamlSerializer.deserialize(yaml, CustomRuleParametersSpec.class);

        Assertions.assertEquals(2, deserialized.getAdditionalProperties().size());
        Assertions.assertEquals("abc", deserialized.getAdditionalProperties().get("field_1"));
        Assertions.assertEquals(20, deserialized.getAdditionalProperties().get("field_2"));
    }

    @Test
    void serializeCustomRule_whenAdditionalPropertiesKnown_thenValuesAreSerialized() {
        this.sut.setParameter("field_1", "abc");
        this.sut.setParameter("field_2", 30);

        String serialized = this.yamlSerializer.serialize(this.sut).replace("\r\n", "\n");
        Assertions.assertEquals("# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/CustomRuleParametersSpec-schema.json\nfield_1: abc\nfield_2: 30\n", serialized);
    }
}
