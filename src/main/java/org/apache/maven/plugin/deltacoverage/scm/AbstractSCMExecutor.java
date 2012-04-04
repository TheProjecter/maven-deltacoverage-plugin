package org.apache.maven.plugin.deltacoverage.scm;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.deltacoverage.change.ClassChange;
import org.apache.maven.plugin.logging.Log;


/**
 * Adapter for the {@link SCMExecutor} interface.
 * Stores and provides access to the logger instance.
 * The actual SCM command methods are not implemented and remain abstract.
 * The subclasses specific to different SCM repositories are required to implement only these abstract command methods.
 * 
 * @author Abhijeet_Kesarkar
 *
 */
public abstract class AbstractSCMExecutor implements SCMExecutor{

	private Log log;
	
	/**
	 * Constructor with mandatory arguments
	 * @param log logger instance
	 */
	public AbstractSCMExecutor(Log log)
	{
		this.log = log;
	}
	
	@Override
	public abstract List<ClassChange> getDiff(File workingDir, String baselineVersion) throws Exception ;

	@Override
	public Log getLog() {
		return log;
	}

}
