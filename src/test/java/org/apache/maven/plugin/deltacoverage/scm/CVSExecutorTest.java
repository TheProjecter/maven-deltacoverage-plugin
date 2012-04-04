package org.apache.maven.plugin.deltacoverage.scm;

import java.io.File;
import java.util.List;


import org.apache.maven.plugin.deltacoverage.change.ClassChange;
import org.apache.maven.plugin.deltacoverage.scm.SCMExecutorFactory;
import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;


public class CVSExecutorTest{

	@Test
	public void testGetCVSDiff() throws Exception {

		String sourceDir = ".\\src\\test\\resources";
		String baselineVersion = "Root_branch_rel_12_10";
		Log log = new Log() {

			@Override
			public void warn(CharSequence arg0, Throwable arg1) {
			}
			@Override
			public void warn(Throwable arg0) {
			}
			@Override
			public void warn(CharSequence arg0) {
			}
			@Override
			public boolean isWarnEnabled() {
				return false;
			}
			@Override
			public boolean isInfoEnabled() {
				return false;
			}
			@Override
			public boolean isErrorEnabled() {
				return false;
			}
			@Override
			public boolean isDebugEnabled() {
				return false;
			}
			@Override
			public void info(CharSequence arg0, Throwable arg1) {
			}
			@Override
			public void info(Throwable arg0) {
			}
			@Override
			public void info(CharSequence arg0) {
			}
			@Override
			public void error(CharSequence arg0, Throwable arg1) {
			}
			@Override
			public void error(Throwable arg0) {
			}
			@Override
			public void error(CharSequence arg0) {
			}

			@Override
			public void debug(CharSequence arg0, Throwable arg1) {
			}
			@Override
			public void debug(Throwable arg0) {
			}
			@Override
			public void debug(CharSequence arg0) {
			}
		};
		List<ClassChange> changes = SCMExecutorFactory.getInstance().getExecutor(SCMExecutorFactory.SCM_CVS, log).getDiff(new File(sourceDir), baselineVersion);

		Assert.assertNotNull(changes);
		Assert.assertEquals(3, changes.size());
		
		assertFile(changes.get(0),"org/apache/maven/plugin/deltacoverage/DeltaCoverageMojo.java", "org.apache.maven.plugin.deltacoverage.DeltaCoverageMojo", 8,52,53,54,55,56,57,64,65);
		assertFile(changes.get(1),"org/apache/maven/plugin/deltacoverage/DeltaCoverageCalculator.java", "org.apache.maven.plugin.deltacoverage.DeltaCoverageCalculator", 7, 135,136,137,138,139,140,141);
		assertFile(changes.get(2),"org/apache/maven/plugin/deltacoverage/util/ProcessExecutor.java", "org.apache.maven.plugin.deltacoverage.util.ProcessExecutor", 52);
	}

	private void assertFile(ClassChange change, String filename, String name, int size, int... lineNumbers) {
		Assert.assertEquals(filename, change.getFilename());
		Assert.assertEquals(name, change.getName());
		Assert.assertEquals(size, change.getLines().size());
		
		for (int i = 0; i < lineNumbers.length; i++) {
			Assert.assertEquals(lineNumbers[i], change.getLines().get(i).getLineNumber());	
		}
			
	}
}
