package org.apache.maven.plugin.deltacoverage.coverage;

import java.util.ArrayList;
import java.util.List;

public class PackageCoverage extends AggregateCoverage {

	private List<ClassCoverage> classes;
	private String name;

	public List<ClassCoverage> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassCoverage> classes) {
		this.classes = classes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addClass(ClassCoverage classCoverage) {
		if (classes == null) {
			classes = new ArrayList<ClassCoverage>();
		}
		classes.add(classCoverage);
	}

	public ClassCoverage getClass(String name) {
		for (ClassCoverage classCoverage : classes) {
			if (classCoverage.getName().equals(name)) {
				return classCoverage;
			}
		}
		return null;
	}

}
