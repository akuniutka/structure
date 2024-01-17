package io.github.akuniutka.structure;

import java.util.*;

public class BinaryTreeSet<E> implements Iterable<E> {
    // TODO: add toArray() method
    // TODO: remove null value checks
    // TODO: balance tree after an element insertion/removal (once for removeAll()/ addAll())

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

    // TODO: add check for recursion (if it is possible)
    // TODO: make a special case for empty tree (<no elements> or <empty>)
    // TODO: convert to standard toString() for sets
    @Override
    public String toString() {
        if (root == null) {
            return "null";
        } else {
            return root.toString();
        }
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
