package me.xujichang.lib.paging.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;


public abstract class DataSourceFactory<KEY, VALUE> extends DataSource.Factory<KEY, VALUE> {

  private MutableLiveData<Boolean> mRefreshing;
  private MutableLiveData<AppNetworkState> mState;
  private MutableLiveData<DataSource<KEY, VALUE>> mSource;

  public DataSourceFactory() {
    mSource = new MutableLiveData<>();
    mRefreshing = new MutableLiveData<>();
    mState = new MutableLiveData<>();
  }

  public DataSourceFactory(MutableLiveData<Boolean> pRefreshing,
    MutableLiveData<AppNetworkState> pState) {
    mRefreshing = pRefreshing;
    mState = pState;
    mSource = new MutableLiveData<>();
  }

  @NonNull
  @Override
  public DataSource<KEY, VALUE> create() {
    DataSource<KEY, VALUE> vSource = onCreate();
    mSource.postValue(vSource);
    return vSource;
  }

  public MutableLiveData<Boolean> getRefreshing() {
    return mRefreshing;
  }

  public MutableLiveData<AppNetworkState> getState() {
    return mState;
  }

  public MutableLiveData<DataSource<KEY, VALUE>> getSource() {
    return mSource;
  }

  protected abstract DataSource<KEY, VALUE> onCreate();
}
