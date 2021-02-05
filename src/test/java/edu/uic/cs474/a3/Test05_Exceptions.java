package edu.uic.cs474.a3;

import edu.uic.cs474.a3.Main;
import edu.uic.cs474.a3.ReflectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class Test05_Exceptions {

    @Test
    public void classDoesNotExist() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            Optional<Object> result = utils.invokeMethod("ThisClassNameDoesNotExist", "privateMethod", Optional.of("Nothing"));
            Assert.assertEquals(Optional.empty(), result);
        }
    }

    @Test
    public void callPrivateMethod() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "privateMethod", Optional.of(a));
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }
    }

    @Test
    public void callMethodThatDoesNotExist() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "methodThatDoesNotExist", Optional.of(a));
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }
    }

    @Test
    public void callMethodWithWrongArguments() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "publicMethod", Optional.of(a), 474);
            Assert.assertEquals(Optional.of(ReflectionUtils.VOID), result);
            Assert.assertEquals("public 474", a.s);
        }

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "publicMethod", Optional.of(a), "wrong argument type");
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }
    }

    @Test
    public void callMethodWithWrongReceiver() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "publicMethod", Optional.of("474"), 474);
            Assert.assertEquals(Optional.empty(), result);
            Assert.assertNull(a.s);
        }
    }

    @Test
    public void methodThrowsCheckedException() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.e);
            Optional<Object> result = utils.invokeMethod(A.class, "throwsCheckedException", Optional.of(a), " 474" );
            Assert.assertNotNull(a.e);
            Assert.assertEquals(Optional.of(a.e), result);
            Assert.assertEquals("Checked" + " 474", ((Checked)result.get()).getMessage());
            Assert.assertEquals("Checked" + " 474", a.e.getMessage());
        }
    }

    @Test
    public void methodThrowsUncheckedException() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.u);
            try {
                Optional<Object> result = utils.invokeMethod(A.class, "throwsUncheckedException", Optional.of(a), " 474" );
                Assert.fail();
            } catch(Unchecked e) {
                Assert.assertNotNull(a.u);
                Assert.assertEquals(a.u, e);
                Assert.assertEquals("Unchecked" + " 474", e.getMessage());
                Assert.assertEquals("Unchecked" + " 474", a.u.getMessage());
            }
        }
    }

    public static class A {
        public String s = null;
        public Checked e;
        public Unchecked u;

        private String privateMethod() { this.s = "private"; return null; }

        public void publicMethod(int i) { this.s = "public " + i; }

        // public String methodThatDoesNotExist() { this.s = "impossible"; return null; }

        public void throwsCheckedException(String s) throws Checked { throw (this.e = new Checked("Checked" + s)); }

        public void throwsUncheckedException(String s) { throw (this.u = new Unchecked("Unchecked" + s)); }

    }

    public static class Checked extends Exception {
        public Checked(String message) {
            super(message);
        }
    }

    public static class Unchecked extends RuntimeException {
        public Unchecked(String message) {
            super(message);
        }
    }
}
