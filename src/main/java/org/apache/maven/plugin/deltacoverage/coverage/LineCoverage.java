package org.apache.maven.plugin.deltacoverage.coverage;

import java.util.List;

public class LineCoverage {

	private int number;
	private int hits;
	private boolean branch;
	private String conditionCoverage;
	private String value;
	
	private List<ConditionCoverage> conditions;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public boolean isBranch() {
		return branch;
	}

	public void setBranch(boolean branch) {
		this.branch = branch;
	}

	public String getConditionCoverage() {
		return conditionCoverage;
	}

	public void setConditionCoverage(String conditionCoverage) {
		this.conditionCoverage = conditionCoverage;
	}

	public List<ConditionCoverage> getConditions() {
		return conditions;
	}

	public void setConditions(List<ConditionCoverage> conditions) {
		this.conditions = conditions;
	}

	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "LineCoverage [branch=" + branch + ", conditionCoverage=" + conditionCoverage + ", hits=" + hits + ", number=" + number + "]";
	}
	
}
