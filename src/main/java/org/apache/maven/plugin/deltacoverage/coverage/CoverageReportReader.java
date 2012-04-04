package org.apache.maven.plugin.deltacoverage.coverage;

import java.io.File;

import com.thoughtworks.xstream.XStream;

/**
 * Class to read existing code coverage report and populates in memory graph of current code coverage.
 * Currently supports Cobertura code coverage report format.
 * 
 * @author Abhijeet_Kesarkar
 *
 * @see ModuleCoverage
 */
public class CoverageReportReader {

	/**
	 * Reads the specified code coverage report in xml format.
	 * Uses xstream to convert from XML to java object.
	 * Xstream aliases are used to bridge gap between xml format and java object structure.
	 * 
	 * @param coverageReport path to existing xml coverage report file
	 * @return {@link ModuleCoverage} instance
	 */
	public static ModuleCoverage getCoverageData(File coverageReport)
	{
		XStream xstream = new XStream();
		xstream.alias("coverage", ModuleCoverage.class);
		xstream.alias("package", PackageCoverage.class);
		xstream.alias("class", ClassCoverage.class);
		xstream.alias("method", MethodCoverage.class);
		xstream.alias("line", LineCoverage.class);
		xstream.alias("condition", ConditionCoverage.class);
		xstream.alias("source", String.class);
		xstream.useAttributeFor(ModuleCoverage.class, "timestamp");
		xstream.useAttributeFor(ModuleCoverage.class, "lineRate");
		xstream.useAttributeFor(ModuleCoverage.class, "branchRate");
		xstream.useAttributeFor(ModuleCoverage.class, "version");
        
		xstream.useAttributeFor(PackageCoverage.class, "name");
		xstream.useAttributeFor(PackageCoverage.class, "lineRate");
		xstream.useAttributeFor(PackageCoverage.class, "branchRate");
		xstream.useAttributeFor(PackageCoverage.class, "complexity");

		xstream.useAttributeFor(ClassCoverage.class, "name");
		xstream.useAttributeFor(ClassCoverage.class, "filename");
		xstream.useAttributeFor(ClassCoverage.class, "lineRate");
		xstream.useAttributeFor(ClassCoverage.class, "branchRate");
		xstream.useAttributeFor(ClassCoverage.class, "complexity");
		
		xstream.useAttributeFor(MethodCoverage.class, "name");
		xstream.useAttributeFor(MethodCoverage.class, "signature");
		xstream.useAttributeFor(MethodCoverage.class, "lineRate");
		xstream.useAttributeFor(MethodCoverage.class, "branchRate");
		
		xstream.useAttributeFor(LineCoverage.class, "number");
		xstream.useAttributeFor(LineCoverage.class, "hits");
		xstream.useAttributeFor(LineCoverage.class, "branch");
		xstream.useAttributeFor(LineCoverage.class, "conditionCoverage");
		
		xstream.useAttributeFor(ConditionCoverage.class, "number");
		xstream.useAttributeFor(ConditionCoverage.class, "type");
		xstream.useAttributeFor(ConditionCoverage.class, "coverage");
		
        xstream.aliasAttribute("condition-coverage", "conditionCoverage");
        xstream.aliasAttribute("line-rate", "lineRate");
        xstream.aliasAttribute("branch-rate", "branchRate");
        
		return (ModuleCoverage) xstream.fromXML(coverageReport);
	}
}
