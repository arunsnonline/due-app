package com.example.fragmentsample;

import java.util.regex.Pattern;

public class Constants {
	
	public static final Pattern smsDueMsgPattern = Pattern.compile("^.+(Rs.+[0-9]\\.[0-9]{2}).+([0-9]{2}-[0-9]{2}-[0-9]{2014}|[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}|[0-9]{2}-[A-Za-z]{3}-[0-9]{4}|[0-9]{2}-[A-Za-z]{3}-[0-9]{2}).+$");
	public static final Pattern ddMMyyyy_withHyphenRegex = Pattern.compile("[0-9]{2}-[0-9]{2}-[0-9]{4}");
	public static final Pattern ddMMyyyy_withDotRegex = Pattern.compile("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}");
	public static final Pattern ddMMMyy_withHyphenRegex = Pattern.compile("[0-9]{2}-[A-Za-z]{3}-[0-9]{2}");
	public static final Pattern ddMMMyyyy_withHyphenRegex = Pattern.compile("[0-9]{2}-[A-Za-z]{3}-[0-9]{4}");
	
	public static final String ddMMyyyy_withHyphen = "dd-MM-yyyy";
	public static final String ddMMyyyy_withDot = "dd.MM.yyyy";
	public static final String ddMMMyy_withHyphen = "dd-MMM-yy";
	public static final String ddMMMyyyy_withHyphen = "dd-MMM-yyyy";
	
	public static final String STD_DATE_PATTERN = "dd-MMM-yyyy";

}
