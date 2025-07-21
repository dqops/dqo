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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
public class AbstractElementWrapperTests extends BaseTest {
    @Test
    void getStatus_whenCalledOnNewObject_thenReturnsNotTouched() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        Assertions.assertEquals(InstanceStatus.NOT_TOUCHED, sut.getStatus());
    }

    @Test
    void setModel_whenCalledWhenNullModel_thenStoresModelAndPreservesStatus() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        TestableSpec model = new TestableSpec("model");
        sut.setSpec(model);
        Assertions.assertEquals(InstanceStatus.ADDED, sut.getStatus());
        Assertions.assertSame(model, sut.getSpec());
    }

    @Test
    void setModel_whenCalledWhenModelWasPresent_thenStoresModelAndChangesStatusToModified() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        TestableSpec oldModel = new TestableSpec("model");
        sut.setSpec(oldModel);

        TestableSpec newModel = new TestableSpec("newmodel");
        sut.setSpec(newModel);
        Assertions.assertEquals(InstanceStatus.ADDED, sut.getStatus());
        Assertions.assertSame(newModel, sut.getSpec());
    }

    @Test
    void markForDeletion_whenUnchanged_thenSetsStatusToBeDeleted() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        Assertions.assertEquals(InstanceStatus.NOT_TOUCHED, sut.getStatus());
        sut.markForDeletion();
        Assertions.assertEquals(InstanceStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenAdded_thenSetsStatusDeleted() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.ADDED);
        sut.markForDeletion();
        Assertions.assertEquals(InstanceStatus.DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenModified_thenSetsStatusToBeDeleted() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.MODIFIED);
        sut.markForDeletion();
        Assertions.assertEquals(InstanceStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenToBeDeleted_thenPreservesStatus() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.TO_BE_DELETED);
        sut.markForDeletion();
        Assertions.assertEquals(InstanceStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenDeleted_thenPreservesStatus() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.DELETED);
        sut.markForDeletion();
        Assertions.assertEquals(InstanceStatus.DELETED, sut.getStatus());
    }

    @Test
    void flush_whenAdded_thenStatusChangesToUnchanged() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.ADDED);
        sut.flush();
        Assertions.assertEquals(InstanceStatus.UNCHANGED, sut.getStatus());
    }

    @Test
    void flush_whenStatusUnchangedButModelIsDirty_thenClearsDirtyAndModifies() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setSpec(new TestableSpec("abcdef"));
        sut.getSpec().setName("newname");
        Assertions.assertTrue(sut.getSpec().isDirty());
        sut.flush();
        Assertions.assertFalse(sut.getSpec().isDirty());
        Assertions.assertEquals(InstanceStatus.UNCHANGED, sut.getStatus());
    }

    @Test
    void flush_whenModified_thenStatusChangesToUnchanged() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.MODIFIED);
        sut.flush();
        Assertions.assertEquals(InstanceStatus.UNCHANGED, sut.getStatus());
    }

    @Test
    void flush_whenToBeDeleted_thenStatusChangesToDeleted() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.markForDeletion();
        sut.flush();
        Assertions.assertEquals(InstanceStatus.DELETED, sut.getStatus());
    }

    @Test
    void setModifiedIf_whenNotModifiedAndStatusAdded_thenPreservesStatus() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.ADDED);
        sut.setModifiedIf(false);
        Assertions.assertEquals(InstanceStatus.ADDED, sut.getStatus());
    }

    @Test
    void setModifiedIf_whenModifiedAndStatusAdded_thenPreservesStatus() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.ADDED);
        sut.setModifiedIf(true);
        Assertions.assertEquals(InstanceStatus.ADDED, sut.getStatus());
    }

    @Test
    void setModifiedIf_whenNotModifiedAndStatusUnchanged_thenPreservesStatus() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.UNCHANGED);
        sut.setModifiedIf(false);
        Assertions.assertEquals(InstanceStatus.UNCHANGED, sut.getStatus());
    }

    @Test
    void setModifiedIf_whenModifiedAndStatusUnchanged_thenChangesStatusToModified() {
        TestableElementWrapper sut = new TestableElementWrapper("abc");
        sut.setStatus(InstanceStatus.UNCHANGED);
        sut.setModifiedIf(true);
        Assertions.assertEquals(InstanceStatus.MODIFIED, sut.getStatus());
    }

    public class TestableElementWrapper extends AbstractElementWrapper<String, TestableSpec> {
        private final ChildHierarchyNodeFieldMapImpl<TestableElementWrapper> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
            {
            }
        };

        private final String name;

        public TestableElementWrapper(String name) {
            super(false);
            this.name = name;
        }

        /**
         * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
         *
         * @return Object name;
         */
        @Override
        public String getObjectName() {
            return this.name;
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
            return null;
        }
    }

    public class TestableSpec extends AbstractSpec {
        private String name;

        public TestableSpec(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
			this.setDirtyIf(!Objects.equals(this.name, name));
            this.name = name;
        }

        /**
         * Returns the child map on the spec class with all fields.
         *
         * @return Return the field map.
         */
        @Override
        protected ChildHierarchyNodeFieldMap getChildMap() {
            return AbstractSpec.FIELDS;
        }

        /**
         * Calls a visitor (using a visitor design pattern) that returns a result.
         *
         * @param visitor   Visitor instance.
         * @param parameter Additional parameter that will be passed back to the visitor.
         */
        @Override
        public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
            return null;
        }
    }
}
