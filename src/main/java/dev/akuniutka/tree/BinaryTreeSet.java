package dev.akuniutka.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BinaryTreeSet implements Iterable<Integer> {
    private Node root;
    private int size;

    // TODO: add generics support
    // TODO: implement custom comparator for generics
    // TODO: add null values
    // TODO: add counter to store duplicates

    private static class Node {
        Node left;
        Node right;
        Integer value;

        Node(Integer value) {
            this.value = value;
        }

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
            boolean isValueAdded = add(value);
            hasTreeChanged = hasTreeChanged || isValueAdded;
        }
        return hasTreeChanged;
    }

    public boolean remove(Integer value) {
        if (value == null || root == null) {
            return false;
        } else {
            Node node = root, previous = null;
            boolean isLeftChild = true;
            while (node != null) {
                if (node.value.compareTo(value) < 0) {
                    previous = node;
                    node = node.right;
                    isLeftChild = false;
                } else if (node.value.compareTo(value) > 0) {
                    previous = node;
                    node = node.left;
                    isLeftChild = true;
                } else {
                    if (node.left == null) {
                        if (previous == null) {
                            root = node.right;
                        } else if (isLeftChild) {
                            previous.left = node.right;
                        } else {
                            previous.right = node.right;
                        }
                    } else if (node.right == null) {
                        if (previous == null) {
                            root = node.left;
                        } else if (isLeftChild) {
                            previous.left = node.left;
                        } else {
                            previous.right = node.left;
                        }
                    } else {
                        Integer minValueInRightSubTree = findMin(node.right);
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
            boolean isValueRemoved = remove(value);
            hasTreeChanged = hasTreeChanged || isValueRemoved;
        }
        return hasTreeChanged;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (root == null) {
            return "null";
        } else {
            return root.toString(" ");
        }
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
