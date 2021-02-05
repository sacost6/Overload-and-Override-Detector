package edu.uic.cs474.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class Test09_OverrideDirectInterfaces {
  @Test
  public void findOverloadsAlongHierarchy() {
    // Process all inner classes
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test09_OverrideDirectInterfaces");

    // Get the overrides
    Map<List<String>, Set<String>> overrides = main.getOverrides();

    // Check that the overrides are correct
    Map<List<String>, Set<String>> expectedOverrides = new TreeMap<>(Comparator.comparing(Objects::toString));
    expectedOverrides.putAll(Map.of(
            List.of("m1"), Set.of("A", "B"),
            List.of("m1", "int"), Set.of("A"),
            List.of("m2"), Set.of("B"),
            List.of("m2", "String"), Set.of("B")
    ));
    Map<List<String>, Set<String>> actualOverrides = new TreeMap<>(Comparator.comparing(Objects::toString));
    actualOverrides.putAll(overrides);
    Assert.assertEquals(expectedOverrides, actualOverrides);

  }
  private /*static*/ interface I1 {
    default void m1() { }
    default void m1(int i) { }
  }
  private /*static*/ interface I2 {
    default void m2() { }
  }
  private static class A implements I1 {
    public void m1() { }
    public void m1(int i) { }
    public void m2(String args) { }
  }
  private static class B extends A implements I2 {
    public void m1() { }
    public void m2() { }
    public void m2(String args) { }
  }
}
