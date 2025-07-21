/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.basespecs;

import com.dqops.BaseTest;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
public class AbstractSpecTests extends BaseTest {
    private AbstractSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new TableSpec();
    }

    @Test
    void isDirty_whenStart_thenIsFalse() {
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void clearDirty_whenClearDone_thenIsDirtyIsFalse() {
		this.sut.setDirty();
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void setDirtyIf_whenTruValueDone_thenIsDirtyIsTrue() {
		this.sut.setDirtyIf(false);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setDirtyIf(true);
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDefault_whenClassHasFieldsAllNull_thenIsDefault() {
        TestableAbstractSpec sut = new TestableAbstractSpec();
        Assertions.assertTrue(sut.isDefault());
    }

    @Test
    void isDefault_whenClassHasFieldsStringNotNull_thenIsDefaultReturnsFalse() {
        TestableAbstractSpec sut = new TestableAbstractSpec();
        sut.setStringField("not empty");
        Assertions.assertFalse(sut.isDefault());
    }

    @Test
    void isDefault_whenClassHasChildClassThatIsNotNullButIsDefaultEmpty_thenIsDefaultReturnsTrue() {
        TestableAbstractSpec sut = new TestableAbstractSpec();
        TestableAbstractSpec child = new TestableAbstractSpec();
        sut.setChild(child);
        Assertions.assertTrue(sut.isDefault());
    }

    @Test
    void isDefault_whenClassHasChildClassThatIsNotNullAndIsNotEmpty_thenIsDefaultReturnsFalse() {
        TestableAbstractSpec sut = new TestableAbstractSpec();
        TestableAbstractSpec child = new TestableAbstractSpec();
        child.setStringField("child value");
        sut.setChild(child);
        Assertions.assertFalse(sut.isDefault());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @EqualsAndHashCode(callSuper = true)
    public static class TestableAbstractSpec extends AbstractSpec {
        public static final ChildHierarchyNodeFieldMapImpl<TestableAbstractSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
            {
                put("child", o -> o.child);
            }
        };

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
        private TestableAbstractSpec child;


        public TestableAbstractSpec getChild() {
            return child;
        }

        public void setChild(TestableAbstractSpec child) {
            this.setDirtyIf(!Objects.equals(this.child, child));
            this.child = child;
            propagateHierarchyIdToField(child, "child");
        }

        private String stringField;

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.setDirtyIf(!Objects.equals(this.stringField, stringField));
            this.stringField = stringField;
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
         * @return Result value returned by an "accept" method of the visitor.
         */
        @Override
        public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
            return null;
        }
    }
}
