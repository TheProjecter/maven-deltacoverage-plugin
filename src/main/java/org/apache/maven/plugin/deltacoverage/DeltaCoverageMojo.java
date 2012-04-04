package org.apache.maven.plugin.deltacoverage;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.deltacoverage.change.ClassChange;
import org.apache.maven.plugin.deltacoverage.coverage.CoverageReportReader;
import org.apache.maven.plugin.deltacoverage.coverage.ModuleCoverage;
import org.apache.maven.plugin.deltacoverage.report.DeltaCoverageReportCreator;
import org.apache.maven.plugin.deltacoverage.scm.SCMExecutorFactory;


/**
 * Mojo to generate delta coverage report. Entry point for maven plugin. 
 * Delta coverage is calculated by taking into consideration current code coverage and code changed since a specific baseline version.
 * Hence mandatorily needs parameters <code>coverageReport</code> and <code>baselineVersion</code>.
 * 
 * @author Abhijeet_Kesarkar
 * @goal deltacoverage
 */
public class DeltaCoverageMojo extends AbstractMojo {

	/**
	 * Location where output report should be generated.
	 * 
	 * @parameter expression="${delta.reportDir}" default-value="${project.build.directory}"
	 */
	private File reportDir;

	/**
	 * Location where source code resides
	 * 
	 * @parameter default-value="${project.build.sourceDirectory}"
	 */
	private File sourceDir;

	/**
	 * Path of coverage report xml.
	 * 
	 * @parameter expression="${delta.coverage.reportPath}" default-value="${project.reporting.outputDirectory}/cobertura/coverage.xml"
	 */
	private File coverageReport;

	/**
	 * CVS branch/tag name to be used as baseline for delta calculation.
	 * 
	 * @parameter expression="${delta.baseline}"
	 */
	private String baselineVersion;
	
	/**
	 * The SCM repository to use. Currently supported - CVS (default).
	 * 
	 * @parameter default-value="CVS" expression="${delta.scm}"
	 */
	private String scm;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		if (coverageReport == null || !coverageReport.exists()) {
			getLog().error("No coverage report found. Configure 'coverageReport' parameter");
			return;
		}

		if (baselineVersion == null) {
			getLog().error("No baselineVersion found. Configure 'baselineVersion' parameter");
			return;
		}

		ModuleCoverage coverage = CoverageReportReader.getCoverageData(coverageReport);
		try {
			List<ClassChange> changes = SCMExecutorFactory.getInstance().getExecutor(scm, getLog()).getDiff(sourceDir, baselineVersion);
			ModuleCoverage deltaCoverage = DeltaCoverageCalculator.calculateDeltaCoverage(coverage, changes);
			DeltaCoverageReportCreator.getInstance().createReport(deltaCoverage, reportDir, coverageReport.getParentFile());
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException("Error", e);
		}
	}

	public void setReportDir(File reportDir) {
		this.reportDir = reportDir;
	}

	public void setSourceDir(File sourceDir) {
		this.sourceDir = sourceDir;
	}

	public void setCoverageReport(File coverageReport) {
		this.coverageReport = coverageReport;
	}

	public void setBaselineVersion(String baselineVersion) {
		this.baselineVersion = baselineVersion;
	}

	public void setScm(String scm) {
		this.scm = scm;
	}

}
