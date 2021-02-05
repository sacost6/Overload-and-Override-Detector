package edu.uic.cs474.a3;

import edu.uic.cs474.a3.Main;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test07_OverloadAlongHierarchy {
  @Test
  public void findOverloadsAlongHierarchy() {
    // Process all inner classes
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test07_OverloadAlongHierarchy");

    // Get the overloads
    Map<String, Map<String, Integer>> overloads = main.getOverloads();

    // Check that the map has all classes
    TreeSet<String> expectedClasses = new TreeSet<>(Set.of("A", "B", "C"));
    Assert.assertNotNull(overloads);
    Assert.assertEquals(expectedClasses, new TreeSet<>(overloads.keySet()));

    // Check that we found all overloads for A
    Map<String, Integer> expectedOverloadsA = Map.of();
    Assert.assertEquals(expectedOverloadsA, new TreeMap<>(overloads.get("A")));

    // Check that we found all overloads for B
    Map<String, Integer> expectedOverloadsB = new TreeMap<>(Map.of(
            "m1", 2,
            "m2", 2
    ));
    Assert.assertEquals(expectedOverloadsB, new TreeMap<>(overloads.get("B")));

    // Check that we found all overloads for C
    Map<String, Integer> expectedOverloadsC = new TreeMap<>(Map.of(
            "m1", 4
    ));
    Assert.assertEquals(expectedOverloadsC, new TreeMap<>(overloads.get("C")));
  }

  private static class A {
    void m1() { }

    void m2() { }
  }

  private static class B extends A {
    void m1(String arg) { }

    private void m2(String arg) { }
  }

  private static class C extends B {
    void m1(int arg) { }

    void m1(long arg) { }
  }

}
