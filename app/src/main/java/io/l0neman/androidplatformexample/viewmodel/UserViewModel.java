package io.l0neman.androidplatformexample.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.l0neman.androidplatformexample.model.bean.UserInfo;
import io.l0neman.androidplatformexample.repository.UserRepository;

public class UserViewModel extends ViewModel {
  private MutableLiveData<String> mUserToken;
  private MutableLiveData<UserInfo> mUserInfo = new MutableLiveData<>();

  private UserRepository.LoginCallback mLoginCallback;
  private UserRepository.UserInfoCallback mUserInfoCallback;

  public MutableLiveData<String> getUserToken() {
    if (mUserToken == null)
      mUserToken = new MutableLiveData<>();

    return mUserToken;
  }

  public void login(String username, String password) {
    if (mLoginCallback == null)
      mLoginCallback = new UserRepository.LoginCallback() {
        @Override
        public void onToken(String token) {
          mUserToken.setValue(token);
        }

        @Override
        public void onMessage(String message) {
          mUserToken.setValue(null);
        }
      };

    UserRepository.getInstance().login(username, password, mLoginCallback);
  }

  public MutableLiveData<UserInfo> getUserInfo(String token) {
    if (mUserInfo == null)
      mUserInfo = new MutableLiveData<>();

    loadUserInfo(token);
    return mUserInfo;
  }

  private void loadUserInfo(String token) {
    if (mUserInfoCallback == null)
      mUserInfoCallback = new UserRepository.UserInfoCallback() {
        @Override
        public void onUserInfo(UserInfo userInfo) {
          mUserInfo.setValue(userInfo);
        }

        @Override
        public void onMessage(String message) {
          mUserInfo.setValue(null);
        }
      };

    UserRepository.getInstance().getUserInfo(token, mUserInfoCallback);
  }
}
