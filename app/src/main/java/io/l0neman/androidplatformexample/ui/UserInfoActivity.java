package io.l0neman.androidplatformexample.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.l0neman.androidplatformexample.databinding.ActivityUserInfoBinding;
import io.l0neman.androidplatformexample.model.bean.UserInfo;
import io.l0neman.androidplatformexample.viewmodel.UserViewModel;

public class UserInfoActivity extends AppCompatActivity {

  private ActivityUserInfoBinding mBinding;
  private ProgressDialog mProgressDialog;
  private UserViewModel mUserViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mBinding = ActivityUserInfoBinding.inflate(getLayoutInflater());
    mBinding.setUserInfo(new UserInfo());

    setContentView(mBinding.getRoot());

    mBinding.setUi(this);

    mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setCancelable(false);

    getUserInfoAndShow();
  }

  private void getUserInfoAndShow() {
    String userToken = getIntent().getStringExtra("token");

    mProgressDialog.show();
    mUserViewModel.getUserInfo(userToken).observe(this, userInfo -> {
      mProgressDialog.dismiss();

      if (userInfo != null)
        mBinding.setUserInfo(userInfo);
      else
        mBinding.setUserInfo(new UserInfo("load error", ""));
    });
  }

  public void onResult(View v) {
    Intent data = new Intent();
    data.putExtra("result", "show ok");
    setResult(0, data);
    finish();
  }
}