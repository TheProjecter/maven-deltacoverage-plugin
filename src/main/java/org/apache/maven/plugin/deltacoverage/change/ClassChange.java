package org.apache.maven.plugin.deltacoverage.change;

import java.util.List;

public class ClassChange {

	private String name;
	private String filename;
	private List<LineChange> lines;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List<LineChange> getLines() {
		return lines;
	}
	public void setLines(List<LineChange> lines) {
		this.lines = lines;
	}
	@Override
	public String toString() {
		return "ClassChange [filename=" + filename + ", name=" + name + "]";
	}
	
	
}
