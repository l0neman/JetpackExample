package io.l0neman.androidplatformexample.model.bean;

public class UserInfo {
  public String name;
  public String age;

  public UserInfo() {}

  public UserInfo(String name, String age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
        "name='" + name + '\'' +
        ", age='" + age + '\'' +
        '}';
  }
}
