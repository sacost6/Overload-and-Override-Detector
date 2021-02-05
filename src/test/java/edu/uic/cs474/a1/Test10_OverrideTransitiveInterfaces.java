package edu.uic.cs474.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class Test10_OverrideTransitiveInterfaces {
  @Test
  public void findOverloadsAlongHierarchy() {
    // Process all inner classes
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test10_OverrideTransitiveInterfaces");

    // Get the overrides
    Map<List<String>, Set<String>> overrides = main.getOverrides();

    // Check that the overrides are correct
    Map<List<String>, Set<String>> expectedOverrides = new TreeMap<>(Comparator.comparing(Objects::toString));
    expectedOverrides.putAll(Map.of(
            List.of("m1"), Set.of("C"),
            List.of("m2"), Set.of("C"),
            List.of("m3"), Set.of("C")
    ));
    Assert.assertEquals(expectedOverrides, overrides);

  }
  private /*static*/ interface I1 { default void m1() { } }
  private /*static*/ interface I2 { default void m2() { } }
  private /*static*/ interface I3 extends I1, I2 { }
  private /*static*/ interface I4 extends I3 { }

  private /*static*/ interface I5 { default void m3() { } }
  private /*static*/ interface I6 extends I2, I5 { }
  private /*static*/ interface I7 extends I4 { }
  private /*static*/ interface I8 extends I7, I6 { }
  private /*static*/ interface I9 extends I8 { }

  private static class C implements I9 {
    public void m1() { }
    public void m2() { }
    public void m3() { }
    public void m4() { }
  }
}
