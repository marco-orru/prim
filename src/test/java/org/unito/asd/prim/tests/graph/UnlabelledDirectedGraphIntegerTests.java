package org.unito.asd.prim.tests.graph;

import org.junit.Before;
import org.junit.Test;
import org.unito.asd.prim.Edge;
import org.unito.asd.prim.Graph;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
public final class UnlabelledDirectedGraphIntegerTests {
    private Graph<Integer, String> graph;

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
        assertTrue(graph.addNode(1));
        assertTrue(graph.addNode(2));
        assertFalse(graph.addNode(1));
        assertEquals(2, graph.numNodes());
    }

    @Test
    public void addEdge() {
        graph.addNode(1);
        graph.addNode(2);

        assertTrue(graph.addEdge(1, 2, "1to2"));
        assertTrue(graph.addEdge(2, 1, "2to1"));
        assertFalse(graph.addEdge(1, 2, "another_1to2"));
        assertEquals(2, graph.numEdges());
        assertDoesNotThrow(() -> graph.addEdge(0, 0, null));
    }

    @Test
    public void containsNode() {
        assertFalse(graph.containsNode(1));

        graph.addNode(1);
        graph.addNode(2);

        assertTrue(graph.containsNode(1));
        assertTrue(graph.containsNode(2));
    }

    @Test
    public void containsEdge() {
        assertFalse(graph.containsEdge(1, 2));

        graph.addNode(1);
        graph.addNode(2);

        graph.addEdge(1, 2, "1to2");

        assertTrue(graph.containsEdge(1, 2));
        assertFalse(graph.containsEdge(2, 1));
    }

    @Test
    public void removeNode() {
        assertFalse(graph.removeNode(1));

        graph.addNode(1);
        graph.addNode(2);
        graph.addEdge(1, 2, "1to2");

        assertTrue(graph.removeNode(1));
        assertFalse(graph.containsEdge(1, 2));
        assertFalse(graph.containsEdge(2, 1));
        assertTrue(graph.removeNode(2));
        assertEquals(0, graph.numNodes());
        assertEquals(0, graph.numEdges());
    }

    @Test
    public void removeEdge() {
        assertThrows(NullPointerException.class, () -> graph.removeEdge(1, null));
        assertFalse(graph.removeNode(1));

        graph.addNode(1);
        graph.addNode(2);
        graph.addEdge(1, 2, "1to2");

        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(2, 1));
    }

    @Test
    public void numNodes() {
        assertEquals(0, graph.numNodes());
        graph.addNode(1);
        graph.addNode(2);

        assertEquals(2, graph.numNodes());
        graph.addNode(2);
        assertEquals(2, graph.numNodes());
    }

    @Test
    public void numEdges() {
        assertEquals(0, graph.numEdges());
        graph.addNode(1);
        graph.addNode(2);
        graph.addEdge(1, 2, "1to2");
        assertEquals(1, graph.numEdges());
        graph.removeEdge(1, 2);
        assertEquals(0, graph.numEdges());
    }

    @Test
    public void getNodes() {
        assertEquals(0, graph.getNodes().size());
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        assertEquals(graph.numNodes(), graph.getNodes().size());
        assertTrue(graph.getNodes().contains(1));
        assertTrue(graph.getNodes().contains(2));
        assertTrue(graph.getNodes().contains(3));
    }

    @Test
    public void getEdges() {
        assertEquals(0, graph.getEdges().size());
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        graph.addEdge(1, 2, "1to2");
        graph.addEdge(2, 3, "2to3");

        assertEquals(graph.numEdges(), graph.getEdges().size());

        var edge = new Edge<>(1, 2, null);
        assertTrue(graph.getEdges().contains(edge));

        edge = new Edge<>(2, 1, null);
        assertFalse(graph.getEdges().contains(edge));

        edge = new Edge<>(2, 3, null);
        assertTrue(graph.getEdges().contains(edge));
    }

    @Test
    public void getNeighbours() {
        assertThrows(NullPointerException.class, () -> graph.getNeighbours(null));

        graph.addNode(1);
        assertEquals(0, graph.getNeighbours(1).size());

        graph.addNode(2);
        graph.addNode(3);
        graph.addEdge(1, 2, "1to2");
        graph.addEdge(1, 3, "1to3");

        assertEquals(2, graph.getNeighbours(1).size());
        assertTrue(graph.getNeighbours(1).contains(2));
        assertTrue(graph.getNeighbours(1).contains(3));
        assertFalse(graph.getNeighbours(1).contains(1));
    }

    @Test
    public void getLabel() {
        assertThrows(NullPointerException.class, () -> graph.getLabel(null, 0));
        assertThrows(NullPointerException.class, () -> graph.getLabel(0, null));
        assertThrows(IllegalStateException.class, () -> graph.getLabel(1, 2));
    }
}
