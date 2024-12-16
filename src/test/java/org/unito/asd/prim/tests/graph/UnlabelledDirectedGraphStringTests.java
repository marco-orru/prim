package org.unito.asd.prim.tests.graph;

import org.junit.Before;
import org.junit.Test;
import org.unito.asd.prim.Edge;
import org.unito.asd.prim.Graph;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
public class UnlabelledDirectedGraphStringTests {
    private Graph<String, String> graph;

    @Before
    public void setUp() {
        graph = new Graph<>(true, false);
    }

    @Test
    public void isDirected() {
        assertTrue(graph.isDirected());
    }

    @Test
    public void isLabelled() {
        assertFalse(graph.isLabelled());
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
        assertTrue(graph.addEdge("B", "A", "BtoA"));
        assertFalse(graph.addEdge("A", "B", "another_AtoB"));
        assertEquals(2, graph.numEdges());
        assertDoesNotThrow(() -> graph.addEdge("A", "A", null));
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
        assertFalse(graph.containsEdge("A", "B"));

        graph.addNode("A");
        graph.addNode("B");

        graph.addEdge("A", "B", "AtoB");

        assertTrue(graph.containsEdge("A", "B"));
        assertFalse(graph.containsEdge("B", "A"));
    }

    @Test
    public void removeNode() {
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
        assertThrows(NullPointerException.class, () -> graph.removeEdge("A", null));
        assertFalse(graph.removeNode("A"));

        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A", "B", "AtoB");

        assertTrue(graph.removeEdge("A", "B"));
        assertFalse(graph.removeEdge("B", "A"));
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
        assertEquals(1, graph.numEdges());
        graph.removeEdge("A", "B");
        assertEquals(0, graph.numEdges());
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
        assertFalse(graph.getEdges().contains(edge));

        edge = new Edge<>("B", "C", null);
        assertTrue(graph.getEdges().contains(edge));
    }

    @Test
    public void getNeighbours() {
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
        assertThrows(IllegalStateException.class, () -> graph.getLabel("A", "B"));
    }
}
