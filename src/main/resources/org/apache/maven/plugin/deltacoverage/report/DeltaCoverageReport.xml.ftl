<?xml version="1.0"?>
<!DOCTYPE coverage SYSTEM "http://cobertura.sourceforge.net/xml/coverage-04.dtd">

<coverage line-rate="${coverage.lineRate}" branch-rate="${coverage.branchRate}"  lines-covered="${coverage.hitLines}" lines-valid="${coverage.totalLines}" branches-covered="${coverage.hitBranches}" branches-valid="${coverage.totalBranches}" complexity="${coverage.complexity}" version="${coverage.version}" timestamp="${coverage.timestamp}">
	<sources>
		<#list coverage.sources as source>
		<source>${source}</source>
		</#list>
	</sources>
    <packages>
        <#list coverage.packages as package>
        <package name="${package.name}" line-rate="${package.lineRate}" branch-rate="${package.branchRate}" complexity="${package.complexity}">
            <classes>
            <#list package.classes as class>
                <class name="${class.name?xml}" filename="${class.filename}" line-rate="${class.lineRate}" branch-rate="${class.branchRate}" complexity="${class.complexity}">
                    <methods>
                    <#list class.methods as method>
                        <method name="${method.name?xml}" signature="${method.signature?xml}" line-rate="${method.lineRate}" branch-rate="${method.branchRate}">
                            <lines>
							<#list method.lines as line>
							    <#if line.branch>
							    <line number="${line.number}" hits="${line.hits}" branch="${line.branch?string}" condition-coverage="${line.conditionCoverage}">
							        <conditions>
							        <#list line.conditions as condition>
							            <condition number="${condition.number}" type="${condition.type}" coverage="${condition.coverage}"/>
							        </#list>
							        </conditions>
							    </line>
							    <#else>
							    <line number="${line.number}" hits="${line.hits}" branch="${line.branch?string}"/>
							    </#if>
							</#list>
							</lines>
						</method>                        
                   </#list>
                   </methods>          
                   <lines>
					<#list class.lines as line>
					    <#if line.branch>
					    <line number="${line.number}" hits="${line.hits}" branch="${line.branch?string}" condition-coverage="${line.conditionCoverage}">
					        <conditions>
					        <#list line.conditions as condition>
					            <condition number="${condition.number}" type="${condition.type}" coverage="${condition.coverage}"/>
					        </#list>
					        </conditions>
					    </line>
					    <#else>
					    <line number="${line.number}" hits="${line.hits}" branch="${line.branch?string}"/>
					    </#if>
					</#list>
					</lines>         
                </class>   
            </#list>
            </classes>
        </package>    
        </#list>
    </packages>
</coverage>