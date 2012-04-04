package org.apache.maven.plugin.deltacoverage.report;

import java.io.File;

import org.apache.maven.plugin.deltacoverage.report.DeltaCoverageReportCreator;
import org.junit.Assert;
import org.junit.Test;


public class DeltaCoverageReportCreatorTest {

	@Test
	public void testGetRelativePath() {

		DeltaCoverageReportCreator writer = DeltaCoverageReportCreator.getInstance();
		String REPORT_DIR = "D:/work/project/lib/coverage/snapshot/module/target";
		String COVERAGE_DIR = "D:\\work\\project\\lib\\coverage\\snapshot\\module\\site\\1.0\\module\\cobertura";

		File reportDir = new File(REPORT_DIR);
		File coverageDir = new File(COVERAGE_DIR);

		String path = writer.getRelativePath(coverageDir, reportDir);

		Assert.assertEquals(".\\..\\site\\1.0\\module\\cobertura\\", path);

	}

	@Test
	public void testGetRelativePath2() {

		DeltaCoverageReportCreator writer = DeltaCoverageReportCreator.getInstance();
		String COVERAGE_DIR = "D:/work/project/lib/coverage/snapshot/module/target";
		String REPORT_DIR = "D:\\work\\project\\lib\\coverage\\snapshot\\module\\site\\1.0\\module\\cobertura";

		File reportDir = new File(REPORT_DIR);
		File coverageDir = new File(COVERAGE_DIR);

		String path = writer.getRelativePath(coverageDir, reportDir);

		Assert.assertEquals(".\\..\\..\\..\\..\\target\\", path);

	}

	@Test
	public void testGetRelativePath3() {

		DeltaCoverageReportCreator writer = DeltaCoverageReportCreator.getInstance();
		String REPORT_DIR = "D:\\work\\project\\lib\\coverage\\snapshot\\module\\site";
		String COVERAGE_DIR = "D:\\work\\project\\lib\\coverage\\snapshot\\module\\site\\1.0\\module\\cobertura";

		File reportDir = new File(REPORT_DIR);
		File coverageDir = new File(COVERAGE_DIR);

		String path = writer.getRelativePath(coverageDir, reportDir);

		Assert.assertEquals(".\\1.0\\module\\cobertura\\", path);

	}

	@Test
	public void testGetRelativePath4() {

		DeltaCoverageReportCreator writer = DeltaCoverageReportCreator.getInstance();
		String COVERAGE_DIR = "D:\\work\\project\\lib\\coverage\\snapshot\\module\\site";
		String REPORT_DIR = "D:\\work\\project\\lib\\coverage\\snapshot\\module\\site\\1.0\\module\\cobertura";

		File reportDir = new File(REPORT_DIR);
		File coverageDir = new File(COVERAGE_DIR);

		String path = writer.getRelativePath(coverageDir, reportDir);

		Assert.assertEquals(".\\..\\..\\..\\", path);

	}

	@Test
	public void testGetRelativePath5() {

		DeltaCoverageReportCreator writer = DeltaCoverageReportCreator.getInstance();
		String COVERAGE_DIR = "D:\\work\\project\\lib\\coverage\\snapshot\\module\\site";
		String REPORT_DIR = "D:\\work\\project\\lib\\coverage\\snapshot\\module\\site";

		File reportDir = new File(REPORT_DIR);
		File coverageDir = new File(COVERAGE_DIR);

		String path = writer.getRelativePath(coverageDir, reportDir);

		Assert.assertEquals(".\\", path);

	}
}
