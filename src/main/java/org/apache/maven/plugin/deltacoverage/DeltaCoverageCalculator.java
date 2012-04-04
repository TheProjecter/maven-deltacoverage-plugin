package org.apache.maven.plugin.deltacoverage;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.deltacoverage.change.ClassChange;
import org.apache.maven.plugin.deltacoverage.change.LineChange;
import org.apache.maven.plugin.deltacoverage.coverage.AggregateCoverage;
import org.apache.maven.plugin.deltacoverage.coverage.ClassCoverage;
import org.apache.maven.plugin.deltacoverage.coverage.CoverageReportReader;
import org.apache.maven.plugin.deltacoverage.coverage.LineCoverage;
import org.apache.maven.plugin.deltacoverage.coverage.MethodCoverage;
import org.apache.maven.plugin.deltacoverage.coverage.ModuleCoverage;
import org.apache.maven.plugin.deltacoverage.coverage.PackageCoverage;
import org.apache.maven.plugin.deltacoverage.scm.CVSExecutor;
import org.apache.maven.plugin.deltacoverage.scm.SCMExecutor;



/**
 * Evaluates the delta coverage based on actual coverage and changes in source code.
 * 
 * @author Abhijeet_Kesarkar
 *
 */
public class DeltaCoverageCalculator {

	/**
	 * Calculates delta coverage. Creates an instance of {@link ModuleCoverage} and populates it
	 * with information from actual coverage, but only for lines changed. Re-calculates the 
	 * coverage details, i.e. line rate and branch rate, based on this delta coverage information.
	 * 
	 * @param actualCoverage as reported by existing coverage report using {@link CoverageReportReader}
	 * @param changes as extracted from SCM by {@link SCMExecutor} like {@link CVSExecutor}
	 * @return {@link ModuleCoverage} instance indicating delta coverage
	 */
	public static ModuleCoverage calculateDeltaCoverage(ModuleCoverage actualCoverage, List<ClassChange> changes) {

		ModuleCoverage deltaCoverage = createDeltaCoverage(actualCoverage, changes);
		evaluateCoverageRate(deltaCoverage);
		return deltaCoverage;
	}

	/**
	 * Calculates the line rate and branch rate at module, package, class and method level based
	 * on the line hits and branch hits for the delta code.
	 * 
	 * @param deltaCoverage {@link ModuleCoverage} instance indicating the delta coverage
	 */
	private static void evaluateCoverageRate(ModuleCoverage deltaCoverage) {

		List<LineCoverage> moduleLines = new ArrayList<LineCoverage>();
		for (PackageCoverage packageCoverage : deltaCoverage.getPackages()) {
			List<LineCoverage> packageLines = new ArrayList<LineCoverage>();

			for (ClassCoverage classCoverage : packageCoverage.getClasses()) {
				for (MethodCoverage methodCoverage : classCoverage.getMethods()) {
					setLineRate(methodCoverage.getLines(), methodCoverage);
					setBranchRate(methodCoverage.getLines(), methodCoverage);

				}
				setLineRate(classCoverage.getLines(), classCoverage);
				setBranchRate(classCoverage.getLines(), classCoverage);
				packageLines.addAll(classCoverage.getLines());
			}
			setLineRate(packageLines, packageCoverage);
			setBranchRate(packageLines, packageCoverage);
			moduleLines.addAll(packageLines);
		}
		setLineRate(moduleLines, deltaCoverage);
		setBranchRate(moduleLines, deltaCoverage);
	}

	/**
	 * Calculates lineRate for specified lines, and sets the same in the coverage instance.
	 * The coverage instance could be a method or a class or a package or a module coverage instance.
	 * 
	 * @param lines list of {@link LineCoverage} 
	 * @param coverage {@link MethodCoverage} or {@link ClassCoverage} or {@link PackageCoverage} or {@link ModuleCoverage} instance
	 */
	private static void setLineRate(List<LineCoverage> lines, AggregateCoverage coverage) {
		double lineRate = -1.0;
		int lineHit = 0;
		for (LineCoverage lineCoverage : lines) {
			if (lineCoverage.getHits() > 0) {
				lineHit++;
			}
		}
		if (lines.size() > 0) {
			lineRate = (double) lineHit / lines.size();
		}
		coverage.setLineRate(lineRate);
		coverage.setHitLines(lineHit);
		coverage.setTotalLines(lines.size());
	}

	/**
	 * Calculates branchRate for specified lines, and sets the same in the coverage instance.
	 * The coverage instance could be a method or a class or a package or a module coverage instance.
	 * 
	 * @param lines list of {@link LineCoverage} 
	 * @param coverage {@link MethodCoverage} or {@link ClassCoverage} or {@link PackageCoverage} or {@link ModuleCoverage} instance
	 */
	private static void setBranchRate(List<LineCoverage> lines, AggregateCoverage coverage) {
		double branchRate = -1.0;
		List<String> conditionCoverages = new ArrayList<String>();
		for (LineCoverage lineCoverage : lines) {
			if (lineCoverage.isBranch()) {
				conditionCoverages.add(lineCoverage.getConditionCoverage());
			}
		}
		if (conditionCoverages.size() > 0) {

			int numerator = 0;
			int denominator = 0;
			for (String conditionCoverage : conditionCoverages) {
				numerator += Integer.valueOf(conditionCoverage.split("[\\(\\)]")[1].split("/")[0]);
				denominator += Integer.valueOf(conditionCoverage.split("[\\(\\)]")[1].split("/")[1]);
			}
			branchRate = (double) numerator / denominator;
			coverage.setHitBranches(numerator);
			coverage.setTotalBranches(denominator);
		}
		coverage.setBranchRate(branchRate);
	}

	/**
	 * Creates {@link ModuleCoverage} instance indicating the delta coverage tree.
	 * For each line change in the give list of {@link ClassChange}, builds the coverage tree 
	 * and populates the leaf nodes (lines) from the values from actual coverage.
	 * 
	 * @param actualCoverage {@link ModuleCoverage} instance indicating actual coverage
	 * @param changes list of {@link ClassChange}
	 * @return
	 */
	public static ModuleCoverage createDeltaCoverage(ModuleCoverage actualCoverage, List<ClassChange> changes) {
		ModuleCoverage deltaCoverage = new ModuleCoverage();
		deltaCoverage.setVersion(actualCoverage.getVersion());
		deltaCoverage.setSources(actualCoverage.getSources());
		for (ClassChange change : changes) {
			String packageName = getPackageName(change);

			ClassCoverage actualClassCoverage = null;
			PackageCoverage actualPackageCoverage = actualCoverage.getPackage(packageName);
			if (actualPackageCoverage != null) {
				actualClassCoverage = actualPackageCoverage.getClass(change.getName());
			}
			if (actualClassCoverage != null) {
				ClassCoverage deltaClassCoverage = new ClassCoverage();
				deltaClassCoverage.setFilename(change.getFilename());
				deltaClassCoverage.setName(change.getName());
				for (LineChange lineChange : change.getLines()) {
					int number = lineChange.getLineNumber();
					LineCoverage lineCoverage = actualClassCoverage.getLine(number);
					if (lineCoverage != null) {
						lineCoverage.setValue(lineChange.getValue());
						deltaClassCoverage.addLine(lineCoverage);

						MethodCoverage actualMethodCoverage = actualClassCoverage.getMethod(number);
						if (actualMethodCoverage != null) {
							MethodCoverage deltaMethodCoverage = deltaClassCoverage.getMethod(actualMethodCoverage.getName(), actualMethodCoverage.getSignature());
							if (deltaMethodCoverage == null) {
								deltaMethodCoverage = new MethodCoverage();
								deltaMethodCoverage.setName(actualMethodCoverage.getName());
								deltaMethodCoverage.setSignature(actualMethodCoverage.getSignature());
								deltaClassCoverage.addMethod(deltaMethodCoverage);
							}
							deltaMethodCoverage.addLine(lineCoverage);
						}
					}
				}

				if (deltaClassCoverage.getLines() != null && deltaClassCoverage.getLines().size() > 0) {

					PackageCoverage deltaPackageCoverage = deltaCoverage.getPackage(packageName);
					if (deltaPackageCoverage == null) {
						deltaPackageCoverage = new PackageCoverage();
						deltaPackageCoverage.setName(packageName);
						deltaCoverage.addPackage(deltaPackageCoverage);
					}
					deltaPackageCoverage.addClass(deltaClassCoverage);
				}
			}
		}
		return deltaCoverage;

	}

	/**
	 * Retrieves package name for the given class change.
	 * 
	 * @param change {@link ClassChange}
	 * @return package name
	 */
	private static String getPackageName(ClassChange change) {
		String packageName = change.getName();
		int index = change.getName().lastIndexOf('.');
		if( index != -1 )
		{
			packageName = change.getName().substring(0, index);
		}
		return packageName;
	}
}
