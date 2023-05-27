package dev.akuniutka.tree;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeSetTest {

    // TODO: add output of tres structure to all tests
    // TODO: move tree structure output from base class to test class (Reflection API?)

    @Test
    void testBinaryTreeSetWithoutComparator() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        List<Integer> values = Arrays.asList(10, 20, 30);
        set.addAll(values);
        int index = 0;
        for (Integer value : set) {
            assertEquals(values.get(index++), value);
        }
    }

    @Test
    void testBinaryTreeSetWithComparator() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>(Comparator.reverseOrder());
        List<Integer> values = Arrays.asList(10, 20, 30);
        set.addAll(values);
        int index = 2;
        for (Integer value : set) {
            assertEquals(values.get(index--), value);
        }
    }

    @Test
    void testIsEmptyIfJustCreated() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        assertTrue(set.isEmpty());
    }

    @Test
    void testIsEmptyIfElementAdded() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        assertFalse(set.isEmpty());
    }

    @Test
    void testIsEmptyIfLastElementRemoved() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        set.remove(10);
        assertTrue(set.isEmpty());
    }

    @Test
    void testSizeIfJustCreated() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        assertEquals(0, set.size());
    }

    @Test
    void testSizeIfNewElementsAdded() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        assertEquals(3, set.size());
    }

    @Test
    void testSizeIfExistingElementsAdded() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        set.addAll(Arrays.asList(30, 40, 50));
        assertEquals(5, set.size());
    }

    @Test
    void testSizeIfElementsRemoved() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        set.removeAll(Arrays.asList(30, 40, 50));
        assertEquals(2, set.size());
    }

    @Test
    void testAddIfNewElement() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        assertTrue(set.add(10));
    }

    @Test
    void testAddIfElementAlreadyExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        assertFalse(set.add(10));
    }

    @Test
    void testAddAll() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        assertTrue(set.addAll(Arrays.asList(10, 20, 30)));
    }

    @Test
    void testContainsIfElementExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        assertTrue(set.contains(10));
    }

    @Test
    void testContainsIfElementDoesNotExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        assertFalse(set.contains(20));
    }

    @Test
    void testContainsAllIfAllElementsExist() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        assertTrue(set.containsAll(Arrays.asList(10, 20)));
    }

    @Test
    void testContainsAllIfSomeElementsDoNotExist() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20));
        assertFalse(set.containsAll(Arrays.asList(10, 20, 30)));
    }

    @Test
    void testRemoveIfElementExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        assertTrue(set.remove(10));
    }

    @Test
    void testRemoveIfElementDoesNotExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        assertFalse(set.remove(20));
    }

    @Test
    void testRemoveAllIfSomeElementsExist() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        assertTrue(set.removeAll(Arrays.asList(10, 40, 50)));
    }

    @Test
    void testRemoveAllIfNoElementsExist() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        assertFalse(set.removeAll(Arrays.asList(40, 50, 60)));
    }

    @Test
    void testClear() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
    }

    @Test
    void testFindMin() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        assertEquals(10, set.findMin());
    }

    @Test
    void testFindMax() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        assertEquals(30, set.findMax());
    }

    @Test
    void testIterator() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        List<Integer> values = Arrays.asList(10, 20, 30);
        int index = 0;
        set.addAll(values);
        for(Integer value : set) {
            assertEquals(values.get(index++), value);
        }
    }

    @Test
    void toStringIfTreeIsEmpty() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        assertEquals("null", set.toString());
    }

    @Test
    void testToStringIfTreeIsNotEmpty() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        String expected =
                "+- 40\n" +
                "   +- 20\n" +
                "   |  +- 10\n" +
                "   |  +- 30\n" +
                "   +- 60\n" +
                "      +- 50\n" +
                "      +- 70\n";
        set.addAll(Arrays.asList(40, 20, 60, 10, 30, 50, 70));
        assertEquals(expected, set.toString());
    }

}