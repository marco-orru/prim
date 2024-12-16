package org.unito.asd.prim.tests.priorityqueue;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public final class PriorityQueueTestsRunner {
  public static void main(String[] args) {
    var junit = new JUnitCore();
    junit.addListener(new TextListener(System.out));
    junit.run(PriorityQueueStringTests.class,
        PriorityQueueIntegerTests.class,
        PriorityQueueFloatTests.class,
        PriorityQueuePersonTests.class);
  }
}
