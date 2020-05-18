package me.xujichang.lib.paging.base;

import androidx.lifecycle.MutableLiveData;


public abstract class PagedDataSourceFactory<T> extends DataSourceFactory<Integer, T> {

  public PagedDataSourceFactory(MutableLiveData<Boolean> pRefreshing,
    MutableLiveData<AppNetworkState> pNetworkState) {
    super(pRefreshing, pNetworkState);
  }

  protected PagedDataSourceFactory() {
  }
}
