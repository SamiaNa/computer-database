package com.excilys.java.formation.ui;

public enum PageActionEnum {

	NEXT("n"),
	PREVIOUS("p"),
	EXIT("q");
	
	private String actionStr;
	
	private PageActionEnum(String action) {
		this.actionStr = action;
	}
	
}
