package com.iksgmbh.utils;

import java.util.List;

public class StringUtil {

	public static String removeBlankAndCommentLines(final String text, final char COMMENT_INDICATOR) {
		String[] lines = text.split(FileUtil.getSystemLineSeparator());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i]; 
			if (line.length() > 0 && line.charAt(0) != COMMENT_INDICATOR) {
				sb.append(line + ", ");
			}
		}
		String toReturn = sb.toString();
		if (toReturn.length() > 2) {
			toReturn = toReturn.substring(0, toReturn.length()-2);
		}
		return toReturn;
	}

	public static String[] getListFromLineWithCommaSeparatedElements(final String line) {
		if (line.trim().length() == 0) {
			return new String[0];
		}
		
		final String trimmedLine = line.replaceAll(" ", "");
		return trimmedLine.split(",");
	}
	
	public static boolean startsWithUpperCase(final String s) {
		return Character.isUpperCase(s.charAt(0));
	}
	
	public static boolean startsWithLowerCase(final String s) {
		return Character.isLowerCase(s.charAt(0));
	}
	
	public static String concat(final List<String> list) {
		final StringBuffer sb = new StringBuffer();
		int counter = 0;
		for (String line : list) {
			counter++;
			sb.append(line);
			if (counter < list.size()) {
				sb.append(FileUtil.getSystemLineSeparator());
			}
		}
		return sb.toString();
	}

}