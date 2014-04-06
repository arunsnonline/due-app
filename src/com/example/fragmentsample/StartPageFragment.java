package com.example.fragmentsample;


import com.example.fragmentsample.listeners.StartPageImageClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StartPageFragment extends Fragment {
	
	StartPageImageClickListener startPageImageClickListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sync_msg, container, false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		 super.onAttach(activity);
	}
	
	 @Override
	    public void onStart() {
	        super.onStart();

	    }

	
}
