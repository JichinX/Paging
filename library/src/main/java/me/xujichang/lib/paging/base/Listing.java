package me.xujichang.lib.paging.base;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class Listing<T> {

  /**
   * 刷新状态
   */
  private LiveData<Boolean> mRefreshing;
  /**
   * 网络状态
   */
  private LiveData<AppNetworkState> mNetworkState;
  /**
   * 数据
   */
  private LiveData<PagedList<T>> mData;


  public Listing(LiveData<Boolean> pRefreshing, LiveData<AppNetworkState> pNetworkState,
    LiveData<PagedList<T>> pData) {
    mRefreshing = pRefreshing;
    mNetworkState = pNetworkState;
    mData = pData;
  }

  public Listing(DataSourceFactory<Integer, T> pFactory) {
    PagedList.Config vConfig = new PagedList
      .Config
      .Builder()
//      .setPageSize(Const.Request.PAGE_SIZE)
//      .setInitialLoadSizeHint(Const.Request.PAGE_SIZE)
      .build();
    LiveData<PagedList<T>> vListLiveData = new LivePagedListBuilder<>(pFactory, vConfig).build();
  }

  public LiveData<Boolean> getRefreshing() {
    return mRefreshing;
  }

  public void setRefreshing(LiveData<Boolean> pRefreshing) {
    mRefreshing = pRefreshing;
  }

  public LiveData<AppNetworkState> getNetworkState() {
    return mNetworkState;
  }

  public void setNetworkState(LiveData<AppNetworkState> pNetworkState) {
    mNetworkState = pNetworkState;
  }

  public LiveData<PagedList<T>> getData() {
    return mData;
  }

  public void setData(LiveData<PagedList<T>> pData) {
    mData = pData;
  }

}
