package io.github.akuniutka.structure;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A sample implementation of dynamic array structure. A dynamic array
 * benefits from {@code O(1)} access to its elements by element's index
 * as a fixed-length array does. But a dynamic array also adjusts its
 * capacity to the number of elements, while a fixed-length array cannot
 * contain more elements than it was specified at the fixed-length arrays
 * creation. This implementation keeps its capacity not less than {@code
 * initialCapacity} which is 10 by default.
 *
 * @author Andrei Kuniutka
 * @version 1.0
 * @since 1.0
 */
public class DynamicArray<E> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private final int initialCapacity;
    private Object[] elements;
    private int capacity;
    private int size;

    /**
     * Creates an empty dynamic array with an initial capacity of 10.
     */
    public DynamicArray() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Creates a dynamic array containing elements from the specified
     * fixed-length array in the same order as they are in the fixed-length
     * array.
     *
     * @param elements fixed-length array containing elements to be placed
     *                 into the dynamic array
     * @throws NullPointerException if the specified fixed-length array is
     *                              null
     */
    public DynamicArray(E[] elements) {
        this(elements.length);
        addAll(elements);
    }

    /**
     * Creates a new dynamic array containing elements from the specified
     * dynamic array in the same order as they are in the original dynamic
     * array.
     *
     * @param elements dynamic array containing elements to be placed
     *                 into the newly created dynamic array
     * @throws NullPointerException if the specified dynamic array is null
     */
    public DynamicArray(DynamicArray<? extends E> elements) {
        this(elements.toArray());
    }

    /**
     * Creates an empty dynamic array with the specified initial capacity.
     *
     * @param initialCapacity initial capacity of the dynamic array being
     *                        created
     * @throws IllegalArgumentException if initial capacity is negative
     */
    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }
        this.initialCapacity = initialCapacity;
        elements = new Object[initialCapacity];
        capacity = initialCapacity;
    }

    /**
     * Returns the element at the specified position in the dynamic array.
     *
     * @param index index of the element to return
     * @return the element at the specified position in the dynamic array
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    public E get(int index) {
        checkIndexWithinRange(index);
        return elements(index);
    }

    /**
     * Replaces the element at the specified position in the dynamic array
     * with the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    public E set(int index, E element) {
        checkIndexWithinRange(index);
        E oldElement = elements(index);
        elements[index] = element;
        return oldElement;
    }

    /**
     * Appends the specified element to the end of the dynamic array.
     *
     * @param element element to be appended to the dynamic array
     */
    public void add(E element) {
        if (size == capacity) {
            increaseCapacity();
        }
        elements[size++] = element;
    }

    /**
     * inserts the specified element at the specified position in the
     * dynamic array.
     *
     * @param index   index at which the specified element is to be
     *                inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    public void add(int index, E element) {
        checkIndexWithinRange(index);
        if (size == capacity) {
            increaseCapacity();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Appends elements from the specified fixed-length array to the
     * end of the dynamic array.
     *
     * @param elements fixed-length array containing elements to be
     *                 appended to the dynamic array
     * @return {@code true} if the dynamic array changed as the result
     * of the call
     * @throws NullPointerException if the specified fixed-length array
     *                              is null
     */
    public boolean addAll(E[] elements) {
        for (E element : elements) {
            add(element);
        }
        return elements.length != 0;
    }

    /**
     * Appends elements from the specified dynamic array to the end of
     * this dynamic array.
     *
     * @param elements dynamic array containing elements to be
     *                 appended to this dynamic array
     * @return {@code true} if this dynamic array changed as the result
     * of the call
     * @throws NullPointerException if the specified dynamic array is
     *                              null
     */
    public boolean addAll(DynamicArray<? extends E> elements) {
        return addAll(elements.toArray());
    }

    /**
     * Inserts elements of the specified fixed-length array into the
     * dynamic array at the specified position. Shifts the element
     * which is currently at the specified position and all subsequent
     * elements to the right (increases their indices). The new elements
     * are placed in the dynamic array in the same order as they are in
     * the fixed-length array.
     *
     * @param index    index at which the new elements are to be
     *                 inserted
     * @param elements fixed-length array containing elements to be
     *                 inserted
     * @return {@code true} if the dynamic array changed as the result
     * of the call
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     * @throws NullPointerException      if the specified fixed-length
     *                                   array is null
     */
    public boolean addAll(int index, E[] elements) {
        int n = elements.length;
        checkIndexWithinRange(index);
        for (int i = 0; i < n; i++) {
            add(index + i, elements[i]);
        }
        return n != 0;
    }

    /**
     * Inserts elements of the specified dynamic array into this
     * dynamic array at the specified position. Shifts the element
     * which is currently at the specified position and all subsequent
     * elements to the right (increases their indices). The new elements
     * are placed in the dynamic array in the same order as they are in
     * the original dynamic array.
     *
     * @param index    index at which the new elements are to be
     *                 inserted
     * @param elements dynamic array containing elements to be
     *                 inserted
     * @return {@code true} if the dynamic array changed as the result
     * of the call
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     * @throws NullPointerException      if the specified dynamic array
     *                                   is null
     */
    public boolean addAll(int index, DynamicArray<? extends E> elements) {
        return addAll(index, elements.toArray());
    }

    /**
     * Removes the element at the specified position from the dynamic
     * array.
     *
     * @param index index of the element to remove
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    public E remove(int index) {
        checkIndexWithinRange(index);
        E oldElement = elements(index);
        size--;
        if (size > index) {
            System.arraycopy(elements, index + 1, elements, index, size - index);
        }
        decreaseCapacityIfAppropriate();
        return oldElement;
    }

    /**
     * Removes all elements from the dynamic array.
     */
    public void clear() {
        size = 0;
        decreaseCapacityIfAppropriate();
    }

    /**
     * Returns the number of elements in the dynamic array.
     *
     * @return the number of elements in the dynamic array.
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if the dynamic array contains no elements.
     *
     * @return {@code true} if the dynamic array contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Sorts elements according to the order induced by the specified
     * {@code comparator}. If the specified comparator is {@code null}
     * then elements must implement {@code Comparable} interface and
     * the natural ordering is used.
     *
     * @param comparator comparator used to compare elements
     */
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> comparator) {
        Arrays.sort((E[]) elements, 0, size, comparator);
    }

    /**
     * Returns a fixed-length array containing all elements of the
     * dynamic array in proper order (from the first element to the
     * last element).
     *
     * @return a fixed length array containing all elements of the
     * dynamic array in proper order
     */
    @SuppressWarnings("unchecked")
    public E[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(elements, 0, result, 0, size);
        return (E[]) result;
    }

    private void checkIndexWithinRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @SuppressWarnings("unchecked")
    private E elements(int index) {
        return (E) elements[index];
    }

    private void increaseCapacity() {
        adjustCapacity(Math.max(1, capacity * 2));
    }

    private void decreaseCapacityIfAppropriate() {
        if (size == 0 && capacity > initialCapacity) {
            adjustCapacity(initialCapacity);
        } else if (size >= initialCapacity && size * 4 == capacity) {
            adjustCapacity(capacity / 2);
        }
    }

    private void adjustCapacity(int newCapacity) {
        Object[] newStorage = new Object[newCapacity];
        System.arraycopy(elements, 0, newStorage, 0, size);
        elements = newStorage;
        capacity = newCapacity;
    }
}
