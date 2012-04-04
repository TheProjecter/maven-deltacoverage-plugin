package org.apache.maven.plugin.deltacoverage.coverage;

import java.util.ArrayList;
import java.util.List;

public class ModuleCoverage extends AggregateCoverage {

	private List<String> sources;
	private List<PackageCoverage> packages = new ArrayList<PackageCoverage>();

	private long timestamp = System.currentTimeMillis();
	private String version = "";

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	public List<PackageCoverage> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageCoverage> packages) {
		this.packages = packages;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void addPackage(PackageCoverage packageCoverage) {
		if (packages == null) {
			packages = new ArrayList<PackageCoverage>();
		}
		packages.add(packageCoverage);

	}

	public PackageCoverage getPackage(String name) {
		if (packages != null) {
			for (PackageCoverage packageCoverage : packages) {
				if (packageCoverage.getName().equals(name)) {
					return packageCoverage;
				}
			}
		}
		return null;
	}

}
