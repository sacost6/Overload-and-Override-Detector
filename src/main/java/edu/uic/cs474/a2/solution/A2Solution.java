package edu.uic.cs474.a2.solution;

import edu.uic.cs474.a2.ReflectionUtils;

import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Ref;
import java.util.*;

public class A2Solution implements ReflectionUtils {
    @Override
    public <T> Optional<Object> invokeMethod(String owner, String name, boolean force, Optional<? extends T> receiver, Object... arguments) throws Throwable {
        try {
            return invokeMethod(Class.forName(owner), name, force, receiver, arguments);
        }
        catch(ClassNotFoundException e) {
            return Optional.empty();
        }
        catch (RuntimeException e) {
            throw e;
        }

    }

    @Override
    public <T> Optional<Object> invokeMethod(Class<? extends T> owner, String name, boolean force, Optional<? extends T> receiver, Object... arguments) throws Throwable {

        Object o;
        o = new Object();
        boolean found = false;
        if(arguments.length > 0) {
            List<Method> n = new ArrayList<>(Arrays.asList(owner.getDeclaredMethods()));
            if(!owner.getSuperclass().getSimpleName().equals("Object")) {
                Class<?> temp = owner;
                while(!temp.getSuperclass().getSimpleName().equals("Object")) {
                    n.addAll(Arrays.asList(temp.getSuperclass().getDeclaredMethods()));
                    temp = temp.getSuperclass();
                }

            }
            for(Method m : n) {
                if(name.equals(m.getName())) {
                    found = true;
                    Integer i = 0;
                    if(m.getParameterCount() == arguments.length) {
                        if(receiver.equals(Optional.empty())) {
                        }
                        if(force) {
                            try {
                                m.setAccessible(true);
                                if(receiver.equals(Optional.empty())) {
                                    if(!owner.getSuperclass().getSimpleName().equals("Object")) {
                                        Class<?> temp = owner;
                                        while(!temp.getSuperclass().getSimpleName().equals("Object")) {
                                            temp = temp.getSuperclass();
                                        }
                                        return Optional.of(m.invoke(Optional.of(owner.getSuperclass()), arguments));
                                    }
                                }
                                return Optional.of(m.invoke(receiver.get(), arguments));
                            }
                            catch (IllegalAccessException e) {
                            }
                        }
                        for(Parameter p : m.getParameters()) {
                            if(!p.getType().getSimpleName().equals(arguments[i].getClass().getSimpleName())) {
                                try {
                                    o = m.invoke(receiver.get(), arguments);
                                }
                                catch(Exception e) {
                                    if(e.getMessage().contains("mismatch"))
                                        continue;
                                    return Optional.empty();
                                }
                                if(m.getReturnType().getSimpleName().equals("void"))
                                    return Optional.of(VOID);
                                else return Optional.of(o);
                            }
                            i++;
                        }
                        if(m.getReturnType().getSimpleName().equals("void")) {
                            try {

                                m.invoke(receiver.get(), arguments);
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                return Optional.empty();
                            }

                            catch(InvocationTargetException e) {
                                if(e.getTargetException().getClass().getSuperclass().getSimpleName().equals("RuntimeException")) {
                                    throw e.getTargetException();
                                }
                                else {
                                    return Optional.of(e.getTargetException());
                                }
                            }
                            return Optional.of(ReflectionUtils.VOID);
                        }

                        try {
                            if(receiver.equals(Optional.empty())) {
                                var temp = Optional.of(owner);
                                o = m.invoke(temp, arguments);
                            }
                            else
                                o = m.invoke(receiver.get(), arguments);
                        }
                        catch (RuntimeException ex){

                        }
                    }

                }
            }
          /*  if(!receiver.equals(Optional.of(name))) {
                return Optional.empty();
            }*/
            if(!found)
                return Optional.empty();
        }
       else {
            if(receiver.equals(Optional.empty())) {
                var temp1 = Optional.of(owner);
                Method n = owner.getMethod(name);
                o = n.invoke(temp1, arguments);
                return Optional.of(o);
            }
            try {
                Method n = owner.getMethod(name);

                o = n.invoke(receiver.get());
            }
            catch(NoSuchElementException e1) {
                return Optional.empty();
            }
            catch (InvocationTargetException e) {
            }
            catch(NoSuchMethodException ex) {
                return Optional.empty();
            }
        }
       if(o == null) {
           return Optional.of(ReflectionUtils.NULL);
       }
       else {
           return Optional.of(o);
       }
    }
}
