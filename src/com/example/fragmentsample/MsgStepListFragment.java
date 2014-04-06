package com.example.fragmentsample;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MsgStepListFragment extends ListFragment{
	
	private List<String> lines;
	
	
	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(
				getActivity(), 
				android.R.layout.simple_list_item_activated_1, 
				lines));
	}
	
	@Override
    public void onStart() {
        super.onStart();
    }
	
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	 }
	 
	 @Override
	 public void onListItemClick(ListView l, View v, int position, long id) {
	      
	 }
}
