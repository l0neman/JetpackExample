package io.l0neman.androidplatformexample.repository;

import io.l0neman.androidplatformexample.model.UserModel;
import io.l0neman.androidplatformexample.model.bean.UserInfo;

public class UserRepository {
  private static final UserRepository INSTANCE = new UserRepository();

  public static UserRepository getInstance() {
    return INSTANCE;
  }

  public interface LoginCallback {
    void onToken(String token);

    void onMessage(String message);
  }

  public interface UserInfoCallback {
    void onUserInfo(UserInfo userInfo);

    void onMessage(String message);
  }

  public void login(String username, String password, LoginCallback callback) {
    UserModel.getInstance().login(username, password, new UserModel.LoginCallback() {
      @Override
      public void onSuccess(String token) {
        callback.onToken(token);
      }

      @Override
      public void onError(Throwable e) {
        callback.onMessage("登录出错: " + e.getMessage());
      }
    });
  }

  public void getUserInfo(String token, UserInfoCallback callback) {
    UserModel.getInstance().getUserInfo(token, new UserModel.UserInfoCallback() {
      @Override
      public void onSuccess(UserInfo userInfo) {
        callback.onUserInfo(userInfo);
      }

      @Override
      public void onError(Throwable e) {
        callback.onMessage("获取用户信息出错：" + e);
      }
    });
  }
}
