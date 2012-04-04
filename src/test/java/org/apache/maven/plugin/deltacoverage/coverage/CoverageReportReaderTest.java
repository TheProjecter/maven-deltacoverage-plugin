package org.apache.maven.plugin.deltacoverage.coverage;

import java.io.File;

import org.apache.maven.plugin.deltacoverage.coverage.CoverageReportReader;
import org.apache.maven.plugin.deltacoverage.coverage.ModuleCoverage;
import org.junit.Assert;
import org.junit.Test;



public class CoverageReportReaderTest{

	public static final String COVERAGE_REPORT = "src/test/resources/site/cobertura/coverage.xml";
	@Test
	public void testGetCoverageData() throws Exception
	{
		ModuleCoverage moduleCoverage = CoverageReportReader.getCoverageData(new File(COVERAGE_REPORT));
		Assert.assertNotNull(moduleCoverage);
		
	}
}
