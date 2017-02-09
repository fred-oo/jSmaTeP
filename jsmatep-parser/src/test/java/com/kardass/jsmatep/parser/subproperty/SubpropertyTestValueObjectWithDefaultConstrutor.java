package com.kardass.jsmatep.parser.subproperty;

public class SubpropertyTestValueObjectWithDefaultConstrutor {
	
	private PrimaryKey pk;
	
	public PrimaryKey getPk() {
		return pk;
	}

	public void setPk(PrimaryKey pk) {
		this.pk = pk;
	}

	private String a;

	public void setA(String a) {
		this.a = a;
	}

	public String getA() {
		return this.a;
	}

	public String toString() {
		return "[" + "pk=" + getPk() + ", " + "a=" + getA() + "]";
	}
}
