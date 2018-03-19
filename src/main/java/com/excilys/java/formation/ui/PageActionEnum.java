package com.excilys.java.formation.ui;

import java.util.HashMap;
import java.util.Map;

public enum PageActionEnum {

	NEXT("n"),
	PREVIOUS("p"),
	GOTO("g"),
	DEFAULT(""),
	EXIT("q");
	
	private String str;
	
	private static  Map <String, PageActionEnum> map;
	private PageActionEnum(String str) {
		this.str = str;
	}
	
	static {
		map = new HashMap<>();
		for (PageActionEnum action : PageActionEnum.values()) {
			map.put(action.str, action);
		}		
	}
	
	public static PageActionEnum getAction(String actionStr) {
		if (map.containsKey(actionStr)){
			return map.get(actionStr.toLowerCase());
		}else {
			return DEFAULT;
		}
	}
	
	

	
}
