package org.unito.asd.prim.tests.graph;

import org.junit.Before;
import org.junit.Test;
import org.unito.asd.prim.Edge;
import org.unito.asd.prim.Graph;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
public final class LabelledUndirectedGraphFloatTests {
    private Graph<Float, String> graph;

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
        assertTrue(graph.addNode(1.5f));
        assertTrue(graph.addNode(2.5f));
        assertFalse(graph.addNode(1.5f));
        assertEquals(2, graph.numNodes());
    }

    @Test
    public void addEdge() {
        graph.addNode(1.5f);
        graph.addNode(2.5f);

        assertTrue(graph.addEdge(1.5f, 2.5f, "1.5to2.5"));
        assertFalse(graph.addEdge(2.5f, 1.5f, "2.5to1.5")); // Because it's undirected.
        assertFalse(graph.addEdge(1.5f, 2.5f, "another_1.5to2.5"));
        assertEquals(2, graph.numEdges());
        assertThrows(NullPointerException.class, () -> graph.addEdge(0.0f, 0.0f, null));
    }

    @Test
    public void containsNode() {
        assertFalse(graph.containsNode(1.5f));

        graph.addNode(1.5f);
        graph.addNode(2.5f);

        assertTrue(graph.containsNode(1.5f));
        assertTrue(graph.containsNode(2.5f));
    }

    @Test
    public void containsEdge() {
        assertThrows(NullPointerException.class, () -> graph.containsEdge(null, 0.0f));
        assertThrows(NullPointerException.class, () -> graph.containsEdge(0.0f, null));
        assertFalse(graph.containsEdge(1.5f, 2.5f));

        graph.addNode(1.5f);
        graph.addNode(2.5f);

        graph.addEdge(1.5f, 2.5f, "1.5to2.5");

        assertTrue(graph.containsEdge(1.5f, 2.5f));
        assertTrue(graph.containsEdge(2.5f, 1.5f)); // Because it's undirected.
    }

    @Test
    public void removeNode() {
        assertThrows(NullPointerException.class, () -> graph.removeNode(null));
        assertFalse(graph.removeNode(1.5f));

        graph.addNode(1.5f);
        graph.addNode(2.5f);
        graph.addEdge(1.5f, 2.5f, "1.5to2.5");

        assertTrue(graph.removeNode(1.5f));
        assertFalse(graph.containsEdge(1.5f, 2.5f));
        assertFalse(graph.containsEdge(2.5f, 1.5f));
        assertTrue(graph.removeNode(2.5f));
        assertEquals(0, graph.numNodes());
        assertEquals(0, graph.numEdges());
    }

    @Test
    public void removeEdge() {
        assertThrows(NullPointerException.class, () -> graph.removeEdge(null, 1.5f));
        assertThrows(NullPointerException.class, () -> graph.removeEdge(1.5f, null));
        assertFalse(graph.removeNode(1.5f));

        graph.addNode(1.5f);
        graph.addNode(2.5f);
        graph.addEdge(1.5f, 2.5f, "1.5to2.5");

        assertTrue(graph.removeEdge(1.5f, 2.5f));
        assertFalse(graph.removeEdge(2.5f, 1.5f)); // Because it's undirected.
    }

    @Test
    public void numNodes() {
        assertEquals(0, graph.numNodes());
        graph.addNode(1.5f);
        graph.addNode(2.5f);

        assertEquals(2, graph.numNodes());
        graph.addNode(2.5f);
        assertEquals(2, graph.numNodes());
    }

    @Test
    public void numEdges() {
        assertEquals(0, graph.numEdges());
        graph.addNode(1.5f);
        graph.addNode(2.5f);
        graph.addEdge(1.5f, 2.5f, "1.5to2.5");
        assertEquals(2, graph.numEdges()); // Because it's undirected.
        graph.removeEdge(1.5f, 2.5f);
        assertEquals(0, graph.numEdges()); // Because it's undirected.
    }

    @Test
    public void getNodes() {
        assertEquals(0, graph.getNodes().size());
        graph.addNode(1.5f);
        graph.addNode(2.5f);
        graph.addNode(3.5f);

        assertEquals(graph.numNodes(), graph.getNodes().size());
        assertTrue(graph.getNodes().contains(1.5f));
        assertTrue(graph.getNodes().contains(2.5f));
        assertTrue(graph.getNodes().contains(3.5f));
    }

    @Test
    public void getEdges() {
        assertEquals(0, graph.getEdges().size());
        graph.addNode(1.5f);
        graph.addNode(2.5f);
        graph.addNode(3.5f);

        graph.addEdge(1.5f, 2.5f, "1.5to2.5");
        graph.addEdge(2.5f, 3.5f, "2.5to3.5");

        assertEquals(graph.numEdges(), graph.getEdges().size());

        var edge = new Edge<>(1.5f, 2.5f, null);
        assertTrue(graph.getEdges().contains(edge));

        edge = new Edge<>(2.5f, 1.5f, null);
        assertTrue(graph.getEdges().contains(edge)); // Because it's undirected.

        edge = new Edge<>(2.5f, 3.5f, null);
        assertTrue(graph.getEdges().contains(edge));
    }

    @Test
    public void getNeighbours() {
        assertThrows(IllegalStateException.class, () -> graph.getNeighbours(1.5f));
        assertThrows(NullPointerException.class, () -> graph.getNeighbours(null));

        graph.addNode(1.5f);
        assertEquals(0, graph.getNeighbours(1.5f).size());

        graph.addNode(2.5f);
        graph.addNode(3.5f);
        graph.addEdge(1.5f, 2.5f, "1.5to2.5");
        graph.addEdge(1.5f, 3.5f, "1.5to3.5");

        assertEquals(2, graph.getNeighbours(1.5f).size());
        assertTrue(graph.getNeighbours(1.5f).contains(2.5f));
        assertTrue(graph.getNeighbours(1.5f).contains(3.5f));
        assertFalse(graph.getNeighbours(1.5f).contains(1.5f));
    }

    @Test
    public void getLabel() {
        assertThrows(NullPointerException.class, () -> graph.getLabel(null, 0.0f));
        assertThrows(NullPointerException.class, () -> graph.getLabel(0.0f, null));
        assertDoesNotThrow(() -> graph.getLabel(1.5f, 2.5f));
        assertNull(graph.getLabel(1.5f, 2.5f));

        graph.addNode(1.5f);
        graph.addNode(2.5f);
        graph.addEdge(1.5f, 2.5f, "2.5to1.5");
        assertEquals("2.5to1.5", graph.getLabel(1.5f, 2.5f));
    }
}