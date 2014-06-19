package com.due.calc.util;

public class StringUtil {
	
	public static String join(String delimiter,String[] array) {
		StringBuilder stringBuilder = new StringBuilder();
		int aLen = array !=null ? array.length:-1;
		if(array != null && aLen > 1) {
			stringBuilder.append(array[0]).append(delimiter);
			for(int i=1;i < aLen-1;i++) {
				stringBuilder.append(array[i]).append(delimiter);
			}
			return stringBuilder.append(array[aLen-1]).toString();
		}else 
		if(array !=null && aLen==1) {
			return stringBuilder.append(array[0]).toString();
		}
		return stringBuilder.toString();
	}

}
