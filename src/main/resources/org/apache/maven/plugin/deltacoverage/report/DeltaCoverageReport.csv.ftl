Class, Method, Covered Lines, Total Lines, Line Coverage, Covered Branches, Total Branches, Branch Coverage
<#list coverage.packages as package>
	<#list package.classes as class>
		<#list class.methods as method>
${class.name}, ${method.name} ${method.signature}, ${method.hitLines}, ${method.totalLines}, ${method.lineRate}, ${method.hitBranches}, ${method.totalBranches}, ${method.branchRate} 			
		</#list>
${class.name}, , ${class.hitLines}, ${class.totalLines}, ${class.lineRate}, ${class.hitBranches}, ${class.totalBranches}, ${class.branchRate}		
	</#list>
${package.name}, , ${package.hitLines}, ${package.totalLines}, ${package.lineRate}, ${package.hitBranches}, ${package.totalBranches}, ${package.branchRate}	
</#list>
Total, , ${coverage.hitLines}, ${coverage.totalLines}, ${coverage.lineRate}, ${coverage.hitBranches}, ${coverage.totalBranches}, ${coverage.branchRate}