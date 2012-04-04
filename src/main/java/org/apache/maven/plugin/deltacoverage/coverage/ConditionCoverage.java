package org.apache.maven.plugin.deltacoverage.coverage;

public class ConditionCoverage {

	private int number;
	private String type;
	private String coverage;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	@Override
	public String toString() {
		return "ConditionCoverage [coverage=" + coverage + ", number=" + number + ", type=" + type + "]";
	}
	
	
}
