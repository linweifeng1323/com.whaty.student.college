package com.whaty.student.college.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.whaty.student.college.BaseConfig;
import com.whaty.student.college.MyApplication;
import com.whaty.student.college.R;
import com.whaty.student.college.base.BaseActivity;
import com.whaty.student.college.beans.UserInfo;
import com.whaty.student.college.sp.CommonSP;
import com.whaty.student.college.sp.CookiesSP;
import com.whaty.student.college.utils.HttpAgent;
import com.whaty.student.college.utils.MyTextUtils;
import com.whaty.student.college.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.domain_name)
    EditText domainName;
    @InjectView(R.id.username)
    EditText username;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.confirmBt)
    Button confirmBt;
    @InjectView(R.id.remember_psw_checkbox)
    CheckBox rememberPswCheckbox;
    @InjectView(R.id.auto_login_checkbox)
    CheckBox autoLoginCheckbox;
    private String passwordStr;
    private String domianNameStr;
    private String usernameStr;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initButterKnife(this);
        if (!isNetworkConnected()) {
            if (CommonSP.getInstance().isAutoLogin() && CommonSP.getInstance().isRememberPSW()) {
                userInfo = CookiesSP.getInstance().get();
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        initView();
    }

    /**
     * alt+f8 debug时选中查看值
     f8相当于eclipse的f6跳到下一步
     shift+f8相当于eclipse的f8跳到下一个断点，也相当于eclipse的f7跳出函数
     f7相当于eclipse的f5就是进入到代码
     alt+shift+f7这个是强制进入代码
     ctrl+shift+f9 debug运行java类
     ctrl+shift+f10正常运行java类
     command+f2停止运行
     */
    private void initView() {
        confirmBt.setEnabled(false);
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        domainName.addTextChangedListener(textWatcher);
        if (StringUtil.isNotEmpty(getIntent().getStringExtra("phoneStr"))) {
            username.setText(getIntent().getStringExtra("phoneStr"));
        }
        confirmBt.setOnClickListener(this);
        if (CommonSP.getInstance().isAutoLogin()) {
            autoLoginCheckbox.setChecked(true);
        }
        if (CommonSP.getInstance().isRememberPSW()) {
            rememberPswCheckbox.setChecked(true);
            UserInfo userInfo = CookiesSP.getCookies();
            if (userInfo != null) {
                username.setText(userInfo.getUserName());
                password.setText(userInfo.getPsw());
                domainName.setText(userInfo.getDomianName());
                domainName.setSelection(userInfo.getDomianName().length());
                domainName.clearFocus();
                username.clearFocus();
                password.clearFocus();
                if (CommonSP.getInstance().isAutoLogin()) {
                    login();
                }
            }
        }
        autoLoginCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    CommonSP.getInstance().setAutoLogin(true);
                } else {
                    CommonSP.getInstance().setAutoLogin(false);
                }
            }
        });
        rememberPswCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    CommonSP.getInstance().setRememberPSW(true);
                } else {
                    CommonSP.getInstance().setRememberPSW(false);
                }
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            usernameStr = username.getText().toString().trim();
            passwordStr = password.getText().toString().trim();
            domianNameStr = domainName.getText().toString().trim();
            if (!StringUtil.isEmpty1(passwordStr)
                    && !StringUtil.isEmpty1(usernameStr)
                    && !StringUtil.isEmpty1(domianNameStr)) {
                confirmBt.setEnabled(true);
            } else {
                confirmBt.setEnabled(false);
            }
        }
    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.confirmBt) {
            passwordStr = password.getText().toString().trim();
            if (passwordStr.length() < 6 || passwordStr.length() > 50) {
                showToast("密码长度不正确");
            } else {
                login();
            }
        }
    }

    private void login() {
        if (isNetworkConnected()) {
            checkdomain();
        } else {
            showToast("网络不可用,请检查是否连接", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 检查域名是否存在
     */
    private void checkdomain() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("domain", domianNameStr + ".fzcollege.com")
                .build();
        String url = HttpAgent.getUrl(domianNameStr + BaseConfig.BASE_URL + "/sys/checkdomainIsExist");
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject myJsonObject = new JSONObject(response.body().string());
                    JSONObject object = myJsonObject
                            .getJSONObject("object");
                    if ("OK".equals(object.getString("tips"))) {
                        String url = domianNameStr + BaseConfig.BASE_URL + "/sys/login";
                        //String url =BaseConfig.BASE_URL+"/sys/login";
                        String str = HttpAgent.getUrl(url);
                        login(str);
                    } else {
                        showToast("请检查你的域名是否正确", Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void login(final String url) {
        String md5Password = MyTextUtils
                .secretMd5HexStr(passwordStr.getBytes());
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("domain", domianNameStr + ".fzcollege.com")
                .add("password", md5Password)
                .add("username", usernameStr)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject myJsonObject = new JSONObject(response.body().string());
                    JSONObject object = myJsonObject
                            .getJSONObject("object");
                    boolean isLogin = object.getBoolean("success");
                    if (isLogin) {
                        JSONObject info = object.getJSONObject("object");
                        userInfo = HttpAgent.getGson().fromJson(
                                info.toString(), UserInfo.class);
                        if (userInfo.getUserType() == 1) {
                            userInfo.setPsw(passwordStr);
                            userInfo.setDomianName(domianNameStr);
                            System.out.println(info.getString("loginToken"));
                            MyApplication.setUser(userInfo);
                            CookiesSP.getInstance().save(userInfo);
                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            intent.putExtra("currentTime",
                                    object.getString("data"));
                            intent.putExtra("url", url);
                            startActivity(intent);
                            finish();
                        } else {
                            showToast("您正在使用是学生版本，请联系管理员下载老师版本", Toast.LENGTH_SHORT);
                        }
                    } else {
                        String tips = object.getString("tips");
                        if (tips.contains("用户名或密码错误")) {
                            showToast("用户名或密码错误", Toast.LENGTH_SHORT);
                        } else {
                            showToast("当前学生暂未加入班级，请先加入班级!", Toast.LENGTH_SHORT);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
