package org.unito.asd.prim.tests.priorityqueue;

import org.junit.Before;
import org.junit.Test;
import org.unito.asd.prim.PriorityQueue;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueStringTests {
    private PriorityQueue<String> priorityQueue;

    @Before
    public void setUp() {
        priorityQueue = new PriorityQueue<>(Comparator.<String>naturalOrder());
    }

    @Test
    public void empty() {
        assertTrue(priorityQueue.empty());
        priorityQueue.push("A");
        assertFalse(priorityQueue.empty());
        priorityQueue.pop();
        assertTrue(priorityQueue.empty());
    }

    @Test
    public void push() {
        assertTrue(priorityQueue.push("A"));
        assertTrue(priorityQueue.push("B"));
        assertFalse(priorityQueue.push("B"));
    }

    @Test
    public void contains() {
        assertFalse(priorityQueue.contains("A"));
        priorityQueue.push("A");
        assertTrue(priorityQueue.contains("A"));
        priorityQueue.pop();
        assertFalse(priorityQueue.contains("A"));
    }

    @Test
    public void top() {
        assertNull(priorityQueue.top());
        priorityQueue.push("B");
        priorityQueue.push("A");
        assertEquals("A", priorityQueue.top());
    }

    @Test
    public void pop() {
        priorityQueue.push("C");
        priorityQueue.push("A");
        priorityQueue.push("B");
        assertEquals("A", priorityQueue.top());
        priorityQueue.pop();
        assertEquals("B", priorityQueue.top());
        priorityQueue.pop();
        assertEquals("C", priorityQueue.top());
    }

    @Test
    public void remove() {
        priorityQueue.push("B");
        priorityQueue.push("A");
        priorityQueue.push("C");

        assertTrue(priorityQueue.remove("C"));
        assertFalse(priorityQueue.contains("C"));
        assertTrue(priorityQueue.remove("A"));
        assertFalse(priorityQueue.contains("A"));

        assertEquals("B", priorityQueue.top());

        assertFalse(priorityQueue.remove("D"));
    }
}
