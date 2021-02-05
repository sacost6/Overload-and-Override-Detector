package edu.uic.cs474.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class Test08_InheritedStaticInvocation {

    @Test
    public void testInheritedStaticMethodsFromB() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "A" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(B.class, "method", true, Optional.empty(), " 474");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    @Test
    public void testInheritedStaticMethodsFromC() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "A" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(C.class, "method", true, Optional.empty(), " 474");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }

        {
            String expected = "C" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(C.class, "methodC", true, Optional.empty(), " 474");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    @Test
    public void testInheritedStaticMethodsFromD() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "A" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(D.class, "method", true, Optional.empty(), " 474");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }

        {
            String expected = "C" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(D.class, "methodC", true, Optional.empty(), " 474");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    @Test
    public void testInheritedStaticMethodsFromE() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "A" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(E.class, "method", true, Optional.empty(), " 474");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }

        {
            String expected = "C" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(E.class, "methodC", true, Optional.empty(), " 474");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }

        {
            String expected = "E" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(E.class, "methodE", true, Optional.empty(), " 474");
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    public static class A {
        public static String s = null;

        protected static String method(String s) { return (A.s = "A" + s); }
    }

    public static class B extends A { }

    public static class C extends B {
        /*default*/ static String methodC(String s) { return (A.s = "C" + s); }
    }

    public static class D extends C { }

    public static class E extends D {
        private static String methodE(String s) { return (A.s = "E" + s); }
    }
}
