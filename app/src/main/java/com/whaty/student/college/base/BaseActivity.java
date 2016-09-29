package com.whaty.student.college.base;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * 通用Activity
 */
public class BaseActivity extends FragmentActivity implements OnClickListener {

	protected Context mContext = null;
	public Toast toast = null;
	public ImageView titleLeftTv;
	public TextView back;
	public TextView titleNameTv;
	public TextView titleRightTv;
	
	public String getTag() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	protected void initButterKnife(Activity context) {
		ButterKnife.inject(context);
	}
	public void showToast(String text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	protected void showToast(String msg, int length) {
		if (toast == null) {
			toast = Toast.makeText(this, msg, length);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		//if (id == R.id.back) {
		//	finish();
		//}
	}

	/**
	 * 设置标题
	 */
	public void setTitle(String titleStr) {
//		titleNameTv = (TextView) findViewById(R.id.titleTv);
//		if (titleNameTv != null) {
//			titleNameTv.setText(titleStr);
//		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (null != this.getCurrentFocus()) {
			/**
			 * 点击空白位置 隐藏软键盘
			 */
			InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			return mInputMethodManager.hideSoftInputFromWindow(this
					.getCurrentFocus().getWindowToken(), 0);
		}
		return true;
	}

	public boolean isNetworkConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}

}
