package edu.uic.cs474.a1;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.lang.reflect.Method;
import java.util.*;

public class A5Solution implements OverloadOverrideFinder{
    @Override
    public Map<String, Map<String, Integer>> getOverloads(Map<String, ClassOrInterfaceDeclaration> classes) {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        for(Map.Entry<String, ClassOrInterfaceDeclaration> e: classes.entrySet()) {
            Map<String, Integer> map = new HashMap<>();
            result.put(e.getKey(), map);

            for(MethodDeclaration m : e.getValue().getMethods()) {
                map.put(m.getNameAsString(), map.getOrDefault(m.getNameAsString(),0) + 1);
            }

            Set<List<String>> inheritedMethods = findInheritedMethods(e.getValue(), classes);
            inheritedMethods.add(List.of("hashCode"));
            inheritedMethods.add(List.of("toString"));
            inheritedMethods.add(List.of("equals", "java.lang.Object"));

            for(List<String> m : inheritedMethods) {
                for(MethodDeclaration declared : e.getValue().getMethods()){
                    if(m.equals(turnMethodIntoList(declared)))
                        break;

                    if(declared.getNameAsString().equals(m.get(0))) {
                        map.put(m.get(0), map.get(m.get(0)) + 1);
                        break;
                    }
                }
            }

            {
                Set<String> notOverloadedMethods = new HashSet<>();
                for(String k : map.keySet()) {
                    if(map.get(k) == 1) {
                        notOverloadedMethods.add(k);
                    }
                }
                for(String k : notOverloadedMethods)
                    map.remove(k);
            }

        }
        return result;
    }

    private Set<List<String>> findInheritedMethods(ClassOrInterfaceDeclaration c, Map<String, ClassOrInterfaceDeclaration> classes) {

        Set<List<String>> result = new HashSet<>(findImplementedMethods(c, classes));

        findInheritedMethodsRec(c, classes, result);

        return result;
    }

    private void findInheritedMethodsRec(ClassOrInterfaceDeclaration c, Map<String, ClassOrInterfaceDeclaration> classes, Set<List<String>> result) {
        for(ClassOrInterfaceType t : c.getExtendedTypes()) {
            c = classes.get(t.getNameAsString());

            result.addAll(findImplementedMethods(c,classes));

            for(MethodDeclaration m : c.getMethods()) {
                if(m.getModifiers().contains(Modifier.privateModifier()) || m.getModifiers().contains(Modifier.staticModifier()))
                    continue;
                result.add(turnMethodIntoList(m));
            }

            findInheritedMethodsRec(c, classes, result);
        }
    }

    private Set<List<String>> findImplementedMethods(ClassOrInterfaceDeclaration c, Map<String, ClassOrInterfaceDeclaration> classes) {
        Set<List<String>> result = new  HashSet<>();

        for(ClassOrInterfaceType t : c.getImplementedTypes()) {
            ClassOrInterfaceDeclaration interfaceDeclaration = classes.get(t.getNameAsString());

            for(MethodDeclaration m : interfaceDeclaration.getMethods()) {
                result.add(turnMethodIntoList(m));
            }

            findInheritedMethodsRec(interfaceDeclaration, classes, result);
        }

        return result;
    }

    private List<String> turnMethodIntoList(MethodDeclaration m) {
        List<String> result = new LinkedList<>();

        result.add(m.getNameAsString());

        for(Parameter p : m.getParameters()) {
            result.add(p.getTypeAsString());
        }

        return result;
    }

    @Override
    public Map<List<String>, Set<String>> getOverrides(Map<String, ClassOrInterfaceDeclaration> classes) {
        Map<List<String>, Set<String>> result = new HashMap<>();

        for(Map.Entry<String, ClassOrInterfaceDeclaration> e : classes.entrySet()) {
            Set<List<String>> inheritedMethods = findInheritedMethods(e.getValue(), classes);
            inheritedMethods.add(List.of("hashCode"));
            inheritedMethods.add(List.of("toString"));
            inheritedMethods.add(List.of("equals", "java.lang.Object"));
            Set<List<String>> declaredMethods = new HashSet<>();

            for(MethodDeclaration declared : e.getValue().getMethods())
                declaredMethods.add(turnMethodIntoList(declared));

            for(List<String> declared: declaredMethods) {
                if(inheritedMethods.contains(declared)) {
                    List<String> methodAsList = declared;

                    Set<String> classesthatOverridethisMethod = result.getOrDefault(methodAsList, new HashSet<>());
                    classesthatOverridethisMethod.add(e.getKey());
                    result.put(methodAsList, classesthatOverridethisMethod);
                }
            }

        }

        return result;
    }
}
