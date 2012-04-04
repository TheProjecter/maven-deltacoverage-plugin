package org.apache.maven.plugin.deltacoverage.coverage;

import java.util.ArrayList;
import java.util.List;

public class MethodCoverage extends AggregateCoverage {

	private List<LineCoverage> lines;

	private String name;
	private String signature;

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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void addLine(LineCoverage lineCoverage) {
		if (lines == null) {
			lines = new ArrayList<LineCoverage>();
		}
		lines.add(lineCoverage);
	}

	public LineCoverage getLine(int number) {
		for (LineCoverage lineCoverage : lines) {
			if (lineCoverage.getNumber() == number) {
				return lineCoverage;
			}
		}
		return null;
	}

}
