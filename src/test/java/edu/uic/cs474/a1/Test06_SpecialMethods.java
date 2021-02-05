package edu.uic.cs474.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test06_SpecialMethods {

  @Test
  public void testFindOverloadedSpecialMethods() {
    // Process all inner classes (only A in this case)
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test06_SpecialMethods");

    // Get the overloads
    Map<String, Map<String, Integer>> overloads = main.getOverloads();

    // Check that the map has all classes
    // Use TreeSet so it's easy to see what's wrong when a test fails
    Set<String> expectedClasses = new TreeSet<>(Set.of("OverrideSpecialMethods", "OverloadSpecialMethods"));
    Assert.assertNotNull(overloads);
    Assert.assertEquals(expectedClasses, new TreeSet<>(overloads.keySet()));

    // Check that we did not find overloads on OverrideSpecialMethods
    // Use TreeSet so it's easy to see what's wrong when a test fails
    {
      Map<String, Integer> overloadsFound = overloads.get("OverrideSpecialMethods");
      Assert.assertTrue(overloadsFound.isEmpty());
    }

    // Check that we found overloads on OverloadSpecialMethods
    // Use TreeSet so it's easy to see what's wrong when a test fails
    {
      Map<String, Integer> overloadsFound = new TreeMap<>(overloads.get("OverloadSpecialMethods"));
      Map<String, Integer> expectedOverloads = new TreeMap<>(Map.of(
              "toString", 2,
              "hashCode", 3
      ));
      Assert.assertEquals(expectedOverloads, overloadsFound);
    }
  }

  @Test
  public void testFindOverridenSpecialMethods() {

    // Process all inner classes
    Main main = new Main();
    main.processClass("edu.uic.cs474.a1.Test06_SpecialMethods");

    // Get the overrides
    Map<List<String>, Set<String>> overrides = main.getOverrides();


    // Check that we found the correct overrides
    Map<List<String>, Set<String>> expected = new TreeMap<>(Comparator.comparing(Object::toString));
    expected.putAll(Map.of(
            List.of("toString"), new TreeSet<>(Set.of("OverrideSpecialMethods")),
            List.of("hashCode"), new TreeSet<>(Set.of("OverrideSpecialMethods"))
    ));

    Assert.assertEquals(expected, overrides);
  }

  private static class OverrideSpecialMethods {

    public String toString() { return "CS474"; }

    public int hashCode() { return 474; }

  }

  private static class OverloadSpecialMethods {

    public int hashCode(int mask) { return this.hashCode() + mask; }

    public int hashCode(short mask) { return this.hashCode() + mask; }

    public String toString(String s) { return this.toString() + s; }
  }

}
