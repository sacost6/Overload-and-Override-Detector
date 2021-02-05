package edu.uic.cs474.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class Test09_OverloadedStaticInvocation {

    @Test
    public void testOverloadedStaticInvocation() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "A" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.empty() ," 4", "7", "4");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }

        {
            String expected = "i" + 474;
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.empty(), 400, 70, 4);
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }

        {
            String expected = "i" + 474;
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.empty(), 4, "7", 4);
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    @Test
    public void testReceiverAsArgument() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "A" + "A" + "474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.of(new A()), 404, 70);
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }

        {
            String expected = "A" + "A" + "474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.of(new A()), 4, "74");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    public static class A {
        public static String s = null;

        public static String method(String s, String s2, String s3) { return (A.s = "A" + s + s2 + s3); }

        public static String method(int i, int i2, int i3) { return (A.s = "i" + (i + i2 + i3)); }

        public static String method(int i, String s, int i2) { return (A.s = "i" + i + s + i2); }

        public static String method(A a, String s2, String s3) { return (A.s = "A" + "A" + s2 + s3); }

        public static String method(A a, int i2, int i3) { return (A.s = "A" + "A" + (i2 + i3)); }

        public static String method(A a, int i, String s) { return (A.s = "A" + "A" + i + s); }
    }
}
