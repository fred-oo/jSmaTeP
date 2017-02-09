package com.kardass.jsmatep.parser.subproperty;

public class PrimaryKey {

	private String a;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrimaryKey [a=");
		builder.append(a);
		builder.append("]");
		return builder.toString();
	}

}
