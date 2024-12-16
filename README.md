# Prim

> Part of a University project for the class _Algorithms and Data Structures_ (*
*[ASD](https://laurea.informatica.unito.it/do/corsi.pl/Show?_id=iw3r)**, *Algoritmi e Strutture Dati*, UNITO
> Informatica).

This repository implements **Prim's Algorithm** to compute the **Minimum Spanning Forest (MSF)** for a given graph. The
MSF is a set of edges that connect all nodes of the graph with the minimum total weight, ensuring no cycles are formed.
The implementation is part of the `org.unito.asd.prim` package and is designed to handle weighted graphs where the
weight is represented by a numeric value.

## Features

- **Prim's Algorithm** for computing the MSF.
- Supports graphs with arbitrary node and edge types (generic).
- Reads graph data from a file with the format `node1,node2,weight`.
- JUnit4 unit tests to validate core functionalities.

## Class Overview

### `Prim` Class

The `Prim` class implements the core logic for computing the Minimum Spanning Forest (MSF) using Prim's algorithm.

#### Key Methods:

- `minimumSpanningForest(Graph<V, L> graph)`: Computes the MSF of a given graph.
- `readDataToGraph(BufferedReader inputFile)`: Reads graph data from a file and constructs the graph.
- `printMsfInfo(Collection<? extends AbstractEdge<V, L>> msf)`: Prints the edges and total weight of the MSF.
- `main(String[] args)`: Reads a graph from a file, computes the MSF using Prim's algorithm, and prints the results.

### Graph Representation

The repository includes a basic graph structure with nodes and edges. Each edge has a start and end node, and an
associated weight. The `Graph`, `Edge`, and `PriorityQueue` classes are all located within the same package (
`org.unito.asd.prim`).

## Requirements

- **Java 17** or higher.
- **JUnit4** for unit tests.
- **Gradle** for building the project.

## Building and Running the Project

### Gradle Build

This project uses Gradle as a build tool. To build and run the project, follow the steps below:

1. Clone this repository:
   ```sh
   git clone https://github.com/your-username/prim-algorithm.git
   cd prim-algorithm
   ```

2. Build the project using Gradle:
   ```sh
   gradle build
   ```

3. Run the main application:
   ```sh
   gradle run --args="<path-to-graph-file>"
   ```
   Replace `<path-to-graph-file>` with the path to your graph file.

### Example Graph File Format

Each line in the file should represent an edge in the graph in the format:

```
node1,node2,weight
```

Where `node1` and `node2` are node identifiers (strings) and `weight` is the edge weight (numeric).

For example:

```
A,B,4.0
A,C,1.0
B,C,2.0
C,D,5.0
```

### Running Unit Tests

Unit tests for core functionality (graph and priority queue) are located in `org.unito.asd.prim.tests`. To run the tests
using Gradle, execute:

```sh
gradle test
```