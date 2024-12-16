package org.unito.asd.prim;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents a generic priority queue.
 *
 * @param <E> The type of elements in the priority queue.
 * @implNote The priority queue is implemented using a max-heap.
 */
public final class PriorityQueue<E> implements AbstractQueue<E> {
    private final Comparator<E> comparator;
    private final List<E> heap;
    private final Map<E, Integer> indexMap;

    /**
     * Constructs a new {@link PriorityQueue} with the given comparator and heap
     * policy.
     * The provided comparator shall not be {@code null}.
     *
     * @param comparator The comparator used to compare elements priority in the
     *                   priority queue.
     */
    public PriorityQueue(@NotNull Comparator<E> comparator) {
        Objects.requireNonNull(comparator);

        this.comparator = comparator;
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
    }

    /**
     * Checks whether the priority queue is empty or not.
     *
     * @return {@code true} if the priority queue is empty, {@code false} otherwise.
     * @implNote This operation has constant time complexity O(1).
     */
    @Override
    @Contract(pure = true)
    public boolean empty() {
        return heap.isEmpty();
    }

    /**
     * Pushes an element onto the priority queue.
     * The element shall not be {@code null}.
     * An element is not pushed when it is already present in the priority queue.
     *
     * @param element The element to be pushed.
     * @return {@code true} if the element is successfully pushed, {@code false}
     * otherwise.
     * @implNote This operation has logarithmic time complexity O(log N).
     */
    @Override
    @Contract(mutates = "this")
    public boolean push(@NotNull E element) {
        Objects.requireNonNull(element);

        if (contains(element))
            return false;

        heap.add(element);
        indexMap.put(element, heap.size() - 1);

        heapifyUp(heap.size() - 1);
        return true;
    }

    /**
     * Checks whether a specific element is present in the priority queue or not.
     * The element shall not be {@code null}.
     *
     * @param element The element to check for existence.
     * @return {@code true} if the element is present, {@code false} otherwise.
     * @implNote This operation has constant time complexity O(1).
     */
    @Override
    @Contract(pure = true)
    public boolean contains(@NotNull E element) {
        Objects.requireNonNull(element);
        return indexMap.containsKey(element);
    }

    /**
     * Accesses the element at the top of the priority queue without removing it.
     *
     * @return The element at the top of the queue, or {@code null} if the queue is
     * empty.
     * @implNote This operation has constant time complexity O(1).
     */
    @Override
    @Contract(pure = true)
    public @Nullable E top() {
        return empty() ? null : heap.getFirst();
    }

    /**
     * Removes the element at the top of the priority queue.
     *
     * @throws NoSuchElementException If the priority queue is empty.
     * @implSpec This operation has logarithmic time complexity O(log N).
     */
    @SuppressWarnings("DataFlowIssue")
    @Override
    @Contract(mutates = "this")
    public void pop() {
        if (empty())
            throw new NoSuchElementException("Cannot pop from an empty priority queue");

        remove(top());
    }

    /**
     * Removes the specified element from the priority queue.
     * The element shall not be {@code null}.
     * An element is not removed when it is not present in the priority queue.
     *
     * @param element The element to be removed.
     * @return {@code true} if the element is successfully removed, {@code false}
     * otherwise.
     * @implNote This operation has logarithmic time complexity O(log N).
     */
    @Override
    @Contract(mutates = "this")
    public boolean remove(@NotNull E element) {
        Objects.requireNonNull(element);

        if (empty() || (indexMap.get(element) == null)) {
            return false;
        } else {
            int elementIndex = indexMap.get(element);
            int lastIndex = heap.size() - 1;

            swapNodes(elementIndex, lastIndex);
            indexMap.remove(element);
            heap.remove(lastIndex);
            heapifyDown(elementIndex);
            return true;
        }
    }

    private void heapifyDown(int elementIndex) {
        int leftIndex = (2 * elementIndex) + 1;
        int rightIndex = 2 * elementIndex + 2;
        int minChildIndex = elementIndex;

        if (leftIndex < heap.size() && comparator.compare(heap.get(leftIndex), heap.get(minChildIndex)) < 0) {
            minChildIndex = leftIndex;
        }

        if (rightIndex < heap.size() && comparator.compare(heap.get(rightIndex), heap.get(minChildIndex)) < 0) {
            minChildIndex = rightIndex;
        }

        if (minChildIndex != elementIndex) {
            swapNodes(elementIndex, minChildIndex);
            heapifyDown(minChildIndex);
        }
    }

    private int getParentIndex(int nodeIndex) {
        return (nodeIndex - 1) / 2;
    }

    private void swapNodes(int sourceIndex, int destinationIndex) {
        if (sourceIndex == destinationIndex)
            return;

        var tmp = heap.get(sourceIndex);
        heap.set(sourceIndex, heap.get(destinationIndex));
        heap.set(destinationIndex, tmp);

        indexMap.put(heap.get(sourceIndex), sourceIndex);
        indexMap.put(heap.get(destinationIndex), destinationIndex);
    }

    private int maxChildParent(int childIndex) {
        var max = childIndex;

        var parentIndex = getParentIndex(childIndex);
        if (comparator.compare(heap.get(parentIndex), heap.get(max)) > 0)
            max = parentIndex;

        return max;
    }

    private void heapifyUp(int fromIndex) {
        if (fromIndex > 0) {
            var minIndex = maxChildParent(fromIndex);

            if (minIndex != fromIndex) {
                swapNodes(fromIndex, minIndex);
                heapifyUp(minIndex);
            }
        }
    }
}
