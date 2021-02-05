package edu.uic.cs474.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test08_OverloadOverrideSameMethod {
  @Test
  public void findOverloadsAndOverridesAlongHierarchy() {
    // Process all inner classes
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test08_OverloadOverrideSameMethod");

    // Get the overloads
    Map<String, Map<String, Integer>> overloads = main.getOverloads();

    // Get the overrides
    Map<List<String>, Set<String>> overrides = main.getOverrides();

    // Check that the overrides has all classes
    {
      TreeSet<String> expectedClasses = new TreeSet<>(Set.of("A", "B", "C", "D"));
      Assert.assertNotNull(overloads);
      Assert.assertEquals(expectedClasses, new TreeSet<>(overloads.keySet()));
    }

    // Check that we found all overloads for A
    Map<String, Integer> expectedOverloadsA = Map.of();
    Assert.assertEquals(expectedOverloadsA, new TreeMap<>(overloads.get("A")));

    // Check that we found all overloads for B
    Map<String, Integer> expectedOverloadsB = new TreeMap<>(Map.of(
            "m1", 2
    ));
    Assert.assertEquals(expectedOverloadsB, new TreeMap<>(overloads.get("B")));

    // Check that we found all overloads for C
    Map<String, Integer> expectedOverloadsC = new TreeMap<>(Map.of(
            "m1", 3
    ));
    Assert.assertEquals(expectedOverloadsC, new TreeMap<>(overloads.get("C")));

    // Check that we found all overloads for D
    Map<String, Integer> expectedOverloadsD = new TreeMap<>(Map.of(
            "m1", 6
    ));
    Assert.assertEquals(expectedOverloadsC, new TreeMap<>(overloads.get("C")));

    // Check that the overrides are correct
    Map<List<String>, Set<String>> expectedOverrides = new TreeMap<>(Comparator.comparing(Objects::toString));
    expectedOverrides.putAll(Map.of(
            List.of("m1"), Set.of("B", "D"),
            List.of("m1", "String"), Set.of("C")
    ));
    Map<List<String>, Set<String>> actualOverrides = new TreeMap<>(Comparator.comparing(Objects::toString));
    actualOverrides.putAll(overrides);
    Assert.assertEquals(expectedOverrides, actualOverrides);
  }

  private static class A {
    void m1() { }
  }
  private static class B extends A {
    void m1() { }
    void m1(String arg) { }
  }
  private static class C extends B {
    void m1(String arg) { }
    private void m1(int arg) { }
  }
  private static class D extends C {
    void m1() { }
    void m1(int arg) { }
  }
}
