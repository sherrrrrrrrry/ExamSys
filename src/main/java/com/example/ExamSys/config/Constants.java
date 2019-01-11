package com.example.ExamSys.config;

public final class Constants {

	public static final String LOGIN_REGEX = "^[_'.@a-zA-Z0-9-]*$";
	public static final String NAME_REGEX = "^[a-zA-Z\u4e00-\u9fa5] {2,}$";
	public static final String PHONE_REGEX = "^[1][3,4,5,7,8,9][0-9]{9}$";
	public static final String IDENTITY_REGEX = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
	
}
