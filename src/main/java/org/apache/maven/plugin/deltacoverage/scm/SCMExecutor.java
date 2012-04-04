package org.apache.maven.plugin.deltacoverage.scm;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.deltacoverage.change.ClassChange;
import org.apache.maven.plugin.logging.Log;


/**
 * Interface for executing commands on version control system.
 * To support a new version control system, one needs to implement this interface.
 * Apart from supporting necessary SCM commands, this also mandates access to a logger.
 * Logger is needed to log the interaction with the SCM system.
 * 
 * {@link AbstractSCMExecutor} adapts this interface to provide access to logger. Hence
 * the actual subclasses only need to implement the necessary SCM commands.
 * 
 * @author Abhijeet_Kesarkar
 * @see AbstractSCMExecutor
 */
public interface SCMExecutor {

	/**
	 * Executes diff command to retrieve changes in source code since the specified baseline version. 
	 * @param workingDir root of source code. It is inside this folder that the command should be fired.
	 * @param baselineVersion revision to compare agains - e.g. Root_branch_10_10
	 * @return list of changes {@link ClassChange}
	 * @throws Exception
	 * 
	 */
	List<ClassChange> getDiff(File workingDir, String baselineVersion) throws Exception;
	
	/**
	 * Provides access to logger instance
	 * @return log 
	 */
	Log getLog();

}
