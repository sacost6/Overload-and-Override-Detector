package edu.uic.cs474.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Test03_SimpleOverride {

  @Test
  public void notOverridenMethodsAreNotFound() {
    // Process all inner classes (only A in this case)
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test03_SimpleOverride");

    // Get the overrides
    Map<List<String>, Set<String>> overrides = main.getOverrides();

    // Check that the map does not have any method that was not overriden
    Assert.assertNotNull(overrides);
    Assert.assertFalse(overrides.containsKey(List.of("notOverridenA")));
    Assert.assertFalse(overrides.containsKey(List.of("notOverridenB")));
    Assert.assertFalse(overrides.containsKey(List.of("notOverridenC")));
  }

  @Test
  public void findDirectOverrides() {
    // Process all inner classes (only A in this case)
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test03_SimpleOverride");

    // Get the overrides
    Map<List<String>, Set<String>> overrides = main.getOverrides();

    // Check that the map has all methods directly overriden
    Assert.assertNotNull(overrides);
    Assert.assertTrue(overrides.containsKey(List.of("overridenAB")));
    Assert.assertTrue(overrides.containsKey(List.of("overridenAC")));

    // Check that we found the correct overrides
    Assert.assertEquals(Set.of("B"), overrides.get(List.of("overridenAB")));
    Assert.assertEquals(Set.of("C"), overrides.get(List.of("overridenAC")));
  }

  @Test
  public void onlyOverridenMethodsAreFound() {
    // Process all inner classes (only A in this case)
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test03_SimpleOverride");

    // Get the overrides
    Map<List<String>, Set<String>> overrides = main.getOverrides();

    Set<List<String>> expectedMethodsFound = Set.of(
            List.of("overridenABC"),
            List.of("overridenAB"),
            List.of("overridenAC"));

    // Check that the map only contains the expected overrides
    Assert.assertNotNull(overrides);
    Assert.assertEquals(expectedMethodsFound, overrides.keySet());
  }

  private static class A {
    public void overridenABC() { }

    public void overridenAB() { }

    public void overridenAC() { }

    public void notOverridenA() { }
  }

  private static class B extends A {
    public void overridenABC() { }

    public void overridenAB() { }

    public void notOverridenB() { }
  }

  private static class C extends A {
    public void overridenABC() { }

    public void overridenAC() { }

    public void notOverridenC() { }
  }
}
