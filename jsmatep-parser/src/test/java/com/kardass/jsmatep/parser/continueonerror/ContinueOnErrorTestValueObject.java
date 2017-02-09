package com.kardass.jsmatep.parser.continueonerror;

public class ContinueOnErrorTestValueObject {
	private String a;

	public void setA(String a) {
		this.a = a;
	}

	public String getA() {
		return this.a;
	}

	private java.util.Date b;

	public void setB(java.util.Date b) {
		this.b = b;
	}

	public java.util.Date getB() {
		return this.b;
	}

	private Integer c;

	public void setC(Integer c) {
		this.c = c;
	}

	public Integer getC() {
		return this.c;
	}

	public String toString() {
		return "[" + "someOtherName=" + getA() + ", " + "b=" + getB() + ", "
				+ "c=" + getC() + "]";
	}
}
