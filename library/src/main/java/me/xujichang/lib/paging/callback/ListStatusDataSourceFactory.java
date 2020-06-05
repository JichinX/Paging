package me.xujichang.lib.paging.callback;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import me.xujichang.lib.common.base.ListStatus;
import me.xujichang.lib.paging.base.DataSourceFactory;

/**
 * @author xujichang on 2020/5/21.
 */
public abstract class ListStatusDataSourceFactory<KEY, VALUE> extends DataSourceFactory<KEY, VALUE> {
    private MutableLiveData<ListStatus> mStatusMutableLiveData;

    public ListStatusDataSourceFactory(MutableLiveData<ListStatus> pStatusMutableLiveData) {
        mStatusMutableLiveData = pStatusMutableLiveData;
    }

    public MutableLiveData<ListStatus> getStatus() {
        return mStatusMutableLiveData;
    }

    public MutableLiveData<ListStatus> getStatusLiveData() {
        return mStatusMutableLiveData;
    }
}
