package io.github.akuniutka.structure;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {
    private static final int[] NULL_FIXED_ARRAY = null;
    private static final DynamicArray NULL_DYNAMIC_ARRAY = null;
    private static final int[] EMPTY_ARRAY = new int[0];
    private static final int[] TEST_ARRAY = new int[]{5, 4, 3, 2, 1};

    //
    // Tests for dynamic array constructing
    //

    @Test
    @SuppressWarnings("ConstantConditions")
    void testConstructingFromNullFixedArray() {
        assertThrows(NullPointerException.class, () -> new DynamicArray(NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testConstructingFromNullDynamicArray() {
        assertThrows(NullPointerException.class, () -> new DynamicArray(NULL_DYNAMIC_ARRAY));
    }

    @Test
    void testConstructingWhenInitialCapacityIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray(-1));
    }

    @Test
    void testConstructingWithoutParameters() {
        DynamicArray array = new DynamicArray();
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingFromEmptyFixedArray() {
        DynamicArray array = new DynamicArray(EMPTY_ARRAY);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingFromEmptyDynamicArray() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(original);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingFromNonEmptyFixedArray() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testConstructingFromNonEmptyDynamicArray() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(original);
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testConstructingWhenInitialCapacityIsZero() {
        DynamicArray array = new DynamicArray(0);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingWhenInitialCapacityIsLessThanDefaultCapacity() {
        DynamicArray array = new DynamicArray(1);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingWhenInitialCapacityEqualsDefaultCapacity() {
        DynamicArray array = new DynamicArray(10);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingWhenInitialCapacityIsGreaterThanDefaultCapacity() {
        DynamicArray array = new DynamicArray(100);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    //
    // Tests for appending elements from a fixed-length array
    //

    @Test
    void testAppendingElementsFromFixedArrayWhenEmptyAndCapacitySuffices() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertTrue(array.addAll(copyOfTestArray()));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSuffices() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertTrue(array.addAll(copyOfTestArray()));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromFixedArrayWhenNotEmptyAndCapacitySuffices() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, 2));
        assertTrue(array.addAll(copyOfTestArray(2, TEST_ARRAY.length)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSuffice() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(0, 2));
        assertTrue(array.addAll(copyOfTestArray(2, TEST_ARRAY.length)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromFixedArrayWhenCapacityIsFull() {
        DynamicArray array = new DynamicArray(copyOfTestArray(0, 2));
        assertTrue(array.addAll(copyOfTestArray(2, TEST_ARRAY.length)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyFixedArrayWhenEmpty() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertFalse(array.addAll(EMPTY_ARRAY));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyFixedArrayWhenNotEmpty() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyFixedArrayWhenCapacityIsFull() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertFalse(array.addAll(EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullFixedArrayWhenEmpty() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullFixedArrayWhenNotEmpty() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullFixedArrayWhenCapacityIsFull() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_FIXED_ARRAY));
    }

    //
    // Tests for appending elements from another dynamic array
    //

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullDynamicArrayWhenEmpty() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullDynamicArrayWhenNotEmpty() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullDynamicArrayWhenCapacityIsFull() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_DYNAMIC_ARRAY));
    }

    @Test
    void testAppendingElementsFromEmptyDynamicArrayWhenEmpty() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertFalse(array.addAll(original));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyDynamicArrayWhenNotEmpty() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyDynamicArrayWhenCapacityIsFull() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertFalse(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenEmptyAndCapacitySuffices() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSuffices() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenNotEmptyAndCapacitySuffices() {
        DynamicArray original = new DynamicArray(copyOfTestArray(2, TEST_ARRAY.length));
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, 2));
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSuffice() {
        DynamicArray original = new DynamicArray(copyOfTestArray(2, TEST_ARRAY.length));
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(0, 2));
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenCapacityIsFull() {
        DynamicArray original = new DynamicArray(copyOfTestArray(2, TEST_ARRAY.length));
        DynamicArray array = new DynamicArray(copyOfTestArray(0, 2));
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    //
    // Tests for inserting elements from a fixed-length array
    //

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 2, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexIsZero() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_FIXED_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 2, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexIsZero() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(0, EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertFalse(array.addAll(0, EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(1, EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertFalse(array.addAll(1, EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacitySufficesAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacitySufficesAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexIsGreaterThanCapacity() {
        int capacity = TEST_ARRAY.length * 2;
        DynamicArray array = new DynamicArray(capacity);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(capacity + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 2, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexIsZero() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, copyOfTestArray(0, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsZero() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, copyOfTestArray(0, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray array = new DynamicArray(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, copyOfTestArray(0, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, copyOfTestArray(1, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, copyOfTestArray(1, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 2);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, copyOfTestArray(1, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    //
    // Tests for inserting elements from another dynamic array
    //

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 2, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexIsZero() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenEmptyAndIndexIsNegative() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexIsNegative() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenEmptyAndIndexEqualsSize() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 2, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexIsZero() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertFalse(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray original = new DynamicArray();
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertFalse(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacitySufficesAndIndexIsGreaterThanSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexIsGreaterThanSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanSize() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacitySufficesAndIndexIsGreaterThanCapacity() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanCapacity() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexIsGreaterThanCapacity() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        int capacity = TEST_ARRAY.length * 2;
        DynamicArray array = new DynamicArray(capacity);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(capacity + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanCapacity() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 2, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray original = new DynamicArray(copyOfTestArray());
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexIsZero() {
        DynamicArray original = new DynamicArray(copyOfTestArray(0, 3));
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsZero() {
        DynamicArray original = new DynamicArray(copyOfTestArray(0, 3));
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray original = new DynamicArray(copyOfTestArray(0, 3));
        DynamicArray array = new DynamicArray(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexWithinBounds() {
        DynamicArray original = new DynamicArray(copyOfTestArray(1, 3));
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexWithinBounds() {
        DynamicArray original = new DynamicArray(copyOfTestArray(1, 3));
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray original = new DynamicArray(copyOfTestArray(1, 3));
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 2);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    //
    // Tests for retrieving an element
    //

    @Test
    void testRetrievingElementWhenEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
    }

    @Test
    void testRetrievingElementWhenEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length - 2));
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length));
    }

    @Test
    void testRetrievingElementWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(1));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length - 1));
    }

    @Test
    void testRetrievingElementWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length + 1));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length + 1));
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length + 1));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexEqualsZero() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        int index = 0;
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length - 2, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, TEST_ARRAY.length - 2), array.toArray());
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexEqualsZero() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        int index = 0;
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        int index = 1;
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length - 2, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, TEST_ARRAY.length - 2), array.toArray());
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        int index = 1;
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    //
    // Tests for replacing the specified element with a new one
    //

    @Test
    void testReplacingElementWhenEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, 0));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, 0));
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, 0));
    }

    @Test
    void testReplacingElementWhenEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(0, 0));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length, 0));
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length, 0));
    }

    @Test
    void testReplacingElementWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(1, 0));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length + 1, 0));
    }

    @Test
    void testReplacingElementWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length + 1, 0));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length + 2, 0));
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length + 1, 0));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexEqualsZero() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        int[] expected = copyOfTestArray(0, TEST_ARRAY.length - 2);
        int index = 0;
        int newElement = expected[index] + 1;
        expected[index] = newElement;
        assertEquals(TEST_ARRAY[index], array.set(index, newElement));
        assertEquals(newElement, array.get(index));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexEqualsZero() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        int[] expected = copyOfTestArray();
        int index = 0;
        int newElement = expected[index] + 1;
        expected[index] = newElement;
        assertEquals(TEST_ARRAY[index], array.set(index, newElement));
        assertEquals(newElement, array.get(index));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        int[] expected = copyOfTestArray(0, TEST_ARRAY.length - 2);
        int index = 1;
        int newElement = expected[index] + 1;
        expected[index] = newElement;
        assertEquals(TEST_ARRAY[index], array.set(index, newElement));
        assertEquals(newElement, array.get(index));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexWithinBounds() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        int[] expected = copyOfTestArray();
        int index = 1;
        int newElement = expected[index] + 1;
        expected[index] = newElement;
        assertEquals(TEST_ARRAY[index], array.set(index, newElement));
        assertEquals(newElement, array.get(index));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    //
    // Tests for appending a single element
    //

    @Test
    void testAppendingElementWhenEmptyAndCapacitySuffices() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertDoesNotThrow(() -> array.add(TEST_ARRAY[0]));
        assertEquals(TEST_ARRAY[0], array.get(0));
        assertEquals(1, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, 1), array.toArray());
    }

    @Test
    void testAppendingElementWhenEmptyAndCapacityDoesNotSuffice() {
        DynamicArray array = new DynamicArray(0);
        assertDoesNotThrow(() -> array.add(TEST_ARRAY[0]));
        assertEquals(TEST_ARRAY[0], array.get(0));
        assertEquals(1, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, 1), array.toArray());
    }

    @Test
    void testAppendingElementWhenNotEmpty() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertDoesNotThrow(() -> array.add(TEST_ARRAY[1]));
        assertEquals(TEST_ARRAY[1], array.get(1));
        assertEquals(2, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, 2), array.toArray());
    }

    @Test
    void testAppendingElementWhenCapacityIsFull() {
        int index = TEST_ARRAY.length - 1;
        DynamicArray array = new DynamicArray(copyOfTestArray(0, index));
        assertDoesNotThrow(() -> array.add(TEST_ARRAY[index]));
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(), array.toArray());
    }

    //
    // Tests for inserting a single element
    //

    @Test
    void testInsertingElementWhenEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, 0));
    }

    @Test
    void testInsertingElementWhenEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(0);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, 0));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, 0));
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, 0));
    }

    @Test
    void testInsertingElementWhenEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(0, 0));
    }

    @Test
    void testInsertingElementWhenEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(0);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(0, 0));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(1, 0));
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(TEST_ARRAY.length, 0));
    }

    @Test
    void testInsertingElementWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(1, 0));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(2, 0));
    }

    @Test
    void testInsertingElementWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(TEST_ARRAY.length + 1, 0));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(TEST_ARRAY.length + 1, 0));
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(TEST_ARRAY.length + 10, 0));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexEqualsZero() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(1, TEST_ARRAY.length));
        assertDoesNotThrow(() -> array.add(0, TEST_ARRAY[0]));
        assertEquals(TEST_ARRAY[0], array.get(0));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexEqualsZero() {
        DynamicArray array = new DynamicArray(copyOfTestArray(1, TEST_ARRAY.length));
        assertDoesNotThrow(() -> array.add(0, TEST_ARRAY[0]));
        assertEquals(TEST_ARRAY[0], array.get(0));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexWithinBounds() {
        int index = 1;
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        for (int i = 0; i < TEST_ARRAY.length; ++i) {
            if (i != index) {
                array.add(TEST_ARRAY[i]);
            }
        }
        assertDoesNotThrow(() -> array.add(index, TEST_ARRAY[index]));
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexWithinBounds() {
        int index = 1;
        DynamicArray array = new DynamicArray(TEST_ARRAY.length - 1);
        for (int i = 0; i < TEST_ARRAY.length; ++i) {
            if (i != index) {
                array.add(TEST_ARRAY[i]);
            }
        }
        assertDoesNotThrow(() -> array.add(index, TEST_ARRAY[index]));
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    //
    // Tests for removing the specified element
    //

    @Test
    void testRemovingElementWhenEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
    }

    @Test
    void testRemovingElementWhenEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length));
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length));
    }

    @Test
    void testRemovingElementWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(1));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length + 1));
    }

    @Test
    void testRemovingElementWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length + 1));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length + 2));
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length + 1));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexEqualsZeroAndItIsNotLastElement() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertEquals(TEST_ARRAY[0], array.remove(0));
        assertEquals(TEST_ARRAY.length - 1, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(1, TEST_ARRAY.length), array.toArray());
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexEqualsZeroAndItIsNotLastElement() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertEquals(TEST_ARRAY[0], array.remove(0));
        assertEquals(TEST_ARRAY.length - 1, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(1, TEST_ARRAY.length), array.toArray());
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexEqualsZeroAndItIsLastElement() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.add(TEST_ARRAY[0]);
        assertEquals(TEST_ARRAY[0], array.remove(0));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexEqualsZeroAndItIsLastElement() {
        DynamicArray array = new DynamicArray(copyOfTestArray(0, 1));
        assertEquals(TEST_ARRAY[0], array.remove(0));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexWithinBoundsAndItIsNotLastElement() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        int index = 1;
        int element = 100;
        array.add(index, element);
        assertEquals(element, array.remove(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexWithinBoundsAndItIsNotLastElement() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        int index = 1;
        int element = 100;
        array.add(index, element);
        assertEquals(element, array.remove(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    //
    // Tests for removing all elements at once
    //

    @Test
    void testClearingWhenEmpty() {
        DynamicArray array = new DynamicArray();
        assertDoesNotThrow(array::clear);
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testClearingWhenNotEmpty() {
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertDoesNotThrow(array::clear);
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testClearingWhenCapacityIsFull() {
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertDoesNotThrow(array::clear);
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    //
    // Tests for sorting elements
    //

    @Test
    void testSortingWhenEmpty() {
        DynamicArray array = new DynamicArray();
        assertDoesNotThrow(array::sort);
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testSortingWhenNotEmptyAndUnsorted() {
        int[] expected = copyOfTestArray();
        Arrays.sort(expected);
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertDoesNotThrow(array::sort);
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testSortingWhenNotEmptyAndSorted() {
        int[] expected = copyOfTestArray();
        Arrays.sort(expected);
        int[] actual = copyOfTestArray();
        Arrays.sort(actual);
        DynamicArray array = new DynamicArray(TEST_ARRAY.length + 1);
        array.addAll(actual);
        assertDoesNotThrow(array::sort);
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testSortingWhenCapacityIsFullAndUnsorted() {
        int[] expected = copyOfTestArray();
        Arrays.sort(expected);
        DynamicArray array = new DynamicArray(copyOfTestArray());
        assertDoesNotThrow(array::sort);
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testSortingWhenCapacityIsFullAndSorted() {
        int[] expected = copyOfTestArray();
        Arrays.sort(expected);
        int[] actual = copyOfTestArray();
        Arrays.sort(actual);
        DynamicArray array = new DynamicArray(actual);
        assertDoesNotThrow(array::sort);
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }
    
    //
    // Supplementary methods
    //

    private int[] copyOfTestArray() {
        return Arrays.copyOf(TEST_ARRAY, TEST_ARRAY.length);
    }

    private int[] copyOfTestArray(int from, int to) {
        return Arrays.copyOfRange(TEST_ARRAY, from, to);
    }
}
