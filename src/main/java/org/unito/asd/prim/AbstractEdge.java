package org.unito.asd.prim;

/**
 * Defines an interface representing an edge in a {@link AbstractGraph graph}.
 *
 * @param <V> Type of nodes in the graph.
 * @param <L> Type of labels associated with edges in the graph.
 */
public interface AbstractEdge<V, L> {
    /**
     * Gets the start node of the edge.
     *
     * @return The start node of the edge.
     */
    V getStart();

    /**
     * Gets the end node of the edge.
     *
     * @return The end node of the edge.
     */
    V getEnd();

    /**
     * Gets the label associated with the edge.
     *
     * @return The label associated with the edge.
     */
    L getLabel();
}
