package me.xujichang.lib.paging.callback;


import androidx.lifecycle.MutableLiveData;

import me.xujichang.lib.common.base.ListStatus;

public abstract class ListDataSource<KEY, VALUE> extends ListStatusPageKeyedDataSource<KEY, VALUE> {

    public ListDataSource(MutableLiveData<ListStatus> pStatusLiveData) {
        super(pStatusLiveData);
    }

    @Override
    protected void onLoadInitial(LoadInitialParams<KEY> pParams, ListStatusPageKeyedLoadInitialCallback<KEY, VALUE> pCallback) {
        onLoadData(onInitialPage(), onNextKey(onInitialPage()), pParams.requestedLoadSize, pCallback);
    }

    @Override
    protected void onLoadBefore(LoadParams<KEY> pParams, ListStatusPageKeyedLoadBeforeCallback<KEY, VALUE> pCallback) {
//        onLoadData(pParams.key, onPreviousKey(pParams.key), pParams.requestedLoadSize, pCallback);
    }

    @Override
    protected void onLoadAfter(LoadParams<KEY> pParams, ListStatusPageKeyedLoadAfterCallback<KEY, VALUE> pCallback) {
        onLoadData(pParams.key, onNextKey(pParams.key), pParams.requestedLoadSize, pCallback);
    }


    protected abstract void onLoadData(KEY pPage, KEY pNext, int pRequestedLoadSize, ListStatusPageKeyedDataCallBack<KEY, VALUE> pCallback);

    protected abstract KEY onInitialPage();

    protected abstract KEY onPreviousKey(KEY pKey);

    protected abstract KEY onNextKey(KEY pKey);
}
