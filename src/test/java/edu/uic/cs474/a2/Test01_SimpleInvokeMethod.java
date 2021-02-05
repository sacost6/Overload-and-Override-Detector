package edu.uic.cs474.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;


public class Test01_SimpleInvokeMethod {

    @Test
    public void invokeMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "methodA", Optional.of(a));
            Assert.assertEquals(Optional.of("mA"), result);
            Assert.assertEquals("mA", a.s);
        }

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(B.class, "methodB", Optional.of(b));
            Assert.assertEquals(Optional.of("mB"), result);
            Assert.assertEquals("mB", b.s);
        }
    }

    @Test
    public void invokeInheritedMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(A.class, "methodA", Optional.of(b));
            Assert.assertEquals(Optional.of("mA"), result);
            Assert.assertEquals("mA", b.s);
        }

        {
            A b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(A.class, "methodA", Optional.of(b));
            Assert.assertEquals(Optional.of("mA"), result);
            Assert.assertEquals("mA", b.s);
        }
    }

    @Test
    public void invokeOverridenMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "overriden", Optional.of(a));
            Assert.assertEquals(Optional.of("oA"), result);
            Assert.assertEquals("oA", a.s);
        }

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(A.class, "overriden", Optional.of(b));
            Assert.assertEquals(Optional.of("oB"), result);
            Assert.assertEquals("oB", b.s);
        }

        {
            A b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod(A.class, "overriden", Optional.of(b));
            Assert.assertEquals(Optional.of("oB"), result);
            Assert.assertEquals("oB", b.s);
        }
    }

    @Test
    public void invokeMethodWithName() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod("edu.uic.cs474.a2.Test01_SimpleInvokeMethod$" + "A", "methodA", Optional.of(a));
            Assert.assertEquals(Optional.of("mA"), result);
            Assert.assertEquals("mA", a.s);
        }

        {
            B b = new B();
            Assert.assertNull(b.s);
            Optional<Object> result = utils.invokeMethod("edu.uic.cs474.a2.Test01_SimpleInvokeMethod$" + "B", "methodB", Optional.of(b));
            Assert.assertEquals(Optional.of("mB"), result);
            Assert.assertEquals("mB", b.s);
        }
    }

    public static class A {
        public String s = null;

        public String methodA() { return (this.s = "mA"); }

        public String overriden() { return (this.s = "oA"); }
    }

    public static class B extends A {
        public String methodB() { return (this.s = "mB"); }

        public String overriden() { return (this.s = "oB"); }
    }
}
