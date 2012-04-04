package org.apache.maven.plugin.deltacoverage.scm;

import org.apache.maven.plugin.logging.Log;

/**
 * Singleton factory to instantiate SCM Executor.
 * 
 * @author Abhijeet_Kesarkar
 *
 */
public class SCMExecutorFactory {

	private static SCMExecutorFactory INSTANCE;
	
	/**
	 * Constants for version control systsems.
	 */
	public static final String SCM_CVS = "CVS";
	public static final String SCM_SVN = "SVN";
	
	private SCMExecutorFactory()
	{
		
	}
	/**
	 * Provides access to the singleton instance
	 * @return {@link SCMExecutorFactory} instance
	 */
	public static SCMExecutorFactory getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new SCMExecutorFactory();
		}
		return INSTANCE;
	}
	
	/**
	 * Creates instance of {@link SCMExecutor} based on repository name specified.
	 * 
	 * @param scmRepository name of the version control system.
	 * @param log maven logger instance (provided by maven using getLog() method in a Mojo)
	 * @return {@link SCMExecutor} instance
	 */
	public SCMExecutor getExecutor(String scmRepository, Log log)
	{
		if( SCM_CVS.equalsIgnoreCase(scmRepository))
		{
			return new CVSExecutor(log);
		}
		return null;
	}
	
}
