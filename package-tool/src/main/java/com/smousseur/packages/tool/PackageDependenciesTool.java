package com.smousseur.packages.tool;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.io.FileUtils;

public class PackageDependenciesTool {
  public static void main(String[] args) throws IOException {
    Map<String, String> params = getParams(args);
    List<String> packagesToFilter = new ArrayList<>(List.of("java.lang", "java.util", "lombok"));
    Optional.ofNullable(params.get("filter"))
        .map(filter -> filter.split(","))
        .map(List::of)
        .ifPresent(packagesToFilter::addAll);
    List<String> projectRootPackages = new ArrayList<>();
    Optional.ofNullable(params.get("projectPackages"))
        .map(filter -> filter.split(","))
        .map(List::of)
        .ifPresent(projectRootPackages::addAll);

    String packagesToExclude = "com.smousseur.packages.tool";
    JavaClasses classes = new ClassFileImporter().importPath(".");
    Map<String, Set<String>> dependencies = new HashMap<>();
    classes.forEach(
        classz -> {
          String packageName = classz.getPackageName();
          classz.getDirectDependenciesFromSelf().stream()
              .filter(
                  dep ->
                      !packageName.equals(dep.getTargetClass().getPackageName())
                          && !packageName.startsWith(packagesToExclude))
              .forEach(
                  dep -> {
                    String targetPkg = dep.getTargetClass().getPackageName();
                    Set<String> depList =
                        dependencies.computeIfAbsent(packageName, k -> new HashSet<>());
                    if (!packagesToFilter.contains(targetPkg)
                        && !targetPkg.startsWith(packagesToExclude)) {
                      depList.add(targetPkg);
                    }
                  });
        });
    DependencyAggregator aggregator = new DependencyAggregator(dependencies, projectRootPackages);
    FileUtils.writeStringToFile(
        new File("target\\package-dependencies.dot"),
        aggregator.convertToDot(),
        StandardCharsets.UTF_8);
  }

  private static Map<String, String> getParams(String[] args) {
    Map<String, String> params = new HashMap<>();
    Arrays.stream(args)
        .filter(arg -> arg.startsWith("-D") && arg.contains("="))
        .forEach(
            arg -> {
              String[] argParts = arg.substring(2).split("=");
              params.put(argParts[0], argParts[1]);
            });
    return params;
  }
}
