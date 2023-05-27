package dev.akuniutka.tree;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeSetTest {
    @Test
    void testBinaryTreeSetWithoutComparator() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        List<Integer> values = Arrays.asList(10, 20, 30);
        set.addAll(values);
        System.out.println(toPrettyString(set));
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
        System.out.println(toPrettyString(set));
        int index = 2;
        for (Integer value : set) {
            assertEquals(values.get(index--), value);
        }
    }

    @Test
    void testIsEmptyIfJustCreated() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        System.out.println(toPrettyString(set));
        assertTrue(set.isEmpty());
    }

    @Test
    void testIsEmptyIfElementAdded() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        System.out.println(toPrettyString(set));
        assertFalse(set.isEmpty());
    }

    @Test
    void testIsEmptyIfLastElementRemoved() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        set.remove(10);
        System.out.println(toPrettyString(set));
        assertTrue(set.isEmpty());
    }

    @Test
    void testSizeIfJustCreated() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        System.out.println(toPrettyString(set));
        assertEquals(0, set.size());
    }

    @Test
    void testSizeIfNewElementsAdded() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        System.out.println(toPrettyString(set));
        assertEquals(3, set.size());
    }

    @Test
    void testSizeIfExistingElementsAdded() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        set.addAll(Arrays.asList(30, 40, 50));
        System.out.println(toPrettyString(set));
        assertEquals(5, set.size());
    }

    @Test
    void testSizeIfElementsRemoved() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        set.removeAll(Arrays.asList(30, 40, 50));
        System.out.println(toPrettyString(set));
        assertEquals(2, set.size());
    }

    @Test
    void testAddIfNewElement() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        System.out.println(toPrettyString(set));
        assertTrue(set.add(10));
    }

    @Test
    void testAddIfElementAlreadyExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        System.out.println(toPrettyString(set));
        assertFalse(set.add(10));
    }

    @Test
    void testAddAll() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        System.out.println(toPrettyString(set));
        assertTrue(set.addAll(Arrays.asList(10, 20, 30)));
    }

    @Test
    void testContainsIfElementExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        System.out.println(toPrettyString(set));
        assertTrue(set.contains(10));
    }

    @Test
    void testContainsIfElementDoesNotExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        System.out.println(toPrettyString(set));
        assertFalse(set.contains(20));
    }

    @Test
    void testContainsAllIfAllElementsExist() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        System.out.println(toPrettyString(set));
        assertTrue(set.containsAll(Arrays.asList(10, 20)));
    }

    @Test
    void testContainsAllIfSomeElementsDoNotExist() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20));
        System.out.println(toPrettyString(set));
        assertFalse(set.containsAll(Arrays.asList(10, 20, 30)));
    }

    @Test
    void testRemoveIfElementExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        System.out.println(toPrettyString(set));
        assertTrue(set.remove(10));
    }

    @Test
    void testRemoveIfElementDoesNotExists() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(10);
        System.out.println(toPrettyString(set));
        assertFalse(set.remove(20));
    }

    @Test
    void testRemoveAllIfSomeElementsExist() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        System.out.println(toPrettyString(set));
        assertTrue(set.removeAll(Arrays.asList(10, 40, 50)));
    }

    @Test
    void testRemoveAllIfNoElementsExist() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        System.out.println(toPrettyString(set));
        assertFalse(set.removeAll(Arrays.asList(40, 50, 60)));
    }

    @Test
    void testClear() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        set.clear();
        System.out.println(toPrettyString(set));
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
    }

    @Test
    void testFindMin() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        System.out.println(toPrettyString(set));
        assertEquals(10, set.findMin());
    }

    @Test
    void testFindMax() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.addAll(Arrays.asList(10, 20, 30));
        System.out.println(toPrettyString(set));
        assertEquals(30, set.findMax());
    }

    @Test
    void testIterator() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        List<Integer> values = Arrays.asList(10, 20, 30);
        set.addAll(values);
        System.out.println(toPrettyString(set));
        int index = 0;
        for(Integer value : set) {
            assertEquals(values.get(index++), value);
        }
    }

    // TODO: add tests for toString — for empty tree and tree with elements


    private <E> String toPrettyString(BinaryTreeSet<E> set) {
        try {
            Field rootNodeField = set.getClass().getDeclaredField("root");
            rootNodeField.setAccessible(true);
            Object rootNode = rootNodeField.get(set);
            if (rootNode == null) {
                return String.valueOf((Object) null);
            }
            return toPrettyString(rootNode, NodeType.ROOT, "");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String toPrettyString(Object node, NodeType type, String prefix) throws NoSuchFieldException, IllegalAccessException {
        Object right = node.getClass().getDeclaredField("right").get(node);
        Object left = node.getClass().getDeclaredField("left").get(node);
        Object value = node.getClass().getDeclaredField("value").get(node);
        StringBuilder buffer = new StringBuilder();
        if (left != null) {
            String prefixForChild = prefix + (type == NodeType.RIGHT_CHILD ? "│ " : "  ");
            buffer.append(toPrettyString(left, NodeType.LEFT_CHILD, prefixForChild));
        }
        buffer.append(prefix);
        buffer.append(type == NodeType.ROOT ? '─' : (type == NodeType.LEFT_CHILD ? '╭' : '╰'));
        buffer.append('─');
        buffer.append(left == null ? (right == null ? '─' : '┬') : (right == null ? '┴' : '┼'));
        buffer.append(' ').append(value).append('\n');
        if (right != null) {
            String prefixForChild = prefix + (type == NodeType.LEFT_CHILD ? "│ " : "  ");
            buffer.append(toPrettyString(right, NodeType.RIGHT_CHILD, prefixForChild));
        }
        return buffer.toString();
    }

    private enum NodeType {
        ROOT,
        LEFT_CHILD,
        RIGHT_CHILD
    }
}