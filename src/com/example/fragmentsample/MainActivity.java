package com.example.fragmentsample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class MainActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}

			StartPageFragment startPageFragment = new StartPageFragment();
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, startPageFragment).addToBackStack("startPage").commit();
		}
	}

	private void onMenuSelect(List<String> dueMsgs) {
		MsgStepListFragment msgStepListFragment = new MsgStepListFragment();
		msgStepListFragment.setLines(dueMsgs);
		msgStepListFragment.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, msgStepListFragment).addToBackStack("list").commit();
	}

	public void displayDue(View view) {
		List<String> dueMsgs = new ArrayList<String>();
		Uri uriSMSURI = Uri.parse("content://sms/inbox");
		String[] mProjection = { "address", "date", "body" };
		Cursor cur = getContentResolver().query(uriSMSURI, mProjection, "body like '%due%' or body like '%bill%'", null, null);
		int i = 1;
		while (cur.moveToNext() && i < 5) {
			String msgBody = cur.getString(2);
			Matcher matcher = Constants.smsDueMsgPattern.matcher(msgBody);
			if (matcher.find()) {
				String currentDatePattern = "";
				String billAmt = matcher.group(1);
				String dueDate = matcher.group(2).trim();
				matcher = Constants.ddMMyyyy_withHyphenRegex.matcher(dueDate);
				if(matcher.find()){
					currentDatePattern = Constants.ddMMyyyy_withHyphen;
				}
				matcher = Constants.ddMMyyyy_withDotRegex.matcher(dueDate);
				if(matcher.find()){
					currentDatePattern = Constants.ddMMyyyy_withDot;
				}
				matcher = Constants.ddMMMyyyy_withHyphenRegex.matcher(dueDate);
				if(matcher.find()){
					currentDatePattern = Constants.ddMMMyyyy_withHyphen;
				}
				matcher = Constants.ddMMMyy_withHyphenRegex.matcher(dueDate);
				if(matcher.find()){
					currentDatePattern = Constants.ddMMMyy_withHyphen;
				}
				DateFormat dftoDate = new SimpleDateFormat(currentDatePattern);
				DateFormat dftoStdFormat = new SimpleDateFormat(Constants.STD_DATE_PATTERN);
				Date due = null;
				try {
					due = dftoDate.parse(dueDate);
					if(due.after(new Date())) {
						dueMsgs.add("VENDOR : " + cur.getString(0) + " \n" + "Bill Amount : " + billAmt + " \n" + "Due Date : " + dftoStdFormat.format(due));
					}
				} catch (ParseException e) {
					dueMsgs.add("VENDOR : " + cur.getString(0) + " \n" + "Bill Amount : " + billAmt + " \n" + "Due Date : " + dueDate);
				}
				i++;
			}
		}
		onMenuSelect(dueMsgs);
	}
}
