package edu.uic.cs474.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;


public class Test02_InvokeMethodsWithArguments {

    @Test
    public void invokeMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "methodA", Optional.of(a), " CS474");
            Assert.assertEquals(Optional.of("mA CS474"), result);
            Assert.assertEquals("mA CS474", a.s);
        }

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(B.class, "methodB", Optional.of(b), " CS", 474);
            Assert.assertEquals(Optional.of("mB CS474"), result);
            Assert.assertEquals("mB CS474", b.s);
        }
    }

    @Test
    public void invokeOverridenMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "overriden", Optional.of(a), new String(" CS474"));
            Assert.assertEquals(Optional.of("oA CS474"), result);
            Assert.assertEquals("oA CS474", a.s);
        }

        try {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(A.class, "overriden", Optional.of(b), new String(" CS474"));
            Assert.assertEquals(Optional.of("oB CS474"), result);
            Assert.assertEquals("oB CS474", b.s);
        } catch (Throwable t) {
            Assert.fail(t.getMessage());
        }

        {
            A b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(A.class, "overriden", Optional.of(b), new String(" CS474"));
            Assert.assertEquals(Optional.of("oB CS474"), result);
            Assert.assertEquals("oB CS474", b.s);
        }
    }

    @Test
    public void invokeOverloadedMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(B.class, "overloaded", Optional.of(b));
            Assert.assertEquals(Optional.of("olB"), result);
            Assert.assertEquals("olB", b.s);
        }

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(B.class, "overloaded", Optional.of(b), " CS474");
            Assert.assertEquals(Optional.of("olB CS474"), result);
            Assert.assertEquals("olB CS474", b.s);
        }

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(B.class, "overloaded", Optional.of(b), " 474");
            Assert.assertEquals(Optional.of("olB 474"), result);
            Assert.assertEquals("olB 474", b.s);
        }

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(B.class, "overloaded", Optional.of(b), " CS", 474);
            Assert.assertEquals(Optional.of("olB CS474"), result);
            Assert.assertEquals("olB CS474", b.s);
        }
    }

    public static class A {
        public String s = null;

        public String methodA(String s) { return (this.s = "mA" + s); }

        public String overriden(String s) { return (this.s = "oA" + s); }
    }

    public static class B extends A {
        public String methodB(String s, int i) { return (this.s = "mB" + s + i); }

        public String overriden(String s) { return (this.s = "oB" + s); }

        public String overloaded() { return (this.s = "olB"); }

        public String overloaded(String s) { return (this.s = "olB" + s); }

        public String overloaded(int i) { return (this.s = "olB" + i); }

        public String overloaded(String s, int i) { return (this.s = "olB" + s + i); }
    }
}
