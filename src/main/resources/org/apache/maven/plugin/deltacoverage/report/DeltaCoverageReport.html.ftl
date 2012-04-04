<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.0 Transitional//EN'>
<HTML>
<HEAD>
<TITLE>Delta Code Coverage</TITLE>
<style type='text/css'>

body {
	font-family: verdana, arial, helvetica;
}
table.report{
	border-collapse: collapse;
	width: 100%;
	border: 1px solid;
}
div.percentgraph
{
	background-color: #f02020;
	border: #808080 1px solid;
	height: 1em;
	magin: 0px;
	padding: 0px;
	width: 100px;
	text-align: left;
}

div.percentgraph div.greenbar
{
	background-color: #00f000;
	height: 1em;
	magin: 0px;
	padding: 0px;
}

div.percentgraph div.na
{
	background-color: #eaeaea;
	height: 1em;
	magin: 0px;
	padding: 0px;
}

div.percentgraph span.text
{
	display: block;
	position: absolute;
	text-align: center;
	width: 100px;
	font-size: 12px;	
}

tr.module
{
	font-size: 30px;
	background-color: #990000;
	color: #ffffff;
}

tr.module span.percent
{
	font-size: 30px;
	color: #ffffff;
}

span.percent
{
	font-size: 12px;
}

tr.package
{
	font-size: 14px;
	font-weight: bold;
}

tr.class
{
	font-size: 14px;
	font-weight: bold;
	background-color: #7777ff;
}

tr.method
{
	font-size: 12px;
	background-color: #aaaaff;
}

tr.coveredLine
{
	font-size: 10px;
	background-color: #bbffbb;
}

td.content1
{
	font-size: 10px;
}

td.content2
{
	font-size: 12px;
}

tr.uncoveredLine
{
	font-size: 10px;
	background-color: #ffbbbb;
}

</style>

</HEAD>

<BODY >
<TABLE  class='report'>
<TR>
	<TH width='60%'>&nbsp;</TH>
	<TH width='20%'>Line Coverage</TH>
	<TH width='20%'>Branch Coverage</TH>
</TR>
<@writeLine class="module" content="Overall" coverage=coverage/>
<#list coverage.packages as package>
	<@writeLine class="package" content="package: ${package.name}" coverage=package/>
	<#list package.classes as class>
		<@writeLine class="class" content="class: <a href='${relativeCoveragePath}/${class.name}.html'>${class.name}</a>" coverage=class/>
		<#list class.methods as method>
			<@writeLine class="method" content="method: ${method.name?html}" coverage=method/>
				<#list method.lines as line>
				    <@writeLineDetail content1="line: ${line.number}" content2=line.value coverage=line/>
				</#list>
		</#list>
	</#list>
</#list>
</TABLE>
</BODY>
</HTML>

<#macro writeLine class content coverage>

<TR class='${class}'>
   <TD>${content}</TD>
   <#if (coverage.lineRate >= 0)>
   <TD align=center><table><tr><td><span class='percent'>${(coverage.lineRate * 100)?string("0")}%&nbsp;</span></td><td><div class='percentgraph'><div class='greenbar' style='width:${(coverage.lineRate * 100)?string("0")}px'><span class='text'>${coverage.hitLines}/${coverage.totalLines}</span></div></div></td></tr></table></TD>
    <#else>
    <TD align=center><table><tr><td><span>NA&nbsp;</span></td><td><div class='percentgraph'><div class='na'></div></div></td></tr></table></TD>
   </#if>
   <#if (coverage.branchRate >= 0)>
   <TD align=center><table><tr><td><span class='percent'>${(coverage.branchRate * 100)?string("0")}%&nbsp;</span></td><td><div class='percentgraph'><div class='greenbar' style='width:${(coverage.branchRate*100)?string("0")}px'><span class='text'>${coverage.hitBranches}/${coverage.totalBranches}</span></div></div></td></tr></table></TD>
    <#else>
    <TD align=center><table><tr><td><span>NA&nbsp;</span></td><td><div class='percentgraph'><div class='na'></div></div></td></tr></table></TD>
   </#if>
</TR>
</#macro>

<#macro writeLineDetail content1 content2 coverage>
<#if (coverage.hits > 0)>
<TR class='coveredLine'>
<#else>
<TR class='uncoveredLine'>
</#if>
   <TD colspan='3'><table width='100%'><tr><td class='content1' width='5%'>${content1?html}</td><td class='content2'><PRE>${content2?html}</PRE></td></tr></table></TD>
</TR>

</#macro>