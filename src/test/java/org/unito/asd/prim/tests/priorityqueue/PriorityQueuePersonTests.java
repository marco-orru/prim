package org.unito.asd.prim.tests.priorityqueue;

import org.junit.Before;
import org.junit.Test;
import org.unito.asd.prim.PriorityQueue;
import org.unito.asd.prim.tests.Person;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueuePersonTests {
    private PriorityQueue<Person> priorityQueue;

    @Before
    public void setUp() {
        priorityQueue = new PriorityQueue<>(Comparator
                .comparing(Person::surname)
                .thenComparing(Person::name)
                .thenComparing(Person::age));
    }

    @Test
    public void empty() {
        assertTrue(priorityQueue.empty());
        priorityQueue.push(new Person("John", "Doe", 30));
        assertFalse(priorityQueue.empty());
        priorityQueue.pop();
        assertTrue(priorityQueue.empty());
    }

    @Test
    public void push() {
        assertTrue(priorityQueue.push(new Person("John", "Doe", 30)));
        assertTrue(priorityQueue.push(new Person("Michael", "Scott", 10)));
        assertTrue(priorityQueue.push(new Person("Michael", "Scott", 12)));
        assertFalse(priorityQueue.push(new Person("Michael", "Scott", 10)));
    }

    @Test
    public void contains() {
        assertFalse(priorityQueue.contains(new Person("John", "Doe", 30)));
        priorityQueue.push(new Person("John", "Doe", 30));
        assertTrue(priorityQueue.contains(new Person("John", "Doe", 30)));
        priorityQueue.pop();
        assertFalse(priorityQueue.contains(new Person("John", "Doe", 30)));
    }

    @Test
    public void top() {
        assertNull(priorityQueue.top());
        priorityQueue.push(new Person("John", "Doe", 30));
        priorityQueue.push(new Person("Michael", "Scott", 10));
        assertEquals(new Person("John", "Doe", 30), priorityQueue.top());
    }

    @Test
    public void pop() {
        priorityQueue.push(new Person("John", "Doe", 30));
        priorityQueue.push(new Person("Michael", "Scott", 30));
        priorityQueue.push(new Person("Walter", "White", 50));
        assertEquals(new Person("John", "Doe", 30), priorityQueue.top());
        priorityQueue.pop();
        assertEquals(new Person("Michael", "Scott", 30), priorityQueue.top());
        priorityQueue.pop();
        assertEquals(new Person("Walter", "White", 50), priorityQueue.top());
    }

    @Test
    public void remove() {
        priorityQueue.push(new Person("John", "Doe", 30));
        priorityQueue.push(new Person("Michael", "Scott", 30));
        priorityQueue.push(new Person("Walter", "White", 52));

        assertTrue(priorityQueue.remove(new Person("Michael", "Scott", 30)));
        assertFalse(priorityQueue.contains(new Person("Michael", "Scott", 30)));
        assertTrue(priorityQueue.remove(new Person("Walter", "White", 52)));
        assertFalse(priorityQueue.contains(new Person("Walter", "White", 52)));

        assertEquals(new Person("John", "Doe", 30), priorityQueue.top());

        assertFalse(priorityQueue.remove(new Person("Walter", "White", 50)));
    }

}
