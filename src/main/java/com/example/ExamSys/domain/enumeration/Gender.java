package com.example.ExamSys.domain.enumeration;

public enum Gender {
	BOY("男"), GIRL("女");
	
	private String alias;
	
	Gender(String alias){
		this.alias = alias;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
