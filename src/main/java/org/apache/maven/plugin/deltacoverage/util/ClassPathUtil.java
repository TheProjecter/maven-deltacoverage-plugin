package org.apache.maven.plugin.deltacoverage.util;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassPathUtil {

	public static List<String> getTemplateNames(String templateRoot,final String templateSuffix) throws Exception{

		List<String> templateNames = new ArrayList<String>();
		Enumeration<URL> urls =  ClassPathUtil.class.getClassLoader().getResources(templateRoot);
		
		while(urls.hasMoreElements())
		{
			URL url = urls.nextElement();
			if( url.getProtocol().equals("jar") )
			{
				String path = url.getPath();
				path = path.substring(5,url.getPath().lastIndexOf('!'));
				ZipFile zipFile = new ZipFile(path);
				Enumeration<? extends ZipEntry> entries = zipFile.entries();				
				while(entries.hasMoreElements())
				{
					ZipEntry entry = entries.nextElement();
					String name = entry.getName();
					if( name.endsWith(templateSuffix) && name.startsWith(templateRoot))
					{
						File file = new File(entry.getName());
						templateNames.add(file.getName());
					}
				}				
			}
			else if( url.getProtocol().endsWith("file"))
			{
				File root = new File(url.getPath());
				String[] files = root.list(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(templateSuffix);
					}
				});
				for (String file : files) {
					templateNames.add(file);
				}
			}
		}
		
		return templateNames;
	}
}
