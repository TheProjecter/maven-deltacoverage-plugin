package org.apache.maven.plugin.deltacoverage.coverage;

public class AggregateCoverage {

	private double lineRate;
	private double branchRate;
	private double complexity;

	private int totalLines;
	private int hitLines;

	private int totalBranches;
	private int hitBranches;

	public double getLineRate() {
		return lineRate;
	}

	public void setLineRate(double lineRate) {
		this.lineRate = lineRate;
	}

	public double getBranchRate() {
		return branchRate;
	}

	public void setBranchRate(double branchRate) {
		this.branchRate = branchRate;
	}

	public double getComplexity() {
		return complexity;
	}

	public void setComplexity(double complexity) {
		this.complexity = complexity;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}

	public int getHitLines() {
		return hitLines;
	}

	public void setHitLines(int hitLines) {
		this.hitLines = hitLines;
	}

	public int getTotalBranches() {
		return totalBranches;
	}

	public void setTotalBranches(int totalBranches) {
		this.totalBranches = totalBranches;
	}

	public int getHitBranches() {
		return hitBranches;
	}

	public void setHitBranches(int hitBranches) {
		this.hitBranches = hitBranches;
	}

}
