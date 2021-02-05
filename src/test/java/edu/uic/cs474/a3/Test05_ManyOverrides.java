package edu.uic.cs474.a3;

import edu.uic.cs474.a3.Main;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test05_ManyOverrides {
    @Test
    public void findAllOverrides() {
        // Process all inner classes (only A in this case)
        Main main = new Main();
        main.processClass("edu.uic.cs474.a1.Test05_ManyOverrides");

        // Get the overrides
        Map<List<String>, Set<String>> overrides = new TreeMap<>(Comparator.comparing(Object::toString));
        overrides.putAll(main.getOverrides());

        Map<List<String>, Set<String>> expected = new TreeMap<>(Comparator.comparing(Object::toString));
        expected.put(List.of("m1"), new TreeSet<>(Set.of("A1","A2","A6","A7","A9","A10")));
        expected.put(List.of("m2"), new TreeSet<>(Set.of("A1","A3","A4","A6")));
        expected.put(List.of("m3"), new TreeSet<>(Set.of("A3","A6","A7","A8","A9","A10")));
        expected.put(List.of("m4"), new TreeSet<>(Set.of("A2","A4","A7","A9")));
        expected.put(List.of("m5"), new TreeSet<>(Set.of("A1","A3","A4","A5","A7")));

        // Check that we found the correct overrides
        Assert.assertEquals(expected, overrides);
    }

    private static class A0 {	void m1() { }	void m2() { }	void m3() { }	void m4() { }	void m5() { }	}
    private static class A1 extends A0 {	void m1() { }	void m2() { }			void m5() { }	}
    private static class A2 extends A1 {	void m1() { }			void m4() { }		}
    private static class A3 extends A2 {		void m2() { }	void m3() { }		void m5() { }	}
    private static class A4 extends A3 {		void m2() { }		void m4() { }	void m5() { }	}
    private static class A5 extends A4 {					void m5() { }	}
    private static class A6 extends A5 {	void m1() { }	void m2() { }	void m3() { }			}
    private static class A7 extends A6 {	void m1() { }		void m3() { }	void m4() { }	void m5() { }	}
    private static class A8 extends A7 {			void m3() { }			}
    private static class A9 extends A8 {	void m1() { }		void m3() { }	void m4() { }		}
    private static class A10 extends A9 {	void m1() { }		void m3() { }			}
}
