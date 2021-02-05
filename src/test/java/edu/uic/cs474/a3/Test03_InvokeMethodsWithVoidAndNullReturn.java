package edu.uic.cs474.a3;

import edu.uic.cs474.a3.Main;
import edu.uic.cs474.a3.ReflectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;


public class Test03_InvokeMethodsWithVoidAndNullReturn {

    @Test
    public void nullReturn() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.of(a) );
            Assert.assertEquals(Optional.of(ReflectionUtils.NULL), result);
            Assert.assertEquals("mA", a.s);
        }
    }

    @Test
    public void voidReturn() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.s);
            Optional<Object> result = utils.invokeMethod(A.class, "method", Optional.of(a), " CS474");
            Assert.assertEquals(Optional.of(ReflectionUtils.VOID), result);
            Assert.assertEquals("mA CS474", a.s);
        }
    }

    public static class A {
        public String s = null;

        public String method() { this.s = "mA"; return null; }

        public void method(String s) { this.s = "mA" + s; }

    }
}
