package dev.akuniutka.tree;

import java.util.*;

public class BinaryTreeSet<E> implements Iterable<E> {
    // TODO: add toArray() method
    // TODO: remove null value checks
    // TODO: rebalance tree after an element insertion/removal (once for removeAll()/ addAll())

    private Node root;
    private int size;
    private final Comparator<? super E> comparator;

    private class Node {
        Node left;
        Node right;
        E value;

        Node(E value) {
            this.value = value;
        }

        // TODO: remove structure output, leave just value or (null)
        @Override
        public String toString() {
            return "+- " + value;
        }

        String toString(String prefix) {
            StringBuilder buffer = new StringBuilder();
            buffer.append(prefix, 0, prefix.length() - 1);
            buffer.append(this).append("\n");
            if (left != null) {
                buffer.append(left.toString(prefix + "  |"));
            }
            if (right != null) {
                buffer.append(right.toString(prefix + "   "));
            }
            return buffer.toString();
        }
    }

    private class TreeIterator implements Iterator<E> {
        // TODO: replace a list with an array
        private final List<E> values = new ArrayList<>();
        private int cursor;

        TreeIterator() {
            collectValues(root);
        }

        @Override
        public boolean hasNext() {
            return cursor < values.size();
        }

        @Override
        public E next() {
            return values.get(cursor++);
        }

        private void collectValues(Node node) {
            if (node != null) {
                collectValues(node.left);
                values.add(node.value);
                collectValues(node.right);
            }
        }
    }

    public BinaryTreeSet() {
        this(null);
    }

    public BinaryTreeSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }


    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public boolean contains(E value) {
        Node node = root;
        while (node != null) {
            if (compare(node.value, value) < 0) {
                node = node.right;
            } else if (compare(node.value, value) > 0) {
                node = node.left;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(Collection<E> values) {
        boolean containsAll = true;
        for (E value : values) {
            if (!contains(value)) {
                containsAll = false;
            }
        }
        return containsAll;
    }

    public E findMin() {
        return findMin(root);
    }

    public E findMax() {
        return findMax(root);
    }

    public Iterator<E> iterator() {
        return new TreeIterator();
    }

    public boolean add(E value) {
        if (value == null) {
            return false;
        } else if (root == null) {
            root = new Node(value);
            size++;
            return true;
        } else {
            Node node = root, previous = node;
            while (node != null) {
                previous = node;
                if (compare(previous.value, value) < 0) {
                    node = previous.right;
                } else if (compare(previous.value, value) > 0) {
                    node = previous.left;
                } else {
                    return false;
                }
            }
            if (compare(previous.value, value) < 0) {
                previous.right = new Node(value);
            } else {
                previous.left = new Node(value);
            }
            size++;
            return true;
        }
    }

    public boolean addAll(Collection<E> values) {
        boolean hasTreeChanged = false;
        for (E value : values) {
            if (add(value)) {
                hasTreeChanged = true;
            }
        }
        return hasTreeChanged;
    }

    public boolean remove(E value) {
        Node node = root, parent = null;
        boolean isLeftChild = true;
        while (node != null) {
            if (compare(node.value, value) < 0) {
                parent = node;
                node = node.right;
                isLeftChild = false;
            } else if (compare(node.value, value) > 0) {
                parent = node;
                node = node.left;
                isLeftChild = true;
            } else {
                removeNode(parent, node, isLeftChild);
                size--;
                return true;
            }
        }
        return false;
    }

    public boolean removeAll(Collection<E> values) {
        boolean hasTreeChanged = false;
        for (E value : values) {
            if (remove(value)) {
                hasTreeChanged = true;
            }
        }
        return hasTreeChanged;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    // TODO: gather tree structure output from nodes here
    // TODO: add check for recursion (if it is possible)
    // TODO: make a special case for empty tree (<no elements> or <empty>)
    // TODO: convert to standard toString() for sets
    // Box Drawing characters: ─│┌┐└┘├┤┬┴┼╭╮╯╰
    @Override
    public String toString() {
        if (root == null) {
            return "null";
        } else {
            return root.toString(" ");
        }
    }

    public String toPrettyString() {
        StringBuilder buffer = new StringBuilder();
        if (root == null) {
            buffer.append(root);
        } else {
            buffer.append(nodeString(root, false, ""));
        }
        return buffer.toString();
    }

    private String nodeString(Node node, boolean isLeftChild, String prefix) {
        StringBuilder buffer = new StringBuilder();
        if (node.left != null) {
            if (isLeftChild || node == root) {
                buffer.append(nodeString(node.left, true, prefix + "  "));
            } else {
                buffer.append(nodeString(node.left, true, prefix + "│ "));
            }
        }
        if (node != root) {
            buffer.append(prefix);
            buffer.append(isLeftChild ? '╭' : '╰');
        } else {
            buffer.append(prefix);
            buffer.append('─');
        }
        buffer.append('─');
        buffer.append(nodeCharacter(node)).append(' ').append(node.value).append('\n');
        if (node.right != null) {
            if (isLeftChild && node != root) {
                buffer.append(nodeString(node.right, false, prefix + "│ "));
            } else {
                buffer.append(nodeString(node.right, false, prefix + "  "));
            }
        }
        return buffer.toString();
    }

    private char nodeCharacter(Node node) {
        return node.left == null ? (node.right == null ? '─' : '┬') : (node.right == null ? '┴' : '┼');
    }

    @SuppressWarnings("unchecked")
    private int compare(E o1, E o2) {
        if (comparator != null) {
            return comparator.compare(o1, o2);
        } else {
            return ((Comparable<? super E>) o1).compareTo(o2);
        }
    }

    private E findMin(Node startingNode) {
        Node node = startingNode;
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }

    private E findMax(Node startingNode) {
        Node node = startingNode;
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node.value;
    }

    private void removeNode(Node parent, Node node, boolean isLeftChild) {
        if (node.left == null) {
            if (parent == null) {
                root = node.right;
            } else if (isLeftChild) {
                parent.left = node.right;
            } else {
                parent.right = node.right;
            }
        } else if (node.right == null) {
            if (parent == null) {
                root = node.left;
            } else if (isLeftChild) {
                parent.left = node.left;
            } else {
                parent.right = node.left;
            }
        } else {
            node.value = popMinValueFromRightSubtreeOf(node);
        }
    }

    private E popMinValueFromRightSubtreeOf(Node subtreeRoot) {
        Node node = subtreeRoot.right;
        if (node.left == null) {
            subtreeRoot.right = node.right;
            return node.value;
        }
        while (node.left.left != null) {
            node = node.left;
        }
        E value = node.left.value;
        // TODO: replace far call "node.left.right" with a method of Node
        node.left = node.left.right;
        return value;
    }
}
