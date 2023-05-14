package dev.akuniutka.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BinaryTree implements Iterable<Integer> {
    private Node root;
    private int size;

    // TODO: implement custom comparator for generics

    private static class Node {
        Node left;
        Node right;
        Integer value;

        Node(Integer value) {
            this.value = value;
        }
    }

    private class TreeIterator implements Iterator<Integer> {
        private final List<Integer> values = new ArrayList<>();
        private int cursor;

        TreeIterator() {
            collectValues(root);
        }

        @Override
        public boolean hasNext() {
            return cursor < values.size();
        }

        @Override
        public Integer next() {
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

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public boolean contains(Integer value) {
        Node node = root;
        while (node != null) {
            if (node.value.compareTo(value) < 0) {
                node = node.right;
            } else if (node.value.compareTo(value) > 0) {
                node = node.left;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(Collection<Integer> values) {
        boolean hasTreeChanged = true;
        for (Integer value : values) {
            hasTreeChanged = hasTreeChanged && contains(value);
        }
        return hasTreeChanged;
    }

    public Integer findMin() {
        return findMin(root);
    }

    public Integer findMax() {
        return findMax(root);
    }

    public Iterator<Integer> iterator() {
        return new TreeIterator();
    }

    public boolean add(Integer value) {
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
                if (previous.value.compareTo(value) < 0) {
                    node = previous.right;
                } else if (previous.value.compareTo(value) > 0) {
                    node = previous.left;
                } else {
                    return false;
                }
            }
            if (previous.value.compareTo(value) < 0) {
                previous.right = new Node(value);
            } else {
                previous.left = new Node(value);
            }
            size++;
            return true;
        }
    }

    public boolean addAll(Collection<Integer> values) {
        boolean hasTreeChanged = false;
        for (Integer value : values) {
            hasTreeChanged = hasTreeChanged || add(value);
        }
        return hasTreeChanged;
    }

    public boolean remove(Integer value) {
        if (value == null || root == null) {
            return false;
        } else if (root.value.compareTo(value) == 0) {
            if (root.left == null) {
                root = root.right;
            } else if (root.right == null) {
                root = root.left;
            } else {
                Integer minValueInRightSubTree = findMin(root.right);
                remove(minValueInRightSubTree);
                root.value = minValueInRightSubTree;
            }
            size--;
            return true;
        } else {
            Node node = root, previous = node;
            boolean isLeftChild = true;
            while (node != null) {
                if (node.value.compareTo(value) < 0) {
                    previous = node;
                    isLeftChild = false;
                    node = node.right;
                } else if (node.value.compareTo(value) > 0) {
                    previous = node;
                    isLeftChild = true;
                    node = node.left;
                } else {
                    if (node.left == null) {
                        if (isLeftChild) {
                            previous.left = node.right;
                        } else {
                            previous.right = node.right;
                        }
                    } else if (node.right == null) {
                        if (isLeftChild) {
                            previous.left = node.left;
                        } else {
                            previous.right = node.left;
                        }
                    } else {
                        Integer minValueInRightSubTree = findMin(node);
                        remove(minValueInRightSubTree);
                        node.value = minValueInRightSubTree;
                    }
                    size--;
                    return true;
                }
            }
            return false;
        }
    }

    public boolean removeAll(Collection<Integer> values) {
        boolean hasTreeChanged = false;
        for (Integer value : values) {
            hasTreeChanged = hasTreeChanged || remove(value);
        }
        return hasTreeChanged;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    private Integer findMin(Node startingNode) {
        Node node = startingNode;
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }

    private Integer findMax(Node startingNode) {
        Node node = startingNode;
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node.value;
    }
}
