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
package ai.dqo.metadata.basespecs;

import ai.dqo.BaseTest;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.*;

@SpringBootTest
public class AbstractIndexingCollectionTests extends BaseTest {
    private TestableAbstractIndexingCollection sut;

    @BeforeEach
    void setUp() {
		this.sut = new TestableAbstractIndexingCollection();
    }

    @Test
    void getByObjectName_whenElementPresentAndTrue_thenIsReturned() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.add(elem);

        Assertions.assertSame(elem, this.sut.getByObjectName("abc", true));
    }

    @Test
    void getByObjectName_whenElementPresentButRequiresLoadAndTrue_thenLoadsDataAndReturns() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.registerForLoad(elem);

        Assertions.assertSame(elem, this.sut.getByObjectName("abc", true));
    }

    @Test
    void getByObjectName_whenElementNotPresentAfterLoadAndTrue_thenLoadsButDoesNotFind() {
        TestableElementWrapper elem = new TestableElementWrapper("other");
		this.sut.registerForLoad(elem);
        Assertions.assertNotNull(this.sut.getModelsForLoad());

        Assertions.assertNull(this.sut.getByObjectName("missing", true));
        Assertions.assertNull(this.sut.getModelsForLoad());
    }

    @Test
    void getByObjectName_whenElementPresentButRequiresLoadAndSpecialImplementationLoadsOne_thenLoadsOnlyThisElement() {
        TestableAbstractIndexingCollectionIndexedLoad sut = new TestableAbstractIndexingCollectionIndexedLoad();
        TestableElementWrapper elem = new TestableElementWrapper("abc");
        sut.registerForLoad(elem);

        TestableElementWrapper result = sut.getByObjectName("abc", true);
        Assertions.assertSame(elem, result);
        Assertions.assertNotNull(sut.getModelsForLoad()); // full load not done
    }

    @Test
    void getByObjectName_whenNotLoadedAndForceLoadTrue_thenLoadsAllAndReturnsElement() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.registerForLoad(elem);
        Assertions.assertNotNull(this.sut.getModelsForLoad());

        Assertions.assertSame(elem, this.sut.getByObjectName("abc", true));
        Assertions.assertNull(this.sut.getModelsForLoad());
    }

    @Test
    void getByObjectName_whenNotLoadedAndForceLoadFalse_thenDoesNotLoadsAllAndReturnsNull() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.registerForLoad(elem);
        Assertions.assertNotNull(this.sut.getModelsForLoad());

        Assertions.assertNull(this.sut.getByObjectName("abc", false));
        Assertions.assertNotNull(this.sut.getModelsForLoad());
    }

    @Test
    void set_whenReplacingExistingItem_thenReindexesNewItemUnderNewName() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.registerForLoad(elem);
        Assertions.assertNotNull(this.sut.getModelsForLoad());

        TestableElementWrapper elem2= new TestableElementWrapper("other");
		this.sut.loadOnce();
		this.sut.set(0, elem2);

        Assertions.assertNull(this.sut.getByObjectName("abc", true));
        Assertions.assertSame(elem2, this.sut.getByObjectName("other", true));
        Object[] arr = this.sut.stream().toArray();
        Assertions.assertEquals(1, arr.length);
        Assertions.assertSame(elem2, arr[0]);
    }

    @Test
    void set_whenReplacingExistingItemButNameIsInUse_thenThrowsException() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
        TestableElementWrapper duplicate = new TestableElementWrapper("other");
		this.sut.registerForLoad(elem);
		this.sut.registerForLoad(duplicate);
        Assertions.assertNotNull(this.sut.getModelsForLoad());

        TestableElementWrapper elem2 = new TestableElementWrapper("other");
		this.sut.loadOnce();
        Assertions.assertThrows(RuntimeException.class, () -> {
			this.sut.set(0, elem2);
        });
    }

    @Test
    void get_whenIndexGiven_thenReturnsWithoutLoading() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.registerForLoad(elem);
        Assertions.assertNotNull(this.sut.getModelsForLoad());
		this.sut.addWithoutFullLoad(elem);

        Assert.assertSame(elem, this.sut.get(0));
        Assertions.assertNotNull(this.sut.getModelsForLoad());
    }

    @Test
    void size_whenCalled_thenTriggersFullLoad() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.registerForLoad(elem);

        Assert.assertEquals(1, this.sut.size());
        Assertions.assertNull(this.sut.getModelsForLoad());
    }

    @Test
    void add_whenCalled_thenPerformsFullLoadBeforeAddingToAvoidDuplicates() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.registerForLoad(elem);
        TestableElementWrapper elem2 = new TestableElementWrapper("abc2");

		this.sut.add(elem2);

        Assertions.assertNull(this.sut.getModelsForLoad()); // load done
        Assertions.assertEquals(2, this.sut.size());
    }

    @Test
    void add_whenCalledWithDuplicate_thenPerformsFullLoadAndDetectsDuplicateAndThrowsException() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.registerForLoad(elem);
        TestableElementWrapper elem2 = new TestableElementWrapper("abc");

        Assertions.assertThrows(RuntimeException.class, () -> {
			this.sut.add(elem2);
        });
    }

    @Test
    void add_whenCalled_thenDoesNotChangeTrackingStatus() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
        elem.setStatus(InstanceStatus.UNCHANGED);

		this.sut.add(elem);
        Assertions.assertEquals(InstanceStatus.UNCHANGED, elem.getStatus());
    }

    @Test
    void createAndAddNew_whenElementNotPresent_thenIsAddedAndStatusIsAdded() {
        TestableElementWrapper model = this.sut.createAndAddNew("abc");

        Assertions.assertEquals(InstanceStatus.ADDED, model.getStatus());
        Assertions.assertEquals(1, this.sut.size());
        Assertions.assertSame(model, this.sut.getByObjectName("abc", true));
        Assertions.assertSame(model, this.sut.get(0));
    }

    @Test
    void createAndAddNew_whenElementUnderKeyIsPresent_thenThrowsException() {
		this.sut.add(new TestableElementWrapper("abc"));
        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            TestableElementWrapper model = this.sut.createAndAddNew("abc");
        });
    }

    @Test
    void remove_whenElementPresent_thenIsRemovedAndReturned() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.addWithoutFullLoad(elem);

        Assertions.assertTrue(this.sut.remove(elem));
        Assertions.assertNull(this.sut.getByObjectName("abc", true));
        Assertions.assertEquals(0, this.sut.size());
        Assertions.assertTrue(this.sut.getDeleted().contains(elem));
    }

    @Test
    void remove_whenElementNotPresent_thenReturnsFalse() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
        Assertions.assertFalse(this.sut.remove(elem));
        Assertions.assertNull(this.sut.getByObjectName("abc", true));
        Assertions.assertEquals(0, this.sut.size());
        Assertions.assertFalse(this.sut.getDeleted().contains(elem));
    }

    @Test
    void remove_whenIndexGiven_thenJustRemoves() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
		this.sut.addWithoutFullLoad(elem);

        Assertions.assertSame(elem, this.sut.remove(0));
        Assertions.assertNull(this.sut.getByObjectName("abc", true));
        Assertions.assertEquals(0, this.sut.size());
        Assertions.assertTrue(this.sut.getDeleted().contains(elem));
    }

    @Test
    void iterator_whenCalled_thenLoadsAllItems() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
        TestableElementWrapper elem2 = new TestableElementWrapper("abc2");
		this.sut.registerForLoad(elem);
		this.sut.registerForLoad(elem2);

        Iterator<TestableElementWrapper> iterator = this.sut.iterator();
        Assertions.assertNotNull(iterator);
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertSame(elem, iterator.next());
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertSame(elem2, iterator.next());
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    void listIterator_whenCalled_thenLoadsAllItems() {
        TestableElementWrapper elem = new TestableElementWrapper("abc");
        TestableElementWrapper elem2 = new TestableElementWrapper("abc2");
		this.sut.registerForLoad(elem);
		this.sut.registerForLoad(elem2);

        ListIterator<TestableElementWrapper> iterator = this.sut.listIterator();
        Assertions.assertNotNull(iterator);
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertSame(elem, iterator.next());
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertSame(elem2, iterator.next());
        Assertions.assertFalse(iterator.hasNext());
    }

    public class TestableAbstractIndexingCollection extends AbstractIndexingList<String, TestableElementWrapper> {
        private List<TestableElementWrapper> modelsForLoad;

        public List<TestableElementWrapper> getModelsForLoad() {
            return modelsForLoad;
        }

        public void setModelsForLoad(List<TestableElementWrapper> modelsForLoad) {
            this.modelsForLoad = modelsForLoad;
        }

        /**
         * Loads all the elements from the backend source.
         */
        @Override
        protected void load() {
            if (this.modelsForLoad == null) {
                return;
            }

            for(TestableElementWrapper model : this.modelsForLoad) {
				this.addWithoutFullLoad(model);
            }

			this.modelsForLoad = null;
        }

       /**
         * Creates a new element given an object name. Derived classes should create a correct object type.
         *
         * @param objectName Object name.
         * @return Created and detached new instance with the object name assigned.
         */
        @Override
        protected TestableElementWrapper createNewElement(String objectName) {
            return new TestableElementWrapper(new TestableSpec(objectName));
        }

        public void registerForLoad(TestableElementWrapper elem) {
            if (this.modelsForLoad == null) {
				this.modelsForLoad = new ArrayList<>();
            }
			this.modelsForLoad.add(elem);
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

    public class TestableAbstractIndexingCollectionIndexedLoad extends TestableAbstractIndexingCollection {
        /**
         * Finds an existing object given the object name.
         *
         * @param objectName Object name.
         * @return Existing object (model wrapper) or null when the object was not found.
         */
        @Override
        public TestableElementWrapper getByObjectName(String objectName, boolean reloadWhenMissing) {
            TestableElementWrapper modelWrapper = super.getByObjectName(objectName, false);
            if (modelWrapper != null) {
                return  modelWrapper;
            }

            if (this.getModelsForLoad() != null) {
                // simulate a direct load of just one item from the persistent store
                for (TestableElementWrapper modelWrapperForLoad : this.getModelsForLoad()) {
                    if (Objects.equals(modelWrapperForLoad.getObjectName(), objectName)) {
						this.addWithoutFullLoad(modelWrapperForLoad);
                        return modelWrapperForLoad;
                    }
                }
            }

            if (reloadWhenMissing) {
				loadOnce(); // not found - make sure that we loaded all
                return super.getByObjectName(objectName, reloadWhenMissing);
            }

            return null;
        }
    }

    public class TestableElementWrapper extends AbstractElementWrapper<String, TestableSpec> {
        private final ChildHierarchyNodeFieldMapImpl<AbstractElementWrapperTests.TestableElementWrapper> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
            {
            }
        };

        public TestableElementWrapper() {
		}

        public TestableElementWrapper(TestableSpec model) {
            this();
			this.setSpec(model);
        }

        public TestableElementWrapper(String objectName) {
            this();
			this.setSpec(new TestableSpec(objectName));
        }

        /**
         * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
         *
         * @return Object name;
         */
        @Override
        public String getObjectName() {
            return this.getSpec().getName();
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

        public TestableSpec() {
        }

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
