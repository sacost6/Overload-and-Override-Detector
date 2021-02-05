package edu.uic.cs474.a3;

import java.util.Optional;

public interface ReflectionUtils {
    Object NULL = new Object() {
        @Override
        public String toString() { return "NULL"; }
    };

    Object VOID = new Object() {
        @Override
        public String toString() { return "VOID"; }
    };

    default <T> Optional<Object> invokeMethod(String owner, String name, Optional<? extends T> receiver, Object ... arguments) throws Throwable {
        return invokeMethod(owner, name, false, receiver, arguments);
    }

    default <T> Optional<Object> invokeMethod(Class<? extends T> owner, String name, Optional<? extends T> receiver, Object ... arguments) throws Throwable {
        return invokeMethod(owner, name, false, receiver, arguments);
    }

    <T> Optional<Object> invokeMethod(String owner, String name, boolean force, Optional<? extends T> receiver, Object ... arguments) throws Throwable;

    <T> Optional<Object> invokeMethod(Class<? extends T> owner, String name, boolean force, Optional<? extends T> receiver, Object ... arguments) throws Throwable;
}
