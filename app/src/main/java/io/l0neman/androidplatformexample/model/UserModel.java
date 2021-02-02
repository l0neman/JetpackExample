package io.l0neman.androidplatformexample.model;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.l0neman.androidplatformexample.model.bean.UserInfo;

public class UserModel {

  private final ExecutorService mExec = Executors.newCachedThreadPool();
  private final Handler mHandler = new Handler(Looper.getMainLooper());

  private static final UserModel INSTANCE = new UserModel();

  public static UserModel getInstance() {
    return INSTANCE;
  }

  public interface LoginCallback {
    void onSuccess(String token);

    void onError(Throwable e);
  }

  public void login(String username, String password, LoginCallback callback) {
    mExec.submit(() -> {
      SystemClock.sleep(2 * 1000);
      if ("l0ne".equals(username) && "123".equals(password)) {
        mHandler.post(() -> callback.onSuccess("test_token"));
      } else {
        mHandler.post(() -> callback.onError(new Exception("login error")));
      }
    });
  }

  public interface UserInfoCallback {
    void onSuccess(UserInfo userInfo);

    void onError(Throwable e);
  }

  public void getUserInfo(String token, UserInfoCallback callback) {
    final UserInfo l0neman = new UserInfo("l0neman", "19951006");

    mExec.submit(() -> {
      SystemClock.sleep(2 * 1000);
      if ("test_token".equals(token)) {
        mHandler.post(() -> callback.onSuccess(l0neman));
      } else {
        mHandler.post(() -> callback.onError(new Exception("user info error")));
      }
    });
  }

  public void clean() {
    mHandler.removeCallbacksAndMessages(null);
  }
}
