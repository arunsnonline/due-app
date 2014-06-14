package com.example.fragmentsample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import com.example.fragmentsample.model.SmsDue;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MainActivity extends FragmentActivity {

	private DateFormat dftoStdFormat = new SimpleDateFormat(
			Constants.STD_DATE_PATTERN);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}

			MsgStepListFragment msgStepListFragment = new MsgStepListFragment();
			msgStepListFragment.setSmsDues(getDueBills());
			msgStepListFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, msgStepListFragment)
					.commit();
		}
	}

	private List<SmsDue> getDueBills() {
		List<SmsDue> dueMsgs = new ArrayList<SmsDue>();
		Uri uriSMSURI = Uri.parse("content://sms/inbox");
		String[] mProjection = { "address", "date", "body" };
		Cursor cur = getContentResolver().query(uriSMSURI, mProjection,
				"body like '%due%' or body like '%bill%'", null, null);
		int i = 1;
		while (cur.moveToNext() && i < 20) {
			String msgBody = cur.getString(2);
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
					continue;
				
				DateFormat dftoDate = new SimpleDateFormat(currentDatePattern);
				Date due = null;
				try {
					due = dftoDate.parse(dueDate);
					Date currentDate = new Date();
					String current = dftoDate.format(currentDate);

					if (dueDate.toLowerCase().equals(current.toLowerCase())
							|| due.after(currentDate)) {
						SmsDue smsDue = new SmsDue(cur.getString(0),
								totalDueAmt, dftoStdFormat.format(due));
						dueMsgs.add(smsDue);
					}
				} catch (ParseException e) {
					SmsDue smsDue = new SmsDue(cur.getString(0), totalDueAmt,
							dueDate);
					dueMsgs.add(smsDue);
				}
				i++;
			}
		}
		if (dueMsgs.size() > 0) {
			Collections.sort(dueMsgs, new DueDateComparator());
		}
		Log.i("MyActivity", "Blah------>" + dueMsgs.size());
		return dueMsgs;
	}

	private class DueDateComparator implements Comparator<SmsDue> {

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
