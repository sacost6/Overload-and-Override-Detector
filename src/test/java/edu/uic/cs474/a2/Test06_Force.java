package edu.uic.cs474.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class Test06_Force {

    @Test
    public void forcePrivateMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "privateMethod", false, Optional.of(a), (byte)0xAF);
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "privateMethod", true, Optional.of(a), (byte)0xAF);
            Assert.assertEquals(Optional.of("privateMethod" + "-81"), result);
            Assert.assertEquals("privateMethod" + "-81", a.s);
        }
    }

    @Test
    public void forceDefaultMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "defaultMethod", false, Optional.of(a), 474.474f);
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "defaultMethod", true, Optional.of(a), 474.474f);
            Assert.assertEquals(Optional.of("defaultMethod" + "474.474"), result);
            Assert.assertEquals("defaultMethod" + "474.474", a.s);
        }
    }

    @Test
    public void forceProtectedMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "protectedMethod", false, Optional.of(a), 474.474d);
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "protectedMethod", true, Optional.of(a), 474.474d);
            Assert.assertEquals(Optional.of("protectedMethod" + "474.474"), result);
            Assert.assertEquals("protectedMethod" + "474.474", a.s);
        }
    }

    @Test
    public void forcePublicMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "publicMethod", false, Optional.of(a), '!');
            Assert.assertEquals(Optional.of("publicMethod" + "!"), result);
            Assert.assertEquals("publicMethod" + "!", a.s);
        }

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "publicMethod", true, Optional.of(a), '!');
            Assert.assertEquals(Optional.of("publicMethod" + "!"), result);
            Assert.assertEquals("publicMethod" + "!", a.s);
        }
    }

    @Test
    public void forceIsResetAfterCall() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "privateMethod", false, Optional.of(a), (byte)0xAF);
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "privateMethod", true, Optional.of(a), (byte)0xAF);
            Assert.assertEquals(Optional.of("privateMethod" + "-81"), result);
            Assert.assertEquals("privateMethod" + "-81", a.s);
        }

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "privateMethod", false, Optional.of(a), (byte)0xAF);
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }

    }

    public static class A {
        public String s = null;

        private String privateMethod(byte b) { return (this.s = "privateMethod" + b); }

        /*default*/ String defaultMethod(float f) { return (this.s = "defaultMethod" + f); }

        protected String protectedMethod(double d) { return (this.s = "protectedMethod" + d); }

        public String publicMethod(char c) { return (this.s = "publicMethod" + c); }

    }
}
