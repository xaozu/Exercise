package xz.exercise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import butterknife.BindView;
import butterknife.OnClick;
import xz.exercise.base.BaseActivity;
import xz.exercise.utils.ToastUtils;

/**
 * Author：zhulunjun
 * Time：2017/2/6
 * description：使用云服务器实现注册登录功能
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login_lin;
    }

    @Override
    protected BaseActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void myClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    //检查
    private boolean check() {
        if (etName.getText().toString().isEmpty()) {
            ToastUtils.showDefault("没有输入用户名！");
            return true;
        }
        if (etPassword.getText().toString().isEmpty()) {
            ToastUtils.showDefault("没有输入密码！");
            return true;
        }
        return false;
    }

    //登录
    private void login() {
        if (check()) return;
        btnLogin.setEnabled(false);
        btnLogin.setText("登录中...");
        btnRegister.setEnabled(false);

        AVUser.logInInBackground(etName.getText().toString(), etPassword.getText().toString(),
                new LogInCallback<AVUser>() {

            @Override
            public void done(AVUser avUser, AVException e) {
                btnLogin.setEnabled(true);
                btnLogin.setText("登录");
                btnRegister.setEnabled(true);
                if(e==null){
                    finish();
                }else{
                    ToastUtils.showDefault(e.getMessage());
                }
            }
        });

    }

    //注册
    private void register() {
        if (check()) return;
        btnRegister.setEnabled(false);
        btnRegister.setText("注册中...");
        btnLogin.setEnabled(true);

        AVUser user=new AVUser();
        user.setUsername(etName.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    btnRegister.setEnabled(true);
                    btnRegister.setText("注册");
                    btnLogin.setEnabled(true);
                    finish();
                }else{
                    ToastUtils.showDefault(e.getMessage());
                }
            }
        });
    }
}
