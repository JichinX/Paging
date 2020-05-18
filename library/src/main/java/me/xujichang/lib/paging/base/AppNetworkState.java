package me.xujichang.lib.paging.base;

public class AppNetworkState {

  private String message;
  private Status mStatus;

  public AppNetworkState(Status pStatus, String pMessage) {
    message = pMessage;
    mStatus = pStatus;
  }

  public AppNetworkState(Status pStatus) {
    mStatus = pStatus;
  }

  public static AppNetworkState error(String msg) {
    return new AppNetworkState(Status.FAILED, msg);
  }

  public static AppNetworkState loaded() {
    return new AppNetworkState(Status.SUCCESS);
  }

  public static AppNetworkState loading() {
    return new AppNetworkState(Status.RUNNING);
  }

  private enum Status {
    RUNNING,
    SUCCESS,
    FAILED
  }
}
