package org.apache.maven.plugin.deltacoverage.util;

import java.util.List;

import junit.framework.Assert;

import org.apache.maven.plugin.deltacoverage.util.ClassPathUtil;
import org.junit.Test;



public class ClassPathUtilTest{

	@Test
	public void testGetTemplateNames() throws Exception{

		String templateSuffix = ".ftl";
		String templateRoot = "org/apache/maven/plugin/deltacoverage/report/";
		List<String> templateNames = ClassPathUtil.getTemplateNames(templateRoot, templateSuffix);
		Assert.assertNotNull(templateNames);
		Assert.assertEquals(3, templateNames.size());
	}
	
	@Test
	public void testGetTemplateNamesJar() throws Exception{

		String templateSuffix = ".class";
		String templateRoot = "freemarker/ext/util/";
		List<String> templateNames = ClassPathUtil.getTemplateNames(templateRoot, templateSuffix);
		Assert.assertNotNull(templateNames);
		Assert.assertEquals(11, templateNames.size());
	}
	
}
