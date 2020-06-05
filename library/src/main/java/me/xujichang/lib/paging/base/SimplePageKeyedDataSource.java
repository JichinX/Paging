package me.xujichang.lib.paging.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;


import java.util.List;

import io.reactivex.observers.ResourceMaybeObserver;


public abstract class SimplePageKeyedDataSource<KEY, VALUE> extends
  PageKeyedDataSource<KEY, VALUE> {

  private MutableLiveData<Boolean> mRefreshing;
  private MutableLiveData<AppNetworkState> mState;



  @Override
  public void loadInitial(@NonNull LoadInitialParams<KEY> params,
    @NonNull LoadInitialCallback<KEY, VALUE> callback) {
    startRefreshing();
    onLoadData(getInitialKey(), params.requestedLoadSize, convertCallback(callback, mRefreshing));
  }


  @Override
  public void loadBefore(@NonNull LoadParams<KEY> params,
    @NonNull LoadCallback<KEY, VALUE> callback) {
    //...
  }

  @Override
  public void loadAfter(@NonNull LoadParams<KEY> params,
    @NonNull LoadCallback<KEY, VALUE> callback) {
    startRefreshing();
    onLoadData(params.key, params.requestedLoadSize, convertCallback(callback, mRefreshing));
  }


  protected abstract KEY getInitialKey();

  protected abstract void onLoadData(KEY page, int size,
    PagedUnionConvert<KEY, VALUE> pConvertCallback);


  protected void startRefreshing() {
    mRefreshing.postValue(true);
  }

  protected void stopRefreshing() {
    mRefreshing.postValue(false);
  }

  protected void postState(AppNetworkState pState) {
    mState.postValue(pState);
  }

  protected <K, V> PagedUnionConvert<K, V> convertCallback(LoadInitialCallback<K, V> pCallback,
    MutableLiveData<Boolean> pRefreshing) {
    return new PagedInitialConvert<>(pCallback, pRefreshing);
  }

  protected <K, V> PagedUnionConvert<K, V> convertCallback(LoadCallback<K, V> pCallback,
    MutableLiveData<Boolean> pRefreshing) {
    return new PagedConvert<>(pCallback, pRefreshing);
  }

  public interface PagedUnionConvert<K, V> {

    void onResult(List<V> values, K previous, K next);
  }

  public class SimplePagedUnionConvert<K, V> implements PagedUnionConvert<K, V> {

    @Override
    public void onResult(List<V> values, K previous, K next) {

    }

    public void onResult(List<V> values, K next) {
      onResult(values, null, next);
    }
  }

  public static class PagedInitialConvert<K, V> implements PagedUnionConvert<K, V> {

    private LoadInitialCallback<K, V> mInitialCallback;
    private MutableLiveData<Boolean> mRefreshing;

    public PagedInitialConvert(LoadInitialCallback<K, V> pCallback,
      MutableLiveData<Boolean> pRefreshing) {
      mInitialCallback = pCallback;
      mRefreshing = pRefreshing;
    }

    @Override
    public void onResult(List<V> values, K previous, K next) {
      mInitialCallback.onResult(values, previous, next);
      mRefreshing.postValue(false);
    }
  }

  public static class PagedConvert<K, V> implements PagedUnionConvert<K, V> {

    private LoadCallback<K, V> mLoadCallback;
    private MutableLiveData<Boolean> mRefreshing;

    public PagedConvert(LoadCallback<K, V> pCallback, MutableLiveData<Boolean> pRefreshing) {
      mLoadCallback = pCallback;
      mRefreshing = pRefreshing;
    }

    @Override
    public void onResult(List<V> values, K previous, K next) {
      mLoadCallback.onResult(values, next);
      mRefreshing.postValue(false);
    }
  }

  public static abstract class PagedMaybeObserver<K, V, D> extends ResourceMaybeObserver<D> {

    protected PagedUnionConvert<K, V> mConvert;
    private K mPage;
    private int size;

    public PagedMaybeObserver(PagedUnionConvert<K, V> pConvert) {
      mConvert = pConvert;
    }

    public PagedMaybeObserver(PagedUnionConvert<K, V> pConvertCallback, K pPage, int pSize) {
      mConvert = pConvertCallback;
      mPage = pPage;
      size = pSize;
    }

    public int getSize() {
      return size;
    }

    public K getPage() {
      return mPage;
    }
  }

  protected abstract KEY previous(KEY page);

  protected abstract KEY next(KEY page);
}
