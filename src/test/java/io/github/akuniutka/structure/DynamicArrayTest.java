package io.github.akuniutka.structure;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {
    private static final Dummy[] NULL_FIXED_ARRAY = null;
    private static final DynamicArray<Dummy> NULL_DYNAMIC_ARRAY = null;
    private static final Dummy[] EMPTY_ARRAY = new Dummy[0];
    private static final Dummy[] TEST_ARRAY = new Dummy[]{
            new Dummy(5),
            new Dummy(4),
            new Dummy(3),
            new Dummy(2),
            new Dummy(1)
    };

    //
    // Tests for dynamic array constructing
    //

    @Test
    @SuppressWarnings("ConstantConditions")
    void testConstructingFromNullFixedArray() {
        assertThrows(NullPointerException.class, () -> new DynamicArray<>(NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testConstructingFromNullDynamicArray() {
        assertThrows(NullPointerException.class, () -> new DynamicArray<>(NULL_DYNAMIC_ARRAY));
    }

    @Test
    void testConstructingWhenInitialCapacityIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray<Dummy>(-1));
    }

    @Test
    void testConstructingWithoutParameters() {
        DynamicArray<Dummy> array = new DynamicArray<>();
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingFromEmptyFixedArray() {
        DynamicArray<Dummy> array = new DynamicArray<>(EMPTY_ARRAY);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingFromEmptyDynamicArray() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(original);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingFromNonEmptyFixedArray() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testConstructingFromNonEmptyDynamicArray() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(original);
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testConstructingWhenInitialCapacityIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(0);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingWhenInitialCapacityIsLessThanDefaultCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(1);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingWhenInitialCapacityEqualsDefaultCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(10);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testConstructingWhenInitialCapacityIsGreaterThanDefaultCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(100);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    //
    // Tests for appending elements from a fixed-length array
    //

    @Test
    void testAppendingElementsFromFixedArrayWhenEmptyAndCapacitySuffices() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertTrue(array.addAll(copyOfTestArray()));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSuffices() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertTrue(array.addAll(copyOfTestArray()));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromFixedArrayWhenNotEmptyAndCapacitySuffices() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, 2));
        assertTrue(array.addAll(copyOfTestArray(2, TEST_ARRAY.length)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSuffice() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(0, 2));
        assertTrue(array.addAll(copyOfTestArray(2, TEST_ARRAY.length)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromFixedArrayWhenCapacityIsFull() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray(0, 2));
        assertTrue(array.addAll(copyOfTestArray(2, TEST_ARRAY.length)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyFixedArrayWhenEmpty() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertFalse(array.addAll(EMPTY_ARRAY));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyFixedArrayWhenNotEmpty() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyFixedArrayWhenCapacityIsFull() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertFalse(array.addAll(EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullFixedArrayWhenEmpty() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullFixedArrayWhenNotEmpty() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullFixedArrayWhenCapacityIsFull() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_FIXED_ARRAY));
    }

    //
    // Tests for appending elements from another dynamic array
    //

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullDynamicArrayWhenEmpty() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullDynamicArrayWhenNotEmpty() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAppendingElementsFromNullDynamicArrayWhenCapacityIsFull() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(NULL_DYNAMIC_ARRAY));
    }

    @Test
    void testAppendingElementsFromEmptyDynamicArrayWhenEmpty() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertFalse(array.addAll(original));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyDynamicArrayWhenNotEmpty() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromEmptyDynamicArrayWhenCapacityIsFull() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertFalse(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenEmptyAndCapacitySuffices() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSuffices() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenNotEmptyAndCapacitySuffices() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(2, TEST_ARRAY.length));
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, 2));
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSuffice() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(2, TEST_ARRAY.length));
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(0, 2));
        assertTrue(array.addAll(original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testAppendingElementsFromDynamicArrayWhenCapacityIsFull() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(2, TEST_ARRAY.length));
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray(0, 2));
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
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 2, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_FIXED_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullFixedArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_FIXED_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 2, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, EMPTY_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(0, EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertFalse(array.addAll(0, EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(1, EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyFixedArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertFalse(array.addAll(1, EMPTY_ARRAY));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacitySufficesAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacitySufficesAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexIsGreaterThanCapacity() {
        int capacity = TEST_ARRAY.length * 2;
        DynamicArray<Dummy> array = new DynamicArray<>(capacity);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(capacity + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 2, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, copyOfTestArray()));
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, copyOfTestArray(0, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, copyOfTestArray(0, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, copyOfTestArray(0, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacitySufficesAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, copyOfTestArray(1, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, copyOfTestArray(1, 3)));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromFixedArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 2);
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
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(-1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 2, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(TEST_ARRAY.length + 1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(0, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testInsertingElementsFromNullDynamicArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(NullPointerException.class, () -> array.addAll(1, NULL_DYNAMIC_ARRAY));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 2, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexIsZero() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertFalse(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertFalse(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromEmptyDynamicArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray<Dummy> original = new DynamicArray<>();
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertFalse(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexIsNegative() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(-1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(0, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacitySufficesAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length * 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacitySufficesAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        int capacity = TEST_ARRAY.length * 2;
        DynamicArray<Dummy> array = new DynamicArray<>(capacity);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(capacity + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 2, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray());
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.addAll(TEST_ARRAY.length + 1, original));
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexIsZero() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(0, 3));
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexIsZero() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(0, 3));
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndIndexIsZero() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(0, 3));
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(0, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacitySufficesAndIndexWithinBounds() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(1, 3));
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenNotEmptyAndCapacityDoesNotSufficeAndIndexWithinBounds() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(1, 3));
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
        array.addAll(copyOfTestArray(0, 1));
        array.addAll(copyOfTestArray(3, TEST_ARRAY.length));
        assertTrue(array.addAll(1, original));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementsFromDynamicArrayWhenCapacityIsFullAndWithinBounds() {
        DynamicArray<Dummy> original = new DynamicArray<>(copyOfTestArray(1, 3));
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 2);
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
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
    }

    @Test
    void testRetrievingElementWhenEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length - 2));
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length));
    }

    @Test
    void testRetrievingElementWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(1));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length - 1));
    }

    @Test
    void testRetrievingElementWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length + 1));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length + 1));
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(TEST_ARRAY.length + 1));
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexEqualsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        int index = 0;
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length - 2, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, TEST_ARRAY.length - 2), array.toArray());
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexEqualsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        int index = 0;
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testRetrievingElementWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        int index = 1;
        assertEquals(TEST_ARRAY[index], array.get(index));
        assertEquals(TEST_ARRAY.length - 2, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, TEST_ARRAY.length - 2), array.toArray());
    }

    @Test
    void testRetrievingElementWhenCapacityIsFullAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
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
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(0, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(1, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length + 1, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length + 1, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length + 2, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(TEST_ARRAY.length + 1, new Dummy(0)));
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexEqualsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(0, TEST_ARRAY.length - 2));
        Dummy[] expected = copyOfTestArray(0, TEST_ARRAY.length - 2);
        int index = 0;
        Dummy newElement = new Dummy(expected[index].getValue() + 1);
        expected[index] = newElement;
        assertEquals(TEST_ARRAY[index], array.set(index, newElement));
        assertEquals(newElement, array.get(index));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexEqualsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        Dummy[] expected = copyOfTestArray();
        int index = 0;
        Dummy newElement = new Dummy(expected[index].getValue() + 1);
        expected[index] = newElement;
        assertEquals(TEST_ARRAY[index], array.set(index, newElement));
        assertEquals(newElement, array.get(index));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testReplacingElementWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        Dummy[] expected = copyOfTestArray();
        int index = 1;
        Dummy newElement = new Dummy(expected[index].getValue() + 1);
        expected[index] = newElement;
        assertEquals(TEST_ARRAY[index], array.set(index, newElement));
        assertEquals(newElement, array.get(index));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testReplacingElementWhenCapacityIsFullAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        Dummy[] expected = copyOfTestArray();
        int index = 1;
        Dummy newElement = new Dummy(expected[index].getValue() + 1);
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
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertDoesNotThrow(() -> array.add(TEST_ARRAY[0]));
        assertEquals(TEST_ARRAY[0], array.get(0));
        assertEquals(1, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, 1), array.toArray());
    }

    @Test
    void testAppendingElementWhenEmptyAndCapacityDoesNotSuffice() {
        DynamicArray<Dummy> array = new DynamicArray<>(0);
        assertDoesNotThrow(() -> array.add(TEST_ARRAY[0]));
        assertEquals(TEST_ARRAY[0], array.get(0));
        assertEquals(1, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(0, 1), array.toArray());
    }

    @Test
    void testAppendingElementWhenNotEmpty() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
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
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray(0, index));
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
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenEmptyAndCapacityDoesNotSufficeAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(0);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenEmptyAndCapacitySufficesAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(0, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenEmptyAndCapacityDoesNotSufficeAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(0);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(0, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(1, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(TEST_ARRAY.length, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(1, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(2, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(TEST_ARRAY.length + 1, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.add(TEST_ARRAY[0]);
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(TEST_ARRAY.length + 1, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(TEST_ARRAY.length + 10, new Dummy(0)));
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexEqualsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        array.addAll(copyOfTestArray(1, TEST_ARRAY.length));
        assertDoesNotThrow(() -> array.add(0, TEST_ARRAY[0]));
        assertEquals(TEST_ARRAY[0], array.get(0));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementWhenCapacityIsFullAndIndexEqualsZero() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray(1, TEST_ARRAY.length));
        assertDoesNotThrow(() -> array.add(0, TEST_ARRAY[0]));
        assertEquals(TEST_ARRAY[0], array.get(0));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testInsertingElementWhenNotEmptyAndIndexWithinBounds() {
        int index = 1;
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
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
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length - 1);
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
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexIsNegative() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
    }

    @Test
    void testRemovingElementWhenEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length));
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexEqualsSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length));
    }

    @Test
    void testRemovingElementWhenEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(1));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexIsGreaterThanSize() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length + 1));
    }

    @Test
    void testRemovingElementWhenEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length);
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length + 1));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length + 2));
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexIsGreaterThanCapacity() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(TEST_ARRAY.length + 1));
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexEqualsZeroAndItIsNotLastElement() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertEquals(TEST_ARRAY[0], array.remove(0));
        assertEquals(TEST_ARRAY.length - 1, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(1, TEST_ARRAY.length), array.toArray());
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexEqualsZeroAndItIsNotLastElement() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertEquals(TEST_ARRAY[0], array.remove(0));
        assertEquals(TEST_ARRAY.length - 1, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(copyOfTestArray(1, TEST_ARRAY.length), array.toArray());
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexEqualsZeroAndItIsLastElement() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.add(TEST_ARRAY[0]);
        assertEquals(TEST_ARRAY[0], array.remove(0));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexEqualsZeroAndItIsLastElement() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray(0, 1));
        assertEquals(TEST_ARRAY[0], array.remove(0));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testRemovingElementWhenNotEmptyAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 2);
        array.addAll(copyOfTestArray());
        int index = 1;
        Dummy element = new Dummy(100);
        array.add(index, element);
        assertEquals(element, array.remove(index));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testRemovingElementWhenCapacityIsFullAndIndexWithinBounds() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        int index = 1;
        Dummy element = new Dummy(100);
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
        DynamicArray<Dummy> array = new DynamicArray<>();
        assertDoesNotThrow(array::clear);
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testClearingWhenNotEmpty() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertDoesNotThrow(array::clear);
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testClearingWhenCapacityIsFull() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertDoesNotThrow(array::clear);
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    //
    // Tests for sorting elements
    //

    @Test
    void testSortingWhenEmptyAndComparatorIsNull() {
        DynamicArray<Dummy> array = new DynamicArray<>();
        assertDoesNotThrow(() -> array.sort(null));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testSortingWhenEmptyAndComparatorIsNotNull() {
        DynamicArray<Dummy> array = new DynamicArray<>();
        assertDoesNotThrow(() -> array.sort(Comparator.comparingInt(Dummy::getValue).reversed()));
        assertEquals(EMPTY_ARRAY.length, array.size());
        assertTrue(array.isEmpty());
        assertArrayEquals(EMPTY_ARRAY, array.toArray());
    }

    @Test
    void testSortingWhenNotEmptyAndUnsortedAndComparatorIsNull() {
        Dummy[] expected = copyOfTestArray();
        Arrays.sort(expected);
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertDoesNotThrow(() -> array.sort(null));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testSortingWhenNotEmptyAndUnsortedAndComparatorIsNotNull() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        Dummy[] unsorted = copyOfTestArray();
        Arrays.sort(unsorted);
        array.addAll(unsorted);
        assertDoesNotThrow(() -> array.sort(Comparator.comparingInt(Dummy::getValue).reversed()));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testSortingWhenNotEmptyAndSortedAndComparatorIsNull() {
        Dummy[] expected = copyOfTestArray();
        Arrays.sort(expected);
        Dummy[] actual = copyOfTestArray();
        Arrays.sort(actual);
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(actual);
        assertDoesNotThrow(() -> array.sort(null));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testSortingWhenNotEmptyAndSortedAndComparatorIsNotNull() {
        DynamicArray<Dummy> array = new DynamicArray<>(TEST_ARRAY.length + 1);
        array.addAll(copyOfTestArray());
        assertDoesNotThrow(() -> array.sort(Comparator.comparingInt(Dummy::getValue).reversed()));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testSortingWhenCapacityIsFullAndUnsortedAndComparatorIsNull() {
        Dummy[] expected = copyOfTestArray();
        Arrays.sort(expected);
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertDoesNotThrow(() -> array.sort(null));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testSortingWhenCapacityIsFullAndUnsortedAndComparatorIsNotNull() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        Dummy[] unsorted = copyOfTestArray();
        Arrays.sort(unsorted);
        assertDoesNotThrow(() -> array.sort(Comparator.comparingInt(Dummy::getValue).reversed()));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    @Test
    void testSortingWhenCapacityIsFullAndSortedAndComparatorIsNull() {
        Dummy[] expected = copyOfTestArray();
        Arrays.sort(expected);
        Dummy[] actual = copyOfTestArray();
        Arrays.sort(actual);
        DynamicArray<Dummy> array = new DynamicArray<>(actual);
        assertDoesNotThrow(() -> array.sort(null));
        assertEquals(expected.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(expected, array.toArray());
    }

    @Test
    void testSortingWhenCapacityIsFullAndSortedAndComparatorIsNotNull() {
        DynamicArray<Dummy> array = new DynamicArray<>(copyOfTestArray());
        assertDoesNotThrow(() -> array.sort(Comparator.comparingInt(Dummy::getValue).reversed()));
        assertEquals(TEST_ARRAY.length, array.size());
        assertFalse(array.isEmpty());
        assertArrayEquals(TEST_ARRAY, array.toArray());
    }

    //
    // Supplementary methods
    //

    private Dummy[] copyOfTestArray() {
        return copyOfTestArray(0, TEST_ARRAY.length);
    }

    private Dummy[] copyOfTestArray(int from, int to) {
        return Arrays.copyOfRange(TEST_ARRAY, from, to);
    }

    private static class Dummy implements Comparable<Dummy> {
        final int value;

        Dummy(int value) {
            this.value = value;
        }

        Integer getValue() {
            return value;
        }

        @Override
        public int compareTo(Dummy dummy) {
            return Integer.compare(this.value, dummy.value);
        }
    }
}
