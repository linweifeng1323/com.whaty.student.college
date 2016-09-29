package com.whaty.student.college.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whaty.student.college.R;
import com.whaty.student.college.activity.MainActivity;

public class MainFragment extends BaseFragment {
	private View rootView;
	private MainActivity mainActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.layout_main_fragment, null);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mainActivity = (MainActivity) getActivity();
	}
}
