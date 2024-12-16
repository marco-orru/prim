package org.unito.asd.prim.tests.priorityqueue;

import org.junit.Before;
import org.junit.Test;
import org.unito.asd.prim.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

public class PriorityQueueFloatTests {
    private PriorityQueue<Float> priorityQueue;

    @Before
    public void setUp() {
        priorityQueue = new PriorityQueue<>(Comparator.<Float>naturalOrder());
    }

    @Test
    public void empty() {
        assertTrue(priorityQueue.empty());
        priorityQueue.push(1.5f);
        assertFalse(priorityQueue.empty());
        priorityQueue.pop();
        assertTrue(priorityQueue.empty());
    }

    @Test
    public void push() {
        assertTrue(priorityQueue.push(1.5f));
        assertTrue(priorityQueue.push(2.5f));
        assertFalse(priorityQueue.push(2.5f));
    }

    @Test
    public void contains() {
        assertFalse(priorityQueue.contains(10.5f));
        priorityQueue.push(10.5f);
        assertTrue(priorityQueue.contains(10.5f));
        priorityQueue.pop();
        assertFalse(priorityQueue.contains(10.5f));
    }

    @Test
    public void top() {
        assertNull(priorityQueue.top());
        priorityQueue.push(7.5f);
        priorityQueue.push(3.5f);
        assertEquals(3.5f, priorityQueue.top());
    }

    @Test
    public void pop() {
        priorityQueue.push(8.5f);
        priorityQueue.push(2.5f);
        priorityQueue.push(5.5f);
        assertEquals(2.5f, priorityQueue.top());
        priorityQueue.pop();
        assertEquals(5.5f, priorityQueue.top());
        priorityQueue.pop();
        assertEquals(8.5f, priorityQueue.top());
    }

    @Test
    public void remove() {
        priorityQueue.push(5.5f);
        priorityQueue.push(3.5f);
        priorityQueue.push(8.5f);

        assertTrue(priorityQueue.remove(8.5f));
        assertFalse(priorityQueue.contains(8.5f));
        assertTrue(priorityQueue.remove(3.5f));
        assertFalse(priorityQueue.contains(3.5f));

        assertEquals(5.5f, priorityQueue.top());

        assertFalse(priorityQueue.remove(10.5f));
    }

}
