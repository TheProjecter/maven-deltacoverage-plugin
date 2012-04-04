package org.apache.maven.plugin.deltacoverage;

import java.io.File;

import junit.framework.Assert;

import org.apache.maven.plugin.deltacoverage.DeltaCoverageMojo;
import org.apache.maven.plugin.deltacoverage.scm.SCMExecutorFactory;
import org.junit.Test;

public class DeltaCoverageMojoTest {

	public static final String COVERAGE_REPORT = "src\\test/resources/site/cobertura/coverage.xml";
	public static final String SOURCE_DIR = "src\\test\\resources";
	public static final String REPORT_DIR = "target";

	@Test
	public void testExecute() throws Exception {
		File reportFile = new File(REPORT_DIR, "DeltaCoverageReport.html");
		if (reportFile.exists()) {
			reportFile.delete();
		}
		DeltaCoverageMojo mojo = new DeltaCoverageMojo();
		mojo.setBaselineVersion("Root_branch_rel_12_10");
		mojo.setCoverageReport(new File(COVERAGE_REPORT));
		mojo.setSourceDir(new File(SOURCE_DIR));
		mojo.setReportDir(new File(REPORT_DIR));
		mojo.setScm(SCMExecutorFactory.SCM_CVS);
		mojo.execute();
		Assert.assertTrue(reportFile.exists());
	}
}
