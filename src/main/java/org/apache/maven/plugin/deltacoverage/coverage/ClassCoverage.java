package org.apache.maven.plugin.deltacoverage.coverage;

import java.util.ArrayList;
import java.util.List;

public class ClassCoverage extends AggregateCoverage{

	private List<MethodCoverage> methods;
	private List<LineCoverage> lines;
	private String name;
	private String filename;

	public List<MethodCoverage> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodCoverage> methods) {
		this.methods = methods;
	}

	public List<LineCoverage> getLines() {
		return lines;
	}

	public void setLines(List<LineCoverage> lines) {
		this.lines = lines;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void addLine(LineCoverage lineCoverage) {
		if (lines == null) {
			lines = new ArrayList<LineCoverage>();
		}
		lines.add(lineCoverage);
	}

	public void addMethod(MethodCoverage methodCoverage) {
		if (methods == null) {
			methods = new ArrayList<MethodCoverage>();
		}
		methods.add(methodCoverage);
	}

	public LineCoverage getLine(int number) {
		for (LineCoverage lineCoverage : lines) {
			if (lineCoverage.getNumber() == number) {
				return lineCoverage;
			}
		}
		return null;
	}

	public MethodCoverage getMethod(int number) {

		if (methods != null) {
			for (MethodCoverage methodCoverage : methods) {

				if (methodCoverage.getLine(number) != null) {
					return methodCoverage;
				}
			}
		}
		return null;
	}

	public MethodCoverage getMethod(String name, String signature) {
		if (methods != null) {
			for (MethodCoverage methodCoverage : methods) {

				if (methodCoverage.getName().equals(name) && methodCoverage.getSignature().equals(signature)) {
					return methodCoverage;
				}
			}
		}
		return null;
	}

}
