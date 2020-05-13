package me.xujichang.lib.paging.callback;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import me.xujichang.lib.common.base.ListStatus;

/**
 * @author xujichang on 2020/5/13.
 */
public class ListBoundaryCallback<T> extends PagedList.BoundaryCallback<T> {
    private MutableLiveData<ListStatus> mStatusLiveData;

    public ListBoundaryCallback(MutableLiveData<ListStatus> pLiveData) {
        mStatusLiveData = pLiveData;
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        mStatusLiveData.setValue(ListStatus.EMPTY);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        mStatusLiveData.setValue(ListStatus.FRONT_END);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull T itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        mStatusLiveData.setValue(ListStatus.END);
    }
}
