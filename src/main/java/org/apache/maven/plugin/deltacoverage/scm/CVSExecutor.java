package org.apache.maven.plugin.deltacoverage.scm;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.deltacoverage.change.ChangeType;
import org.apache.maven.plugin.deltacoverage.change.ClassChange;
import org.apache.maven.plugin.deltacoverage.change.LineChange;
import org.apache.maven.plugin.deltacoverage.util.ProcessExecutor;
import org.apache.maven.plugin.logging.Log;


/**
 * Implements the {@link SCMExecutor} interface for CVS version control system.
 * Extends {@link AbstractSCMExecutor} class to implement SCM specific methods.
 * 
 * Relies on command line execution of CVS command. Hence, it is necessary that a CVS client
 * is installed and CVS command can be executed from command line.
 * 
 * Uses {@link ProcessBuilder} to build a command. The command is then executed and the output
 * is parsed to populate list of {@link ClassChange}.
 * 
 * @author Abhijeet_Kesarkar
 *
 */
public class CVSExecutor extends AbstractSCMExecutor {

	private static final String REPLACE_VERSION = "$version";
	private static final String COMMAND_DIFF = "DIFF";

	/**
	 * Constructor
	 * @param log logger instance
	 */
	public CVSExecutor(Log log) {
		super(log);
	}

	@Override
	public List<ClassChange> getDiff(File workingDir, String baselineVersion) throws Exception {
		
		Map<String, String> replaceArgs= new HashMap<String, String>();
		replaceArgs.put(REPLACE_VERSION, baselineVersion);
		ProcessBuilder builder = ProcessExecutor.getInstance().buildCommand(SCMExecutorFactory.SCM_CVS + "_" + COMMAND_DIFF, replaceArgs, workingDir);

		getLog().info("CVS Command : " + builder.command());
		Process process = builder.start();
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(process.getInputStream()));
		
		return parseDiffOutput(reader);
	}

	/**
	 * Parses the output of the diff command to return the list of changes.
	 * @param reader reading the output of the command executed.
	 * @return list of {@link ClassChange}
	 * @throws Exception in case of IO error
	 */
	private List<ClassChange> parseDiffOutput(LineNumberReader reader) throws Exception{
		List<ClassChange> classChanges = new ArrayList<ClassChange>();
		String line = null;
		ClassChange classChange = null;
		List<LineChange> lines = null;
		int changeIndex = 0;
		while ((line = reader.readLine()) != null) {
			getLog().debug(line);
			if (isIndexLine(line)) { // e.g. Index <filename>
				classChange = getClassChange(line);
				classChanges.add(classChange);
				reader.readLine(); // ignore
				reader.readLine(); // ignore
				line = reader.readLine(); // ignore
				if (!line.startsWith("diff")) // not new file
				{
					line = reader.readLine(); // ignore
					if( !line.startsWith("diff")) // not local modification
					{
						reader.readLine(); // ignore
					}
				}
			} else if (isDiffLine(line)) { // this line contains the change description like 21,28c34,37
				ChangeType type = getChangeType(line);
				lines = null;
				changeIndex = 0;
				if (type == ChangeType.ADD || type == ChangeType.MODIFY) {
					String range = line.split("[acd]")[1];
					lines = getLineChanges(range, type);
					classChange.getLines().addAll(lines);
				}
			} else if (isChangeLine(line) && lines != null) { // this line contains the modified code (starts with > )
				lines.get(changeIndex++).setValue(line.substring(1));
			}
		}
		return classChanges;
	}

	/**
	 * Converts line range to create list of {@link LineChange} corresponding
	 * to each line changed.
	 * e.g. for input range as "12,16" indicating line numbers 12 to 16 (both inclusive),
	 * returns list of five LineChange instances numbering 12 to 16. Specified ChangeType 
	 * is set in each LineChange instance.
	 * 
	 * @param range e.g. "12,16" or simply "12"
	 * @param type {@link ChangeType} 
	 * @return list of {@link LineChange}
	 */
	private static List<LineChange> getLineChanges(String range, ChangeType type) {
		String[] ranges = range.split(",");
		int from = Integer.valueOf(ranges[0]);
		int to = (ranges.length > 1 ? Integer.valueOf(ranges[1]) : from);

		List<LineChange> lineChanges = new ArrayList<LineChange>();
		for (int i = from; i <= to; i++) {
			LineChange change = new LineChange();
			change.setLineNumber(i);
			change.setType(type);
			lineChanges.add(change);
		}
		return lineChanges;
	}

	/**
	 * Parses the change description to return the change type.
	 * e.g. 
	 * <table>
	 * 	<tr><th>Change description</th><th>Indicates</th><th>Returns</th></tr>
	 * 	<tr><td>9a13,15</td><td>Lines 13 to 15 added after line 9</td><td>ChangeType.ADD</td></tr>
	 * 	<tr><td>21,25c26</td><td>Line numbers 21 to 25 changed with line 26</td><td>ChangeType.MODIFY</td></tr>
	 * 	<tr><td>33d0</td><td>Line numbers 33 deleted</td><td>ChangeType.DELETE</td></tr>
	 * </table>
	 * 
	 * @param changeDescription like 9a13,15
	 * @return {@link ChangeType} indicating if the change was ADD, MODIFY or DELETE
	 */
	private static ChangeType getChangeType(String changeDescription) {

		if (changeDescription.indexOf('a') != -1) {
			return ChangeType.ADD;
		} else if (changeDescription.indexOf('c') != -1) {
			return ChangeType.MODIFY;
		} else {
			return ChangeType.DELETE;
		}
	}

	/**
	 * Creates a new instance of {@link ClassChange} based on current index line.
	 * The line looks something like<p>
	 * <code>Index: org/apache/maven/plugin/deltacoverage/DeltaCoverageMojo.java</code>
	 * Decodes file name, class name from the line and returns a new instance of <code>ClassChange</code>
	 * 
	 * @param line index line
	 * @return {@link ClassChange} instance
	 */
	private static ClassChange getClassChange(String line) {

		ClassChange classChange = new ClassChange();
		String filename = getFileNameFromIndex(line);
		classChange.setFilename(filename);
		classChange.setName(getNameFromFileName(filename));
		List<LineChange> lines = new ArrayList<LineChange>();
		classChange.setLines(lines);
		return classChange;
	}

	/**
	 * Converts file name to class name.
	 * e.g.
	 * converts
	 * <code>org/apache/maven/plugin/deltacoverage/DeltaCoverageMojo.java</code>
	 * to
	 * <code>org.apache.maven.plugin.deltacoverage.DeltaCoverageMojo</code>
	 * 
	 * @param filename 
	 * @return fully qualified class name
	 */
	private static String getNameFromFileName(String filename) {

		return filename.substring(0, filename.indexOf(".")).replace('/', '.');
	}

	/**
	 * Extracts filename from index line.
	 * e.g. 
	 * Extracts <code>org/apache/maven/plugin/deltacoverage/DeltaCoverageMojo.java</code>
	 * from
	 * <code>Index: org/apache/maven/plugin/deltacoverage/DeltaCoverageMojo.java</code>
	 * @param indexLine
	 * @return
	 */
	private static String getFileNameFromIndex(String indexLine) {
		return indexLine.substring(indexLine.indexOf(':') + 2);
	}

	/**
	 * Indicates if the specified line is an index line
	 * e.g. 
	 * <code>Index: org/apache/maven/plugin/deltacoverage/DeltaCoverageMojo.java</code>
	 * 
	 * @param line
	 * @return boolean true if line is index line.
	 */
	private static boolean isIndexLine(String line) {
		return line.startsWith("Index");
	}

	/**
	 * Indicates if the specified line is a diff line. A diff line is the one that contains
	 * change description like "12a21,42".
	 * Method of elimination is used. i.e. checks if it is not all other types of lines.
	 * 
	 * @param line
	 * @return boolean true if line is a diff line.
	 */
	private static boolean isDiffLine(String line) {
		//return line.matches("[0-9,]+[acd][0-9,]+");
		return !(line.startsWith("<") || line.startsWith(">") || line.startsWith("---") || line.startsWith("cvs diff") || line.startsWith("?") || line.startsWith("\\"));
	}

	/**
	 * Indicates if the specified line is a change line. A change line is one that contains
	 * the actual code change.
	 * e.g.
	 * <pre>
	 * <code>> // this is a changed line</code>
	 * <code>>      x = y;</code>
	 * </pre>
	 * @param line
	 * @return boolean true if line is a change line.
	 */
	private static boolean isChangeLine(String line) {
		return line.startsWith(">");
	}

}
