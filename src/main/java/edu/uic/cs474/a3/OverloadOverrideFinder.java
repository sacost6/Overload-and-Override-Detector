package edu.uic.cs474.a3;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OverloadOverrideFinder {

    Map<String, Map<String, Integer>> getOverloads(Map<String, ClassOrInterfaceDeclaration> classes);

    Map<List<String>, Set<String>> getOverrides(Map<String, ClassOrInterfaceDeclaration> classes);

}
