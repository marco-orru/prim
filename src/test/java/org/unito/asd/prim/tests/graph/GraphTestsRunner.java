package org.unito.asd.prim.tests.graph;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public final class GraphTestsRunner {
  public static void main(String[] args) {
    var junit = new JUnitCore();
    junit.addListener(new TextListener(System.out));
    junit.run(LabelledUndirectedGraphFloatTests.class,
        LabelledUndirectedGraphIntegerTests.class,
        LabelledUndirectedGraphStringTests.class,
        UnlabelledDirectedGraphStringTests.class,
        UnlabelledDirectedGraphFloatTests.class,
        UnlabelledDirectedGraphIntegerTests.class);
  }
}
