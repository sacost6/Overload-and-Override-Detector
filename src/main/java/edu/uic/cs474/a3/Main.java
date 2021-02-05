package edu.uic.cs474.a3;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public Main() { /* Empty */ }

    public static ReflectionUtils getReflectionUtils() {
        throw new Error("Not implemented");
    }

    public static OverloadOverrideFinder getMain() {
        throw new Error("Not implemented");
    }

    private Map<String, ClassOrInterfaceDeclaration> classes = new HashMap<>();

    public final void processClass(String className) {
        // Get file for target class
        Path baseDir = Paths.get(System.getProperty("user.dir"));
        Path testFolder = Paths.get(baseDir.toAbsolutePath().toString(), "src", "test", "java");
        Path filePath = Paths.get(testFolder.toAbsolutePath().toString(), className.replace(".", File.separator) + ".java");

        // Parse Java file
        CompilationUnit cu;
        try {
            cu = new JavaParser().parse(filePath).getResult().orElseThrow();
        } catch (IOException e) {
            throw new Error(e);
        }


        // Get nested classes
        Map<String, ClassOrInterfaceDeclaration> classes = new HashMap<>();
        for (TypeDeclaration<?> t : cu.getTypes()) {
            for (BodyDeclaration<?> b : t.getMembers()) {
                if (b.isClassOrInterfaceDeclaration()) {
                    ClassOrInterfaceDeclaration c = b.asClassOrInterfaceDeclaration();
                    this.classes.put(c.getNameAsString(), c);
                }
            }
        }
    }

    public Map<String, Map<String, Integer>> getOverloads() {
        return Main.getMain().getOverloads(this.classes);
    }

    public Map<List<String>, Set<String>> getOverrides() {
        return Main.getMain().getOverrides(this.classes);
    }
}
