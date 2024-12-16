package org.unito.asd.prim;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A class implementing Prim's algorithm to compute the minimum spanning forest (MSF)
 * for a given graph. The MSF is a set of edges connecting all nodes of the graph with
 * the minimum total weight, ensuring that there is no cycle.
 */
public class Prim {
    /**
     * Computes the minimum spanning forest for a given graph.
     * Prim's algorithm is applied to each unvisited node, and edges are added
     * to the forest in the order of their weight, ensuring no cycles.
     *
     * @param graph the graph to compute the minimum spanning forest for
     * @param <V>   the type of vertices in the graph
     * @param <L>   the type of the edge label (weight), must extend Number
     * @return a collection of edges representing the minimum spanning forest
     */
    @Contract(pure = true)
    public static <V, L extends Number> Collection<? extends AbstractEdge<V, L>> minimumSpanningForest(@NotNull Graph<V, L> graph) {
        var visitedNodes = new HashSet<V>(graph.numNodes());
        var priorityQueue = new PriorityQueue<Edge<V, L>>(Comparator.comparing(edge -> edge.getLabel().doubleValue()));
        var msf = new ArrayList<AbstractEdge<V, L>>();

        // Iter through all the nodes to make sure every mst is considered.
        for (var node : graph.getNodes()) {
            // If the node has been already visited, continue with the next one, otherwise, mark it as visited.
            if (!visitedNodes.add(node))
                continue;

            // Loop until eventually all the nodes has been analyzed
            // this condition is true iff there is only one mst in the graph, otherwise the "break" from below will be used.
            while (visitedNodes.size() < graph.numNodes()) {

                // Add all the edges from the current node to its neighbours to the priority queue.
                for (var neighbour : graph.getNeighbours(node)) {
                    // Optimization: Add it iff the node has not been considered yet.
                    if (!visitedNodes.contains(neighbour)) {
                        var edge = new Edge<>(node, neighbour, graph.getLabel(node, neighbour));
                        priorityQueue.push(edge);
                    }
                }

                // Keep extracting the min edge from the priority queue,
                // until the priority queue is empty (the mst is complete) or
                // the end node of the edge has already been visited (it should be ignored).
                var minEdge = priorityQueue.top();
                while (minEdge != null && visitedNodes.contains(minEdge.getEnd())) {
                    priorityQueue.pop();
                    minEdge = priorityQueue.top();
                }

                // If the min edge is null, the mst built from 'node' is complete, so we should break the loop.
                if (minEdge == null)
                    break;

                // Add the edge to the mst, add its end node to the visited nodes and restart the loop, using the end
                // node as the start node for the subsequent node search.
                msf.add(minEdge);
                visitedNodes.add(minEdge.getEnd());
                node = minEdge.getEnd();
            }

            if (visitedNodes.size() == graph.numNodes())
                break;
        }

        return msf;
    }

    /**
     * Reads data from the given file and constructs a graph.
     * Each line in the file should describe an edge in the format:
     * "node1,node2,weight".
     *
     * @param inputFile the BufferedReader for the input file
     * @return a Graph representing the edges and nodes
     * @throws IOException if an I/O error occurs while reading the file
     */
    private static Graph<String, Double> readDataToGraph(BufferedReader inputFile) throws IOException {
        var graph = new Graph<String, Double>(false, true);

        String line;
        while ((line = inputFile.readLine()) != null) {
            var fmtLine = line.split(",");

            graph.addNode(fmtLine[0]);  // from
            graph.addNode(fmtLine[1]);  // to
            graph.addEdge(fmtLine[0], fmtLine[1], Double.parseDouble(fmtLine[2]));  // distance
        }

        return graph;
    }

    /**
     * Prints the information about the minimum spanning forest (MSF).
     * This includes the number of edges and the total weight of the MSF,
     * followed by the details of each edge.
     *
     * @param msf the minimum spanning forest as a collection of edges
     * @param <V> the type of vertices in the graph
     * @param <L> the type of the edge label (weight), must extend Number
     */
    private static <V, L extends Number> void printMsfInfo(@NotNull Collection<? extends AbstractEdge<V, L>> msf) {
        for (var edge : msf) {
            System.out.printf("[%s]--(%s)--[%s]%n", edge.getStart(), edge.getLabel(), edge.getEnd());
        }
        System.out.printf("Number of edges: %d%n", msf.size());
        System.out.printf("Total weight: %.0f km%n", msf.stream().mapToDouble(edge -> edge.getLabel().doubleValue()).sum());
    }

    /**
     * The main method of the application. Reads the graph from a file, computes the
     * minimum spanning forest using Prim's algorithm, and prints the results.
     *
     * @param args the command-line arguments, where the first argument is the path
     *             to the input file containing the graph data
     */
    public static void main(String @NotNull [] args) {
        try {
            if (args.length == 0)
                throw new RuntimeException("Wrong number of arguments (input file not found!)");

            var path = args[0];

            Graph<String, Double> graph;
            try (var inputFile = new BufferedReader(new FileReader(path))) {
                graph = readDataToGraph(inputFile);
            } catch (IOException e) {
                System.err.println("Error while opening input file");
                throw new RuntimeException(e);
            }

            var msf = minimumSpanningForest(graph);
            printMsfInfo(msf);

        } catch (Exception e) {
            System.err.printf("Error: %s%n", e.getMessage());
        }
    }
}
