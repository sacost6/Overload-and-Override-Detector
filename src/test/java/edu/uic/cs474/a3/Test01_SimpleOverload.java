package edu.uic.cs474.a3;

import edu.uic.cs474.a3.Main;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test01_SimpleOverload {

    @Test
    public void findAllClasses() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test01_SimpleOverload");

        // Get the overloads
        Map<String, Map<String, Integer>> overloads = main.getOverloads();

        // Ensure that the map has all classes defined in Test01: A, B, C1, and C2
        // Use TreeSet so it's easy to see what's wrong when a test fails
        Assert.assertNotNull(overloads);
        Set<String> classes = new TreeSet<>(overloads.keySet());
        Set<String> expectedClasses = new TreeSet<>(Set.of("A", "B", "C"));
        Assert.assertEquals(expectedClasses, classes);
    }

    @Test
    public void findNoOverloadedMethods() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test01_SimpleOverload");

        // Get the overloads
        Map<String, Map<String, Integer>> overloads = main.getOverloads();

        // Check that the map has class C1 and C2
        Assert.assertNotNull(overloads);
        Assert.assertTrue(overloads.containsKey("C"));

        // Check that we found zero overloads
        Assert.assertTrue(overloads.get("C").isEmpty());
    }

    @Test
    public void findSingleOverloadedMethods() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test01_SimpleOverload");

        // Get the overloads
        Map<String, Map<String, Integer>> overloads = main.getOverloads();

        // Check that the map has class A
        Assert.assertNotNull(overloads);
        Assert.assertTrue(overloads.containsKey("A"));

        // Check that we only found one overload
        // Use TreeSet so it's easy to see what's wrong when a test fails
        Map<String, Integer> overloadsFoundInA = new TreeMap<>(overloads.get("A"));
        Map<String, Integer> expectedOverloads = new TreeMap<>(Map.of("overloadedMethod", 2));
        Assert.assertEquals(expectedOverloads, overloadsFoundInA);
    }

    private static class A {
        public void overloadedMethod() { }

        public void overloadedMethod(int argument) { }
    }

    private static class B {
        public void notOverloadedMethod() { }

        public void alsoNotOverloadedMethod(int argument) { }
    }

    private static class C {
        public int hashCode() { return 0; }

        public String toString() { return ""; }
    }
}
