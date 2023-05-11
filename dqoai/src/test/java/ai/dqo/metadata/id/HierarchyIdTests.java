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
package ai.dqo.metadata.id;

import ai.dqo.BaseTest;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.userhome.UserHomeImpl;
import ai.dqo.metadata.userhome.UserHomeObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HierarchyIdTests extends BaseTest {
    private HierarchyId sut;

    @BeforeEach
    void setUp() {
		this.sut = new HierarchyId("first", "second", "third");
    }

    @Test
    void constructor_whenStringsGiven_thenCreatesId() {
        HierarchyId sut = new HierarchyId("first", "second", "third");
        Assertions.assertEquals(3, sut.size());
        Assertions.assertEquals("first", sut.get(0));
        Assertions.assertEquals("second", sut.get(1));
        Assertions.assertEquals("third", sut.get(2));
    }

    @Test
    void constructor_whenParentGivenAndNewValue_thenCreatesHierarchyOneElementLonger() {
        HierarchyId child = new HierarchyId(this.sut, "fourth");
        Assertions.assertEquals(4, child.size());
        Assertions.assertEquals("first", child.get(0));
        Assertions.assertEquals("second", child.get(1));
        Assertions.assertEquals("third", child.get(2));
        Assertions.assertEquals("fourth", child.get(3));
    }

    @Test
    void getRoot_whenCalled_thenReturnsEmptyHierarchyIdSingleton() {
        Assertions.assertNotNull(HierarchyId.getRoot());
        Assertions.assertSame(HierarchyId.getRoot(), HierarchyId.getRoot());
    }

    @Test
    void getRoot_whenRetrieved_thenHasNoElements() {
        HierarchyId root = HierarchyId.getRoot();
        Assertions.assertEquals(0, root.size());
    }

    @Test
    void get_whenCalledWithIndex_thenReturnsGivenElement() {
        Assertions.assertEquals("second", this.sut.get(1));
        Assertions.assertEquals("third", this.sut.get(2));
    }

    @Test
    void size_whenCalled_thenReturnsSizeOfPath() {
        Assertions.assertEquals(3, this.sut.size());
    }

    @Test
    void getLast_whenCalled_thenReturnsLastElement() {
        Assertions.assertEquals("third", this.sut.getLast());
    }

    @Test
    void isMyDescendant_whenCandidateIsShorter_thenReturnsFalse() {
        HierarchyId candidate = new HierarchyId("first", "second");
        Assertions.assertFalse(this.sut.isMyDescendant(candidate));
    }

    @Test
    void isMyDescendant_whenCandidateIsSelf_thenReturnsFalseBecauseMustBeChild() {
        Assertions.assertFalse(this.sut.isMyDescendant(this.sut));
    }

    @Test
    void isMyDescendant_whenCandidateIsLongerButDifferentHierarchy_thenReturnsFalse() {
        HierarchyId candidate = new HierarchyId("first", "second", "OTHER", "child");
        Assertions.assertFalse(this.sut.isMyDescendant(candidate));
    }

    @Test
    void isMyDescendant_whenCandidateIsDirectChild_thenReturnsTrue() {
        HierarchyId candidate = new HierarchyId("first", "second", "third", "child");
        Assertions.assertTrue(this.sut.isMyDescendant(candidate));
    }

    @Test
    void isMyDescendant_whenCandidateIsDirectGrandChild_thenReturnsTrue() {
        HierarchyId candidate = new HierarchyId("first", "second", "third", "child", "grandchild");
        Assertions.assertTrue(this.sut.isMyDescendant(candidate));
    }

    @Test
    void equals_whenTwoEqualHierarchies_thenReturnsTrue() {
        HierarchyId other = new HierarchyId("first", "second", "third");
        Assertions.assertTrue(this.sut.equals(other));
    }

    @Test
    void equals_whenTwoDifferentHierarchies_thenReturnsTrue() {
        HierarchyId other = new HierarchyId("first", "second", "OTHER");
        Assertions.assertFalse(this.sut.equals(other));
    }

    @Test
    void hashcode_whenCalledForTwoDifferentInstancesThatAreEqual_thenReturnsTheSameCode() {
        HierarchyId other = new HierarchyId("first", "second", "third");
        Assertions.assertEquals(this.sut.hashCode(), other.hashCode());
    }

    @Test
    void hashcode_whenCalledForTwoDifferentInstancesThatAreNotEqual_thenReturnsDifferentCode() {
        HierarchyId other = new HierarchyId("first", "second", "OTHER");
        Assertions.assertNotEquals(this.sut.hashCode(), other.hashCode());
    }

    @Test
    void hashcode64_whenCalledForTwoDifferentInstancesThatAreEqual_thenReturnsTheSameCode() {
        HierarchyId other = new HierarchyId("first", "second", "third");
        Assertions.assertEquals(this.sut.hashCode64(), other.hashCode64());
    }

    @Test
    void hashCode64_whenCalledForTwoDifferentInstancesThatAreNotEqual_thenReturnsDifferentCode() {
        HierarchyId other = new HierarchyId("first", "second", "OTHER");
        Assertions.assertNotEquals(this.sut.hashCode64(), other.hashCode64());
    }

    @Test
    void hashcode64_whenCalledForKnownInput_thenReturnsKnownCode() {
        // if this test ever fails, it means that the Guava implementation of the hashing algorithm hash changed and we will have duplicates in the database at next insert
        Assertions.assertEquals(769947014711747294L, this.sut.hashCode64());
    }

    @Test
    void toString_whenCalledForMultipleElementHierarchyId_thenReturnsConcatenatedPath() {
        Assertions.assertEquals("first/second/third", this.sut.toString());
    }

    @Test
    void getNodesOnPath_whenSearchingForConnection_thenReturnsAllNodesOnPath() {
        UserHomeImpl userHome = UserHomeObjectMother.createBareUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        ConnectionSpec connectionSpec = new ConnectionSpec();
        connectionWrapper.setSpec(connectionSpec);

        HierarchyId sut = connectionSpec.getHierarchyId();
        Assertions.assertNotNull(sut);

        HierarchyNode[] nodesOnPath = sut.getNodesOnPath(userHome);
        Assertions.assertEquals(3, nodesOnPath.length);
        Assertions.assertSame(userHome.getConnections(), nodesOnPath[0]);
        Assertions.assertSame(connectionWrapper, nodesOnPath[1]);
        Assertions.assertSame(connectionSpec, nodesOnPath[2]);
    }
}
