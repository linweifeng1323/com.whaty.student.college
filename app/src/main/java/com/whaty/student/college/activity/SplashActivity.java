package com.whaty.student.college.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.whaty.student.college.R;
import com.whaty.student.college.base.BaseActivity;

public class SplashActivity extends BaseActivity {

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new CountDownTimer(2000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, LoginActivity.class);
				startActivity(intent);

				int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
				if (VERSION >= 5) {
					SplashActivity.this.overridePendingTransition(
							R.anim.fade_in, R.anim.fade_out);
				}
				finish();
			}
		}.start();
	}
}