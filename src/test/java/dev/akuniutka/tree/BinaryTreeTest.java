package dev.akuniutka.tree;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {

    @Test
    void testIsEmptyIfJustCreated() {
        BinaryTree tree = new BinaryTree();
        assertTrue(tree.isEmpty());
    }

    @Test
    void testIsEmptyIfElementAdded() {
        BinaryTree tree = new BinaryTree();
        tree.add(10);
        assertFalse(tree.isEmpty());
    }

    @Test
    void testIsEmptyIfLastElementRemoved() {
        BinaryTree tree = new BinaryTree();
        tree.add(10);
        tree.remove(10);
        assertTrue(tree.isEmpty());
    }

    @Test
    void testSizeIfJustCreated() {
        BinaryTree tree = new BinaryTree();
        assertEquals(0, tree.size());
    }

    @Test
    void testSizeIfNewElementsAdded() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertEquals(3, tree.size());
    }

    @Test
    void testSizeIfExistingElementsAdded() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        tree.addAll(Arrays.asList(30, 40, 50));
        assertEquals(5, tree.size());
    }

    @Test
    void testSizeIfElementsRemoved() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        tree.removeAll(Arrays.asList(30, 40, 50));
        assertEquals(2, tree.size());
    }

    @Test
    void testAddIfNewElement() {
        BinaryTree tree = new BinaryTree();
        assertTrue(tree.add(10));
    }

    @Test
    void testAddIfElementAlreadyExists() {
        BinaryTree tree = new BinaryTree();
        tree.add(10);
        assertFalse(tree.add(10));
    }

    @Test
    void testAddAll() {
        BinaryTree tree = new BinaryTree();
        assertTrue(tree.addAll(Arrays.asList(10, 20, 30)));
    }

    @Test
    void testContainsIfElementExists() {
        BinaryTree tree = new BinaryTree();
        tree.add(10);
        assertTrue(tree.contains(10));
    }

    @Test
    void testContainsIfElementDoesNotExists() {
        BinaryTree tree = new BinaryTree();
        tree.add(10);
        assertFalse(tree.contains(20));
    }

    @Test
    void testContainsAllIfAllElementsExist() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertTrue(tree.containsAll(Arrays.asList(10, 20)));
    }

    @Test
    void testContainsAllIfSomeElementsDoNotExist() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20));
        assertFalse(tree.containsAll(Arrays.asList(10, 20, 30)));
    }

    @Test
    void testRemoveIfElementExists() {
        BinaryTree tree = new BinaryTree();
        tree.add(10);
        assertTrue(tree.remove(10));
    }

    @Test
    void testRemoveIfElementDoesNotExists() {
        BinaryTree tree = new BinaryTree();
        tree.add(10);
        assertFalse(tree.remove(20));
    }

    @Test
    void testRemoveAllIfSomeElementsExist() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertTrue(tree.removeAll(Arrays.asList(10, 40, 50)));
    }

    @Test
    void testRemoveAllIfNoElementsExist() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertFalse(tree.removeAll(Arrays.asList(40, 50, 60)));
    }

    @Test
    void testClear() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        tree.clear();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

    @Test
    void testFindMin() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertEquals(10, tree.findMin());
    }

    @Test
    void testFindMax() {
        BinaryTree tree = new BinaryTree();
        tree.addAll(Arrays.asList(10, 20, 30));
        assertEquals(30, tree.findMax());
    }

    @Test
    void testIterator() {
        BinaryTree tree = new BinaryTree();
        List<Integer> values = Arrays.asList(10, 20, 30);
        int index = 0;
        tree.addAll(values);
        for(Integer value : tree) {
            assertEquals(values.get(index++), value);
        }
    }

    @Disabled("Temporary disabled")
    @Test
    void testToString() {
        BinaryTree tree = new BinaryTree();
        String expected =
                "- 40\n" +
                "  +- 30\n" +
                "  |  +- 10\n" +
                "  |  |  +- null\n" +
                "  |  |  +- null\n" +
                "  |  +- 20\n" +
                "  |     +- null\n" +
                "  |     +- null\n" +
                "  +- 60\n" +
                "     +- 50\n" +
                "     |  +- null\n" +
                "     |  +- null\n" +
                "     +- 70\n" +
                "        +- null\n" +
                "        +- null\n";
        tree.addAll(Arrays.asList(40, 30, 60, 10, 20, 50, 70));
        assertEquals(expected, tree.toString());
    }

}