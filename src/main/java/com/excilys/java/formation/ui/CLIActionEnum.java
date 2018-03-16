package com.excilys.java.formation.ui;

import java.util.HashMap;
import java.util.Map;


public enum CLIActionEnum {
	
	LIST_COMPUTERS(1),
	LIST_COMPANIES(2),
	COMPUTER_DETAILS(3),
	CREATE_COMPUTER(4),
	UPDATE_COMPUTER(5),
	DELETE_COMPUTER(6),
	COMPANY_BY_NAME(7),
	EXIT(8);
		
	private int actionNumber;
	public static Map< Integer, CLIActionEnum> actionMap = new HashMap<>();
	private CLIActionEnum(int i) {
		this.actionNumber = i;
	}
	
	static {
		for (CLIActionEnum action : CLIActionEnum.values())
			actionMap.put(action.actionNumber, action);
	}
	
	public static CLIActionEnum getAction(int i) {
		return actionMap.get(i);
	}
	
	
		
}
