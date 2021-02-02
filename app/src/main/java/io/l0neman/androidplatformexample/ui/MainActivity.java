package io.l0neman.androidplatformexample.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import io.l0neman.androidplatformexample.databinding.ActivityMainBinding;
import io.l0neman.androidplatformexample.model.bean.UserLoginInfo;
import io.l0neman.androidplatformexample.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding mBinding;
  private ProgressDialog mProgressDialog;
  private String mUserToken;
  private UserViewModel mUserViewModel;

  private final ActivityResultLauncher<String> mGetUserInfoResult = registerForActivityResult(
      new ActivityResultContract<String, String>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, String token) {
          Intent intent = new Intent(context, UserInfoActivity.class);
          intent.putExtra("token", token);
          return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
          if (resultCode == 0)
            return intent.getStringExtra("result");

          return "";
        }
      }, result -> Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = ActivityMainBinding.inflate(getLayoutInflater());
    View root = mBinding.getRoot();
    setContentView(root);

    mBinding.setUi(this);
    mBinding.setUserLoginInfo(new UserLoginInfo());

    mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setCancelable(false);

    prepareUserLogin();
  }

  private void prepareUserLogin() {
    mUserViewModel.getUserToken().observe(this, token -> {
      mUserToken = token;
      mProgressDialog.dismiss();
      Toast.makeText(MainActivity.this, token != null ? "login success" : "login error",
          Toast.LENGTH_SHORT).show();
    });
  }

  public void onClickLogin(View loginBtn) {
    mProgressDialog.show();
    mUserViewModel.login(
        mBinding.getUserLoginInfo().username,
        mBinding.getUserLoginInfo().password
    );
  }

  public void onUserShow(View loginBtn) {
    if (TextUtils.isEmpty(mUserToken)) {
      Toast.makeText(MainActivity.this, "please login first", Toast.LENGTH_SHORT).show();
      return;
    }

    mGetUserInfoResult.launch(mUserToken);
  }
}