package org.unito.asd.prim.tests.graph;

import org.junit.Before;
import org.junit.Test;
import org.unito.asd.prim.Edge;
import org.unito.asd.prim.Graph;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
public class LabelledUndirectedGraphStringTests {
    private Graph<String, String> graph;

    @Before
    public void setUp() {
        graph = new Graph<>(false, true);
    }

    @Test
    public void isDirected() {
        assertFalse(graph.isDirected());
    }

    @Test
    public void isLabelled() {
        assertTrue(graph.isLabelled());
    }

    @Test
    public void addNode() {
        assertEquals(0, graph.numEdges());
        assertThrows(NullPointerException.class, () -> graph.addNode(null));
        assertTrue(graph.addNode("A"));
        assertTrue(graph.addNode("B"));
        assertFalse(graph.addNode("A"));
        assertEquals(2, graph.numNodes());
    }

    @Test
    public void addEdge() {
        graph.addNode("A");
        graph.addNode("B");

        assertTrue(graph.addEdge("A", "B", "AtoB"));
        assertFalse(graph.addEdge("B", "A", "BtoA")); // Because it's undirected.
        assertFalse(graph.addEdge("A", "B", "another_AtoB"));
        assertEquals(2, graph.numEdges());
        assertThrows(NullPointerException.class, () -> graph.addEdge("A", "A", null));
    }

    @Test
    public void containsNode() {
        assertFalse(graph.containsNode("A"));

        graph.addNode("A");
        graph.addNode("B");

        assertTrue(graph.containsNode("A"));
        assertTrue(graph.containsNode("B"));
    }

    @Test
    public void containsEdge() {
        assertThrows(NullPointerException.class, () -> graph.containsEdge(null, "A"));
        assertThrows(NullPointerException.class, () -> graph.containsEdge("A", null));
        assertFalse(graph.containsEdge("A", "B"));

        graph.addNode("A");
        graph.addNode("B");

        graph.addEdge("A", "B", "AtoB");

        assertTrue(graph.containsEdge("A", "B"));
        assertTrue(graph.containsEdge("B", "A")); // Because it's undirected.
    }

    @Test
    public void removeNode() {
        assertThrows(NullPointerException.class, () -> graph.removeNode(null));
        assertFalse(graph.removeNode("A"));

        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A", "B", "AtoB");

        assertTrue(graph.removeNode("A"));
        assertFalse(graph.containsEdge("A", "B"));
        assertFalse(graph.containsEdge("B", "A"));
        assertTrue(graph.removeNode("B"));
        assertEquals(0, graph.numNodes());
        assertEquals(0, graph.numEdges());
    }

    @Test
    public void removeEdge() {
        assertThrows(NullPointerException.class, () -> graph.removeEdge(null, "A"));
        assertThrows(NullPointerException.class, () -> graph.removeEdge("A", null));
        assertFalse(graph.removeNode("A"));

        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A", "B", "AtoB");

        assertTrue(graph.removeEdge("A", "B"));
        assertFalse(graph.removeEdge("B", "A")); // Because it's undirected.
    }

    @Test
    public void numNodes() {
        assertEquals(0, graph.numNodes());
        graph.addNode("A");
        graph.addNode("B");

        assertEquals(2, graph.numNodes());
        graph.addNode("B");
        assertEquals(2, graph.numNodes());
    }

    @Test
    public void numEdges() {
        assertEquals(0, graph.numEdges());
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A", "B", "AtoB");
        assertEquals(2, graph.numEdges()); // Because it's undirected.
        graph.removeEdge("A", "B");
        assertEquals(0, graph.numEdges()); // Because it's undirected.
    }

    @Test
    public void getNodes() {
        assertEquals(0, graph.getNodes().size());
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        assertEquals(graph.numNodes(), graph.getNodes().size());
        assertTrue(graph.getNodes().contains("A"));
        assertTrue(graph.getNodes().contains("B"));
        assertTrue(graph.getNodes().contains("C"));
    }

    @Test
    public void getEdges() {
        assertEquals(0, graph.getEdges().size());
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B", "AtoB");
        graph.addEdge("B", "C", "BtoC");

        assertEquals(graph.numEdges(), graph.getEdges().size());

        var edge = new Edge<>("A", "B", null);
        assertTrue(graph.getEdges().contains(edge));

        edge = new Edge<>("B", "A", null);
        assertTrue(graph.getEdges().contains(edge)); // Because it's undirected.

        edge = new Edge<>("B", "C", null);
        assertTrue(graph.getEdges().contains(edge));
    }

    @Test
    public void getNeighbours() {
        assertThrows(IllegalStateException.class, () -> graph.getNeighbours("A"));
        assertThrows(NullPointerException.class, () -> graph.getNeighbours(null));

        graph.addNode("A");
        assertEquals(0, graph.getNeighbours("A").size());

        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B", "AtoB");
        graph.addEdge("A", "C", "AtoC");

        assertEquals(2, graph.getNeighbours("A").size());
        assertTrue(graph.getNeighbours("A").contains("B"));
        assertTrue(graph.getNeighbours("A").contains("C"));
        assertFalse(graph.getNeighbours("A").contains("A"));
    }

    @Test
    public void getLabel() {
        assertThrows(NullPointerException.class, () -> graph.getLabel(null, "A"));
        assertThrows(NullPointerException.class, () -> graph.getLabel("A", null));
        assertDoesNotThrow(() -> graph.getLabel("A", "B"));
        assertNull(graph.getLabel("A", "B"));

        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A", "B", "BtoA");
        assertEquals("BtoA", graph.getLabel("A", "B"));
    }
}
