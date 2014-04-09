package com.example.fragmentsample;


import java.util.List;

import com.example.fragmentsample.customadapter.MsgViewAdapter;
import com.example.fragmentsample.model.SmsDue;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;


public class MsgStepListFragment extends ListFragment{
	
	private List<SmsDue> smsDues;
	
	
	
	 public void setSmsDues(List<SmsDue> smsDues) {
		this.smsDues = smsDues;
	}

	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    MsgViewAdapter adapter = new MsgViewAdapter(getActivity(), smsDues);
	    setListAdapter(adapter);
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
	  }
}
