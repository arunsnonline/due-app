package com.example.fragmentsample.customadapter;

import java.util.List;

import com.example.fragmentsample.R;
import com.example.fragmentsample.model.SmsDue;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MsgViewAdapter extends ArrayAdapter<SmsDue> {
	private final Context context;
	private final List<SmsDue> values;

	public MsgViewAdapter(Context context, List<SmsDue> values) {
		super(context, R.layout.sync_msg, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public int getCount() {
		return values.size()>0?values.size():1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		
		Log.i("MyActivity", "Blah------>inflate"+values.size());
		if (values.size() == 0) {
			rowView = inflater.inflate(R.layout.empty_msg, parent, false);
		} else {

			rowView = inflater.inflate(R.layout.sync_msg, parent, false);
			TextView vendor = (TextView) rowView.findViewById(R.id.vendor);
			TextView amount = (TextView) rowView.findViewById(R.id.amount);
			TextView dueDate = (TextView) rowView.findViewById(R.id.dueDate);

			String vendorName = values.get(position).getVendorName();
			String amountString = values.get(position).getAmount();
			String dueDateString = values.get(position).getDueDate();

			vendor.setText(vendorName);
			amount.setText(amountString);
			dueDate.setText(dueDateString);
		}

		return rowView;
	}
}
