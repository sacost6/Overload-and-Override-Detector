package edu.uic.cs474.a3;

import edu.uic.cs474.a3.Main;
import edu.uic.cs474.a3.ReflectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class Test10_OverloadedHierarchyStaticInvocation {

    @Test
    public void testArgumentType() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "ArgumentType" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.of(new ArgumentType(" 474")));
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    @Test
    public void testExtendsArgumentType() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "ArgumentType" + "ExtendsArgumentType" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.of(new ExtendsArgumentType(" 474")));
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    @Test
    public void testObject() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            Object o = new Object();
            String expected = "Object " + o.toString();
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.of(o));
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    @Test
    public void testList() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "Object " + List.of("ArgumentType", new ArgumentType(" 474"), new ExtendsArgumentType(" 474")).toString();
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method",
                    Optional.of(List.<Object>of("ArgumentType", new ArgumentType(" 474"), new ExtendsArgumentType(" 474"))));
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    @Test
    public void testNotAnArgumentType() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            String expected = "Object " + "NotAnArgumentType" + " 474";
            Assert.assertNotEquals(expected, A.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.of(new NotAnArgumentType(" 474")));
            Assert.assertEquals(Optional.of(expected), result);
            Assert.assertEquals(expected, A.s);
            A.s = null;
        }
    }

    public static class A {
        public static String s = null;

        public static String method(String s) { return (A.s = "A" + s); }

        public static String method(int i) { return (A.s = "i"); }

        public static String method(ArgumentType a) { return (A.s = "ArgumentType" + a.s); }

        public static String method(Object o) { return (A.s = "Object " + o.toString()); }
    }

    public static class ArgumentType {
        public final String s;

        public ArgumentType(String s) { this.s  = s; }

        @Override
        public String toString() { return "ArgumentType" + "{" + "s='" + this.s + '\'' + '}'; }
    }

    public static class ExtendsArgumentType extends ArgumentType {
        public ExtendsArgumentType(String s) { super("ExtendsArgumentType" + s); }

        @Override
        public String toString() { return "ExtendsArgumentType" + "{" + "s='" + this.s + '\'' + '}'; }
    }

    public static class NotAnArgumentType {
        public final String s;

        public NotAnArgumentType(String s) { this.s  = s; }

        @Override
        public String toString() { return "NotAnArgumentType" + this.s; }

    }
}
