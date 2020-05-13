package me.xujichang.lib.paging.callback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import me.xujichang.lib.common.base.ListStatus;

/**
 * @author xujichang on 2020/5/13.
 */
public abstract class ListStatusPageKeyedDataSource<KEY, VALUE> extends PageKeyedDataSource<KEY, VALUE> {
    private MutableLiveData<ListStatus> mStatusLiveData;

    public ListStatusPageKeyedDataSource(MutableLiveData<ListStatus> pStatusLiveData) {
        mStatusLiveData = pStatusLiveData;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<KEY> params, @NonNull LoadInitialCallback<KEY, VALUE> callback) {
        mStatusLiveData.postValue(ListStatus.INITIALIZE);
        onLoadInitial(params, new ListStatusPageKeyedLoadInitialCallback<>(callback, mStatusLiveData));
    }


    @Override
    public void loadBefore(@NonNull LoadParams<KEY> params, @NonNull LoadCallback<KEY, VALUE> callback) {
        mStatusLiveData.postValue(ListStatus.LOAD_MORE);
        onLoadBefore(params, new ListStatusPageKeyedLoadBeforeCallback<>(callback, mStatusLiveData));
    }


    @Override
    public void loadAfter(@NonNull LoadParams<KEY> params, @NonNull LoadCallback<KEY, VALUE> callback) {
        mStatusLiveData.postValue(ListStatus.LOAD_MORE);
        onLoadAfter(params, new ListStatusPageKeyedLoadAfterCallback<>(callback, mStatusLiveData));
    }

    protected abstract void onLoadInitial(LoadInitialParams<KEY> pParams, ListStatusPageKeyedLoadInitialCallback<KEY, VALUE> pCallback);

    protected abstract void onLoadBefore(LoadParams<KEY> pParams, ListStatusPageKeyedLoadBeforeCallback<KEY, VALUE> pCallback);

    protected abstract void onLoadAfter(LoadParams<KEY> pParams, ListStatusPageKeyedLoadAfterCallback<KEY, VALUE> pCallback);

    public static class ListStatusPageKeyedLoadInitialCallback<KEY, VALUE> extends PageKeyedDataSource.LoadInitialCallback<KEY, VALUE> {
        private LoadInitialCallback<KEY, VALUE> mCallback;
        private MutableLiveData<ListStatus> mStatusLiveData;

        public ListStatusPageKeyedLoadInitialCallback(LoadInitialCallback<KEY, VALUE> pCallback, MutableLiveData<ListStatus> pData) {
            mCallback = pCallback;
            mStatusLiveData = pData;
        }

        @Override
        public void onResult(@NonNull List<VALUE> data, int position, int totalCount, @Nullable KEY previousPageKey, @Nullable KEY nextPageKey) {
            mCallback.onResult(data, position, totalCount, previousPageKey, nextPageKey);
            if (!data.isEmpty()) {
                mStatusLiveData.postValue(ListStatus.INITIALIZE_SUCCESS);
            }
        }

        @Override
        public void onResult(@NonNull List<VALUE> data, @Nullable KEY previousPageKey, @Nullable KEY nextPageKey) {
            mCallback.onResult(data, previousPageKey, nextPageKey);
            if (!data.isEmpty()) {
                mStatusLiveData.postValue(ListStatus.INITIALIZE_SUCCESS);
            }
        }

        public void onError() {
            mStatusLiveData.postValue(ListStatus.INITIAL_ERROR);
        }
    }

    public static class ListStatusPageKeyedLoadBeforeCallback<KEY, VALUE> extends PageKeyedDataSource.LoadCallback<KEY, VALUE> {
        private LoadCallback<KEY, VALUE> mCallback;
        private MutableLiveData<ListStatus> mStatusLiveData;

        public ListStatusPageKeyedLoadBeforeCallback(LoadCallback<KEY, VALUE> pCallback, MutableLiveData<ListStatus> pData) {
            mCallback = pCallback;
            mStatusLiveData = pData;
        }

        @Override
        public void onResult(@NonNull List<VALUE> data, @Nullable KEY adjacentPageKey) {
            mCallback.onResult(data, adjacentPageKey);
            mStatusLiveData.postValue(ListStatus.SUCCESS);
        }

        public void onError() {
            mStatusLiveData.postValue(ListStatus.ERROR);
        }

    }

    public static class ListStatusPageKeyedLoadAfterCallback<KEY, VALUE> extends PageKeyedDataSource.LoadCallback<KEY, VALUE> {
        private LoadCallback<KEY, VALUE> mCallback;
        private MutableLiveData<ListStatus> mStatusLiveData;

        public ListStatusPageKeyedLoadAfterCallback(LoadCallback<KEY, VALUE> pCallback, MutableLiveData<ListStatus> pData) {
            mCallback = pCallback;
            mStatusLiveData = pData;
        }

        @Override
        public void onResult(@NonNull List<VALUE> data, @Nullable KEY adjacentPageKey) {
            mCallback.onResult(data, adjacentPageKey);
            mStatusLiveData.postValue(ListStatus.SUCCESS);
        }

        public void onError() {
            mStatusLiveData.postValue(ListStatus.ERROR);
        }
    }
}
