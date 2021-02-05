package edu.uic.cs474.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class Test02_ManyOverloads {

    @Test
    public void findTwoOverloadedMethods() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test02_ManyOverloads");

        // Get the overloads
        Map<String, Map<String, Integer>> overloads = main.getOverloads();

        // Check that the map has class A
        Assert.assertNotNull(overloads);
        Assert.assertTrue(overloads.containsKey("A"));

        // Check that we found the correct overloads
        // Use TreeSet so it's easy to see what's wrong when a test fails
        Map<String, Integer> overloadsFoundInA = new TreeMap<>(overloads.get("A"));
        Map<String, Integer> expectedOverloads = new TreeMap<>(Map.of("overloadedMethod1", 2, "overloadedMethod2", 3));
        Assert.assertEquals(expectedOverloads, overloadsFoundInA);
    }

    private static class A {
        public void overloadedMethod1() {}

        public void overloadedMethod2(C1 argument1, C2 argument2) {}

        public void overloadedMethod1(C1 argument1, C2 argument2) {}

        public void overloadedMethod2(C2 argument) {}

        public void overloadedMethod2() {}
    }

    private static class C1 { @Override public int  hashCode() { return 0; } }
    private static class C2 {           public void notOverloadedMethod() { } }
    private static class C3 {           public void notOverloadedMethod() { } }

    @Test
    public void findManyOverloadedMethods() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test02_ManyOverloads");

        // Get the overloads
        Map<String, Map<String, Integer>> overloads = main.getOverloads();

        String[] classes = new String[]{ "A1" , "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10" };

        Map<String, Map<String, Integer>> expected = Map.of(
                "A1", Map.of("m1", 2,"m3", 3),
                "A2", Map.of("m1", 3,"m2", 2,"m3", 2),
                "A3", Map.of("m2", 2,"m3", 3),
                "A4" ,Map.of("m1", 3,"m2", 2),
                "A5" ,Map.of("m1", 2,"m2", 3,"m3", 2),
                "A6" ,Map.of("m1", 2,"m2", 2,"m3", 3),
                "A7" ,Map.of("m1", 3,"m3", 3),
                "A8" ,Map.of("m1", 3,"m2", 2),
                "A9" ,Map.of("m1", 2,"m2", 2,"m3", 3),
                "A10",Map.of("m2", 3)
        );

        for (String c : classes) {
            // Check that the map has classes A_
            Assert.assertNotNull(overloads);
            Assert.assertTrue(overloads.containsKey(c));

            // Check that we found the correct overloads
            System.out.println(c);
            Assert.assertEquals(expected.get(c), overloads.get(c));
        }
    }

    private static class A1{		void m1(C1 c1) { }		void m1(C3 c3) { }	void m2() { }				void m3() { }	void m3(C1 c1) { }	void m3(C2 c2) { }		}
    private static class A2{	void m1() { }	void m1(C1 c1) { }	void m1(C2 c2) { }			void m2(C1 c1) { }	void m2(C2 c2) { }		void m3() { }		void m3(C2 c2) { }		}
    private static class A3{		void m1(C1 c1) { }			void m2() { }	void m2(C1 c1) { }			void m3() { }	void m3(C1 c1) { }		void m3(C3 c3) { }	}
    private static class A4{		void m1(C1 c1) { }	void m1(C2 c2) { }	void m1(C3 c3) { }		void m2(C1 c1) { }		void m2(C3 c3) { }				void m3(C3 c3) { }	}
    private static class A5{		void m1(C1 c1) { }	void m1(C2 c2) { }		void m2() { }	void m2(C1 c1) { }	void m2(C2 c2) { }			void m3(C1 c1) { }	void m3(C2 c2) { }		}
    private static class A6{			void m1(C2 c2) { }	void m1(C3 c3) { }		void m2(C1 c1) { }	void m2(C2 c2) { }		void m3() { }	void m3(C1 c1) { }	void m3(C2 c2) { }		}
    private static class A7{	void m1() { }		void m1(C2 c2) { }	void m1(C3 c3) { }			void m2(C2 c2) { }		void m3() { }	void m3(C1 c1) { }		void m3(C3 c3) { }	}
    private static class A8{	void m1() { }		void m1(C2 c2) { }	void m1(C3 c3) { }	void m2() { }			void m2(C3 c3) { }				void m3(C3 c3) { }	}
    private static class A9{	void m1() { }	void m1(C1 c1) { }			void m2() { }		void m2(C2 c2) { }		void m3() { }		void m3(C2 c2) { }	void m3(C3 c3) { }	}
    private static class A10{				void m1(C3 c3) { }	void m2() { }		void m2(C2 c2) { }	void m2(C3 c3) { }				void m3(C3 c3) { }	}
}
