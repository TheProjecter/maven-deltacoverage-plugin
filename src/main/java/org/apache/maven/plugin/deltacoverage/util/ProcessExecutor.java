package org.apache.maven.plugin.deltacoverage.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class ProcessExecutor {

	private static final String COMMANDS = "commands.properties";
	private static ProcessExecutor INSTANCE;
	private Properties properties = new Properties();

	private ProcessExecutor() {
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream(COMMANDS));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize Process Executor", e);
		}
	}

	public static ProcessExecutor getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ProcessExecutor();
		}
		return INSTANCE;
	}

	public ProcessBuilder buildCommand(String commandName, Map<String, String> replaceArgs, File workingDir) throws IOException{
		String commandArgLine = properties.getProperty(commandName);
		if (commandArgLine == null) {
			commandArgLine = properties.getProperty(commandName + "_" + System.getenv("OS"));
			if (commandArgLine == null) {
				throw new RuntimeException("Could not find command : '" + commandName + "', OS : " + System.getProperty("OS"));
			}
		}
		String[] commandArgs = commandArgLine.split(" ");

		if (replaceArgs != null && replaceArgs.size() > 0) {
			for (int i = 0; i < commandArgs.length; i++) {
				String commandArg = commandArgs[i];
				if (replaceArgs.get(commandArg) != null) {
					commandArgs[i] = replaceArgs.get(commandArg);
				}
			}
		}
		ProcessBuilder builder = new ProcessBuilder(commandArgs);
		builder.directory(workingDir);
		builder.redirectErrorStream(true);
		return builder;
	}
}
