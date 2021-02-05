package edu.uic.cs474.a2;


import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.Random;

public class Test04_PrimitiveTypes {

    @Test
    public void testByte() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.n);
            Optional<Object> result = utils.invokeMethod(A.class, "byteM", Optional.of(a), ((byte)2));
            Assert.assertEquals((byte)3, result.get());
            Assert.assertEquals((byte)3, a.n);
        }
    }

    @Test
    public void testShort() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.n);
            short s = (short) new Random().nextInt();
            Optional<Object> result = utils.invokeMethod(A.class, "shortM", Optional.of(a), s);
            Assert.assertEquals((short)(s + 474), result.get());
            Assert.assertEquals((short)(s + 474), a.n);
        }
    }

    @Test
    public void testInt() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.n);
            int i = new Random().nextInt();
            Optional<Object> result = utils.invokeMethod(A.class, "intM", Optional.of(a), i);
            Assert.assertEquals((i + 474474), result.get());
            Assert.assertEquals((i + 474474), a.n);
        }
    }

    @Test
    public void testLong() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.n);
            long l = new Random().nextLong();
            Optional<Object> result = utils.invokeMethod(A.class, "longM", Optional.of(a), l);
            Assert.assertEquals((l + 474474474474L), result.get());
            Assert.assertEquals((l + 474474474474L), a.n);
        }
    }

    @Test
    public void testFloat() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.n);
            float f = new Random().nextFloat();
            Optional<Object> result = utils.invokeMethod(A.class, "floatM", Optional.of(a), f);
            Assert.assertEquals((f + 474.474F), result.get());
            Assert.assertEquals((f + 474.474F), a.n);
        }
    }

    @Test
    public void testDouble() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.n);
            double d = new Random().nextDouble();
            Optional<Object> result = utils.invokeMethod(A.class, "doubleM", Optional.of(a), d);
            Assert.assertEquals((d + 474474474.474474474D), result.get());
            Assert.assertEquals((d + 474474474.474474474D), a.n);
        }
    }

    @Test
    public void testChar() throws Throwable {
        ReflectionUtils utils = Main.getReflectionUtils();

        {
            A a = new A();
            Assert.assertNull(a.n);
            char c = 'a';
            Optional<Object> result = utils.invokeMethod(A.class, "charM", Optional.of(a), c);
            Assert.assertEquals('A', result.get());
            Assert.assertEquals('A', (char) a.n.intValue());
        }
    }

    public static class A {
        public Number n = null;

        public Number byteM(byte b) { return (this.n = (byte)(b | 1)); }

        public Number shortM(short s) { return (this.n = (short)(s + 474)); }

        public Number intM(int s) { return (this.n = (s + 474474)); }

        public Number longM(long l) { return (this.n = (l + 474474474474L)); }

        public Number floatM(float f) { return (this.n = (f + 474.474F)); }

        public Number doubleM(double d) { return (this.n = (d + 474474474.474474474D)); }

        public Character charM(char c) { return (char)(this.n = (c & 0x5F)).intValue(); }

    }
}
