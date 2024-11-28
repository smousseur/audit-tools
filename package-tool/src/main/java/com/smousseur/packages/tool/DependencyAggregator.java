package com.smousseur.packages.tool;

import java.util.*;
import org.apache.commons.lang3.tuple.Pair;

public class DependencyAggregator {
  private static final String EOL = ";";
  private static final String NEWLINE = "\n";
  private static final String ARROW = " -> ";
  private static final String TAB = "  ";

  private final Map<String, Set<String>> dependenciesMap;
  private final List<String> projectRootPackages;

  public DependencyAggregator(
      Map<String, Set<String>> dependenciesMap, List<String> projectRootPackages) {
    this.dependenciesMap = dependenciesMap;
    this.projectRootPackages = projectRootPackages;
  }

  public String convertToDot() {
    List<String> packages =
        new ArrayList<>(this.dependenciesMap.keySet())
            .stream().sorted(Comparator.naturalOrder()).toList();
    List<Pair<String, String>> dependencies = new ArrayList<>();
    dependenciesMap.forEach(
        (key, value) -> value.forEach(target -> dependencies.add(Pair.of(key, target))));
    StringBuilder builder = new StringBuilder("digraph Dependencies {").append(NEWLINE);
    packages.forEach(
        pkg -> {
          String style = "";
          boolean isProjectPackage = this.projectRootPackages.stream().anyMatch(pkg::startsWith);
          if (isProjectPackage) {
            style = " [shape=\"box\", style= \"filled, rounded\", fillcolor=\"cornflowerblue\"]";
          }
          builder.append(TAB).append(quotedString(pkg)).append(style).append(EOL).append(NEWLINE);
        });
    dependencies.forEach(
        dependency ->
            builder
                .append(TAB)
                .append(quotedString(dependency.getLeft()))
                .append(ARROW)
                .append(quotedString(dependency.getRight()))
                .append(EOL)
                .append(NEWLINE));
    builder.append("}").append(NEWLINE);
    return builder.toString();
  }

  private String quotedString(String string) {
    return "\"" + string + "\"";
  }
}
