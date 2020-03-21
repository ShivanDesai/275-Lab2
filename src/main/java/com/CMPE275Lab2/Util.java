package com.CMPE275Lab2;

public class Util {

	public boolean requiredAndIsEmpty(String s) {
		return s.trim().isEmpty();
	}

	public boolean notRequiredAndIsEmpty(String s) {
		if (s == null) {
			return true;
		}
		return s.trim().isEmpty();
	}
	
	public String trimInput(String s) {
		if (s == null) {
			return s;
		}
		return s.trim();
	}
}
