package dev.akuniutka.tree;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeSetTest {

    @Test
    void testIsEmptyIfJustCreated() {
        BinaryTreeSet tree = new BinaryTreeSet();
        assertTrue(tree.isEmpty());
    }

    @Test
    void testIsEmptyIfElementAdded() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.add(10);
        assertFalse(tree.isEmpty());
    }

    @Test
    void testIsEmptyIfLastElementRemoved() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.add(10);
        tree.remove(10);
        assertTrue(tree.isEmpty());
    }

    @Test
    void testSizeIfJustCreated() {
        BinaryTreeSet tree = new BinaryTreeSet();
        assertEquals(0, tree.size());
    }

    @Test
    void testSizeIfNewElementsAdded() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertEquals(3, tree.size());
    }

    @Test
    void testSizeIfExistingElementsAdded() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        tree.addAll(Arrays.asList(30, 40, 50));
        assertEquals(5, tree.size());
    }

    @Test
    void testSizeIfElementsRemoved() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        tree.removeAll(Arrays.asList(30, 40, 50));
        assertEquals(2, tree.size());
    }

    @Test
    void testAddIfNewElement() {
        BinaryTreeSet tree = new BinaryTreeSet();
        assertTrue(tree.add(10));
    }

    @Test
    void testAddIfElementAlreadyExists() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.add(10);
        assertFalse(tree.add(10));
    }

    @Test
    void testAddAll() {
        BinaryTreeSet tree = new BinaryTreeSet();
        assertTrue(tree.addAll(Arrays.asList(10, 20, 30)));
    }

    @Test
    void testContainsIfElementExists() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.add(10);
        assertTrue(tree.contains(10));
    }

    @Test
    void testContainsIfElementDoesNotExists() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.add(10);
        assertFalse(tree.contains(20));
    }

    @Test
    void testContainsAllIfAllElementsExist() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertTrue(tree.containsAll(Arrays.asList(10, 20)));
    }

    @Test
    void testContainsAllIfSomeElementsDoNotExist() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20));
        assertFalse(tree.containsAll(Arrays.asList(10, 20, 30)));
    }

    @Test
    void testRemoveIfElementExists() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.add(10);
        assertTrue(tree.remove(10));
    }

    @Test
    void testRemoveIfElementDoesNotExists() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.add(10);
        assertFalse(tree.remove(20));
    }

    @Test
    void testRemoveAllIfSomeElementsExist() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertTrue(tree.removeAll(Arrays.asList(10, 40, 50)));
    }

    @Test
    void testRemoveAllIfNoElementsExist() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertFalse(tree.removeAll(Arrays.asList(40, 50, 60)));
    }

    @Test
    void testClear() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        tree.clear();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

    @Test
    void testFindMin() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertEquals(10, tree.findMin());
    }

    @Test
    void testFindMax() {
        BinaryTreeSet tree = new BinaryTreeSet();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertEquals(30, tree.findMax());
    }

    @Test
    void testIterator() {
        BinaryTreeSet tree = new BinaryTreeSet();
        List<Integer> values = Arrays.asList(10, 20, 30);
        int index = 0;
        tree.addAll(values);
        for(Integer value : tree) {
            assertEquals(values.get(index++), value);
        }
    }

    @Test
    void toStringIfTreeIsEmpty() {
        BinaryTreeSet tree = new BinaryTreeSet();
        assertEquals("null", tree.toString());
    }

    @Test
    void testToStringIfTreeIsNotEmpty() {
        BinaryTreeSet tree = new BinaryTreeSet();
        String expected =
                "+- 40\n" +
                "   +- 20\n" +
                "   |  +- 10\n" +
                "   |  +- 30\n" +
                "   +- 60\n" +
                "      +- 50\n" +
                "      +- 70\n";
        tree.addAll(Arrays.asList(40, 20, 60, 10, 30, 50, 70));
        assertEquals(expected, tree.toString());
    }

}