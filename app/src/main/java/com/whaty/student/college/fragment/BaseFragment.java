package com.whaty.student.college.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.whaty.student.college.base.BaseActivity;

import java.text.SimpleDateFormat;

public class BaseFragment extends Fragment implements OnClickListener {

	public BaseActivity mActivity;
	public SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (BaseActivity) getActivity();
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 查找并设置监听器
	 * 
	 * @param resourceIds
	 *            控件们
	 */
	public void setOnClickListener(int... resourceIds) {
		for (int i : resourceIds) {
			getActivity().findViewById(i).setOnClickListener(this);
		}
	}

	protected void startActivity(Class<?> clazz) {
		if (clazz == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(getActivity(), clazz);
		getActivity().startActivity(intent);
	}

	// Fragment的onResume是Activity的生命周期回调方法
	public void onFragmentResume() {

	}

	public boolean isNetworkConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}

}
