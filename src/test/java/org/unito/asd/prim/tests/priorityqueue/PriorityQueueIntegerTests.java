package org.unito.asd.prim.tests.priorityqueue;

import org.junit.Before;
import org.junit.Test;
import org.unito.asd.prim.PriorityQueue;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueIntegerTests {
    private PriorityQueue<Integer> priorityQueue;

    @Before
    public void setUp() {
        priorityQueue = new PriorityQueue<>(Comparator.<Integer>naturalOrder());
    }

    @Test
    public void empty() {
        assertTrue(priorityQueue.empty());
        priorityQueue.push(1);
        assertFalse(priorityQueue.empty());
        priorityQueue.pop();
        assertTrue(priorityQueue.empty());
    }

    @Test
    public void push() {
        assertTrue(priorityQueue.push(1));
        assertTrue(priorityQueue.push(2));
        assertFalse(priorityQueue.push(2));
    }

    @Test
    public void contains() {
        assertFalse(priorityQueue.contains(10));
        priorityQueue.push(10);
        assertTrue(priorityQueue.contains(10));
        priorityQueue.pop();
        assertFalse(priorityQueue.contains(10));
    }

    @Test
    public void top() {
        assertNull(priorityQueue.top());
        priorityQueue.push(7);
        priorityQueue.push(3);
        assertEquals(Integer.valueOf(3), priorityQueue.top());
    }

    @Test
    public void pop() {
        priorityQueue.push(8);
        priorityQueue.push(2);
        priorityQueue.push(5);
        assertEquals(Integer.valueOf(2), priorityQueue.top());
        priorityQueue.pop();
        assertEquals(Integer.valueOf(5), priorityQueue.top());
        priorityQueue.pop();
        assertEquals(Integer.valueOf(8), priorityQueue.top());
    }

    @Test
    public void remove() {
        priorityQueue.push(5);
        priorityQueue.push(3);
        priorityQueue.push(8);

        assertTrue(priorityQueue.remove(8));
        assertFalse(priorityQueue.contains(8));
        assertTrue(priorityQueue.remove(3));
        assertFalse(priorityQueue.contains(3));

        assertEquals(Integer.valueOf(5), priorityQueue.top());

        assertFalse(priorityQueue.remove(10));
    }

}
