package com.due.calc;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.due.calc.core.DueFinder;
import com.due.calc.core.QueryManager;
import com.due.calc.model.SmsDue;
import com.due.calc.R;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MainActivity extends FragmentActivity {

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
		Set<SmsDue> dueMsgs = new HashSet<SmsDue>();
		Cursor cur = QueryManager.build(getContentResolver())
				.queryOn("content://sms/inbox")
				.getFields( "address", "date", "body" )
				.sortDesc("date")
				.query("body like '%due%' or body like '%bill%'");
		int i = 1;
		DueFinder dueFinder = new DueFinder();
		while (cur.moveToNext() && i < 20) {
			String address = cur.getString(0);
			String msgBody = cur.getString(2);
			SmsDue smsDue = dueFinder.from(msgBody, address);
			if(smsDue != null) {
				dueMsgs.add(smsDue);
				i++;
			}
		}
		List<SmsDue> sortedDueMsgs = new ArrayList<SmsDue>(dueMsgs);
		if(sortedDueMsgs.size() > 0) {
			Collections.sort(sortedDueMsgs, dueFinder.new DueDateComparator());
		}
		Log.i("MyActivity", "Blah------>" + dueMsgs.size());
		return sortedDueMsgs;
	}
	
}
