package com.kardass.jsmatep.parser.setterlogic;

import java.sql.Date;

public class SetterLogicTestValueObject {
	
	private String a;
	private Date b;
	private Integer c;
	
	public SetterLogicTestValueObject() {
		super();
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public Date getB() {
		return b;
	}

	public void setB(java.sql.Date b) {
		this.b = b;
	}

//	public void setB(java.util.Date b) {
//		this.b = new java.sql.Date(b.getTime());
//	}

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}
	
}
