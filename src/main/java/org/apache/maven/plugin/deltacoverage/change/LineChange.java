package org.apache.maven.plugin.deltacoverage.change;

public class LineChange {
	
	private ChangeType type;
	private int lineNumber;
	private String value;
	
	public ChangeType getType() {
		return type;
	}
	public void setType(ChangeType type) {
		this.type = type;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "LineChange [lineNumber=" + lineNumber + ", type=" + type + "]";
	}
}
