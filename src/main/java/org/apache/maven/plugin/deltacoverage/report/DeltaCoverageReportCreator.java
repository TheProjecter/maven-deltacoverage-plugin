package org.apache.maven.plugin.deltacoverage.report;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.deltacoverage.coverage.ModuleCoverage;
import org.apache.maven.plugin.deltacoverage.util.ClassPathUtil;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Generates report based on delta coverage. Uses Freemarker for report generation.
 * Reports are generated based on freemarker templates found in the package <code>org/apache/maven/plugin/deltacoverage/report/</code> in class path.
 * To create new type of report, simply add a new template. 
 * Note: 
 * <li>The report name is derived from the template name (by simply removing the <code>.ftl</code> template extension)</li>
 * <li>Relative path to existing code coverage reports is available as variable <code>relativeCoveragePath</code>. 
 * This can be used to create links to existing coverage reports from the delta coverage report</li>
 * 
 * @author Abhijeet_Kesarkar
 *
 */
public class DeltaCoverageReportCreator {

	private static final String KEY_COVERAGE = "coverage";
	private static final String KEY_RELATIVE_COVERAGE_PATH = "relativeCoveragePath";
	private static final String TEMPLATE_SUFFIX = ".ftl";
	private static final String TEMPLATE_ROOT = "org/apache/maven/plugin/deltacoverage/report/";
	private static DeltaCoverageReportCreator INSTANCE;
	private Configuration cfg;

	/**
	 * Constructor with initialization of freemarker configuration.
	 */
	private DeltaCoverageReportCreator() {
		cfg = new Configuration();
		cfg.setClassForTemplateLoading(DeltaCoverageReportCreator.class, "");
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

	/**
	 * Provides access to the singleton instance
	 * @return singleton instance
	 */
	public static DeltaCoverageReportCreator getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DeltaCoverageReportCreator();
		}
		return INSTANCE;
	}

	/**
	 * Generates reports for the specified delta coverage. The reports are generated in the
	 * specified <code>reportDir</code>. Relative path to existing code coverage reports is available 
	 * as variable <code>relativeCoveragePath</code>. This can be used to create links to existing coverage reports 
	 * from the delta coverage report.
	 * 
	 * @param deltaCoverage {@link ModuleCoverage} instance indicating the delta coverage.
	 * @param reportDir folder in which report will be generated
	 * @param coverageDir folder containing existing coverage reports
	 * @throws Exception in case of IO error
	 */
	public void createReport(ModuleCoverage deltaCoverage, File reportDir, File coverageDir) throws Exception{

		List<String> templateNames = ClassPathUtil.getTemplateNames(TEMPLATE_ROOT, TEMPLATE_SUFFIX);

		for (String templateName : templateNames) {

			Writer out = new FileWriter(new File(reportDir, getReportName(templateName)));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(KEY_COVERAGE, deltaCoverage);
			map.put(KEY_RELATIVE_COVERAGE_PATH, getRelativePath(coverageDir, reportDir));

			Template template = cfg.getTemplate(templateName);
			template.process(map, out);

		}

	}

	/**
	 * Calculates relative path between coverage dir and report dir.
	 * e.g. if coverage dir is <code>/x/y/site/coverage</code> and report dir is <code>/x/y/target</code>,
	 * then this will return <code>../site/coverage/</code>
	 * 
	 * @param coverageDir folder containing to existing coverage reports
	 * @param reportDir folder in which reports will be generated
	 * @return relative path between reportDir and coverageDir
	 */
	String getRelativePath(File coverageDir, File reportDir) {
		String[] reportPaths = reportDir.getAbsolutePath().split("\\" + File.separator);
		String[] coveragePaths = coverageDir.getAbsolutePath().split("\\" + File.separator);
		StringBuilder builder = new StringBuilder(".");
		int i = 0;
		boolean different = false;
		for (; i < coveragePaths.length; i++) {
			if (i >= reportPaths.length || !coveragePaths[i].equals(reportPaths[i])) {
				for (int j = i; j < reportPaths.length; j++) {
					builder.append(File.separator + "..");
				}
				for (int j = i; j < coveragePaths.length; j++) {
					builder.append(File.separator + coveragePaths[j]);
				}
				different = true;
				break;
			}
		}
		if (i < reportPaths.length && !different) {
			for (int j = i; j < reportPaths.length; j++) {
				builder.append(File.separator + "..");
			}
		}
		builder.append(File.separator);

		return builder.toString();
	}

	/**
	 * Extracts report name from template name by simply removing the <code>.ftl</code> template extension.
	 * 
	 * @param templateName template file name like <code>DeltaCodeCoverage.html.ftl</code> 
	 * @return report name like <code>DeltaCodeCoverage.html</code> 
	 */
	private String getReportName(String templateName) {
		String reportName = null;
		File file = new File(templateName);
		reportName = file.getName();
		return reportName.replace(TEMPLATE_SUFFIX, "");
	}

}
