package com.due.calc.core;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;

import android.util.Log;

import com.due.calc.Constants;
import com.due.calc.model.SmsDue;

public class DueFinder {
	
	private DateFormat dftoStdFormat;

	public DueFinder() {
		this.dftoStdFormat = new SimpleDateFormat(Constants.STD_DATE_PATTERN);
	}
	
	public SmsDue from(String msgBody,String address) {
		SmsDue smsDue = null;
		Matcher dueAmtMatcher = Constants.DUE_AMT_PATTERN.matcher(msgBody);
		String totalDueAmt = "";
		double maxDueAmt = -0.0;
		while (dueAmtMatcher.find()) {
			String tempDueAmt = dueAmtMatcher.group();
			Log.i("MyActivity", "Blah------>tempDueAmt" + tempDueAmt);
			Matcher amtMatcher = Constants.AMT_PATTERN.matcher(tempDueAmt);
			if (amtMatcher.find()) {
				double dueAmt = Double.valueOf(amtMatcher.group());
				if (dueAmt > maxDueAmt) {
					maxDueAmt = dueAmt;
					totalDueAmt = tempDueAmt;
				}
			}
		}

		Matcher dueDateMatcher = Constants.DUE_DATE_PATTERN
				.matcher(msgBody);
		if (dueDateMatcher.find()) {
			String currentDatePattern = "";
			String dueDate = dueDateMatcher.group().trim();
			Matcher dateMatcher = Constants.ddMMyyyy_withHyphenRegex
					.matcher(dueDate);
			if (dateMatcher.find()) {
				currentDatePattern = Constants.ddMMyyyy_withHyphen;
			}
			dateMatcher = Constants.ddMMyyyy_withDotRegex.matcher(dueDate);
			if (dateMatcher.find()) {
				currentDatePattern = Constants.ddMMyyyy_withDot;
			}
			dateMatcher = Constants.ddMMMyyyy_withHyphenRegex
					.matcher(dueDate);
			if (dateMatcher.find()) {
				currentDatePattern = Constants.ddMMMyyyy_withHyphen;
			}
			dateMatcher = Constants.ddMMMyy_withHyphenRegex
					.matcher(dueDate);
			if (dateMatcher.find()) {
				currentDatePattern = Constants.ddMMMyy_withHyphen;
			}
			dateMatcher = Constants.ddMMMyyyy_withForwardSlashRegex
					.matcher(dueDate);
			if (dateMatcher.find()) {
				currentDatePattern = Constants.ddMMMyyyy_withForwardSlash;
			}
			
			if(totalDueAmt.isEmpty() || currentDatePattern.isEmpty())
				return null;
			
			DateFormat dftoDate = new SimpleDateFormat(currentDatePattern);
			Date due = null;
			try {
				due = dftoDate.parse(dueDate);
				Date currentDate = new Date();
				String current = dftoDate.format(currentDate);

				if (dueDate.toLowerCase().equals(current.toLowerCase())
						|| due.after(currentDate)) {
					smsDue = new SmsDue(address,
							totalDueAmt, dftoStdFormat.format(due));
				}
			} catch (ParseException e) {
				smsDue = new SmsDue(address, totalDueAmt,
						dueDate);
			}
		}
		return smsDue;
	}
	
	public class DueDateComparator implements Comparator<SmsDue> {

		@Override
		public int compare(SmsDue lhs, SmsDue rhs) {
			Date lhsDate = null;
			Date rhsDate = null;
			try {
				lhsDate = dftoStdFormat.parse(lhs.getDueDate());
				rhsDate = dftoStdFormat.parse(rhs.getDueDate());
			} catch (ParseException pe) {
				return 0;
			}
			return lhsDate.compareTo(rhsDate);
		}
	}
	
}
