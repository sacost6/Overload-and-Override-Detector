package edu.uic.cs474.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test04_ComplexOverrides {

    @Test
    public void privateMethodsAreNotOverriden() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test04_ComplexOverrides");

        // Get the overrides
        Map<List<String>, Set<String>> overrides = main.getOverrides();

        // Check that the map does not have any method that was not overriden
        Assert.assertNotNull(overrides);
        Assert.assertFalse(overrides.containsKey(List.of("privateMethodsAreNotOverriden")));
    }

    @Test
    public void staticMethodsAreNotOverriden() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test04_ComplexOverrides");

        // Get the overrides
        Map<List<String>, Set<String>> overrides = main.getOverrides();

        // Check that the map does not have any method that was not overriden
        Assert.assertNotNull(overrides);
        Assert.assertFalse(overrides.containsKey(List.of("staticMethodsAreNotOverriden")));
    }

    @Test
    public void findDirectOverrides() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test04_ComplexOverrides");

        // Get the overrides
        Map<List<String>, Set<String>> overrides = main.getOverrides();

        // Check that the map has all methods directly overriden
        Assert.assertNotNull(overrides);
        Assert.assertTrue(overrides.containsKey(List.of("overridenAB")));
        Assert.assertTrue(overrides.containsKey(List.of("overridenBC")));

        // Check that we found the correct overrides
        Assert.assertEquals(Set.of("B"), overrides.get(List.of("overridenAB")));
        Assert.assertEquals(Set.of("C"), overrides.get(List.of("overridenBC")));
    }

    @Test
    public void findTransitiveOverrides() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test04_ComplexOverrides");

        // Get the overrides
        Map<List<String>, Set<String>> overrides = main.getOverrides();

        // Check that the map has all methods directly overriden
        Assert.assertNotNull(overrides);
        Assert.assertTrue(overrides.containsKey(List.of("overridenABC")));
        Assert.assertTrue(overrides.containsKey(List.of("overridenAC")));

        // Check that we found the correct overrides
        Assert.assertEquals(Set.of("B", "C"), overrides.get(List.of("overridenABC")));
        Assert.assertEquals(Set.of("C"), overrides.get(List.of("overridenAC")));
    }

    @Test
    public void onlyOverridenMethodsAreFound() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test04_ComplexOverrides");

        // Get the overrides
        Map<List<String>, Set<String>> overrides = main.getOverrides();

        Set<List<String>> expectedMethodsFound = Set.of(
                List.of("overridenABC"),
                List.of("overridenAB"),
                List.of("overridenAC"),
                List.of("overridenBC"));

        // Check that the map only contains the expected overrides
        Assert.assertNotNull(overrides);
        Assert.assertEquals(expectedMethodsFound, overrides.keySet());
    }

    private static class A {
        public void overridenABC() { }

        protected void overridenAB() { }

        /*default*/ void overridenAC() { }

        void notOverridenA() { }

        private void privateMethodsAreNotOverriden() { }

        static void staticMethodsAreNotOverriden() { }
    }

    private static class B extends A {
        public void overridenABC() { }

        protected void overridenAB() { }

        void overridenBC() { }

        void notOverridenB() { }

        private void privateMethodsAreNotOverriden() { }

        static void staticMethodsAreNotOverriden() { }
    }

    private static class C extends B {
        public void overridenABC() { }

        /*default*/ void overridenAC() { }

        final void overridenBC() { }

        void notOverridenC() { }

        private void notOverriden() { }

        private void privateMethodsAreNotOverriden() { }

        static void staticMethodsAreNotOverriden() { }
    }

}
