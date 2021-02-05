package edu.uic.cs474.a3;

import edu.uic.cs474.a3.Main;
import edu.uic.cs474.a3.ReflectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class Test07_DirectStaticInvocation {

    @Test
    public void testDirectStaticInvocation() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            Assert.assertNotEquals("A", A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.empty());
            Assert.assertEquals(Optional.of("A"), result);
            Assert.assertEquals("A", A.s);
        }

        {
            Assert.assertNotEquals("A 474", A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.empty(), " 474");
            Assert.assertEquals(Optional.of("A" + " 474"), result);
            Assert.assertEquals("A" + " 474", A.s);
        }

        {
            Assert.assertNotEquals("B", A.s);
            Optional<Object> result = utils.invokeMethod(B.class, "methodB", Optional.empty());
            Assert.assertEquals(Optional.of("B"), result);
            Assert.assertEquals("B", A.s);
        }
    }

    @Test
    public void testNotExistingStaticMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String s = A.s;
            Optional<Object> result = utils.invokeMethod(B.class, "methodDoesNotExist", Optional.empty(), " 474");
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertEquals(s, A.s);
        }
    }

    public static class A {
        public static String s = null;

        public static String method() { return (A.s = "A"); }

        public static String method(String s) { return (A.s = "A" + s); }
    }

    public static class B extends A {
        public static String methodB() { return (A.s = "B"); }
    }
}
