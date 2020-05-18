package me.xujichang.lib.paging.util;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import me.xujichang.lib.paging.callback.ListBoundaryCallback;
import me.xujichang.lib.common.base.ListStatus;

/**
 * @author xujichang on 2020/5/13.
 */
public class PagingUtil {

    public static PagedList.Config makePagedListConfig(int size) {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(size)
                .setPrefetchDistance(2)
                .setPageSize(size)
                .build();
    }

    public static <KEY, VALUE> LiveData<PagedList<VALUE>> makePagedList(DataSource.Factory<KEY, VALUE> pFactory, MutableLiveData<ListStatus> pStatusLiveData, int size) {
        return new LivePagedListBuilder<>(pFactory, makePagedListConfig(size))
                .setBoundaryCallback(new ListBoundaryCallback<VALUE>(pStatusLiveData))
                .build();
    }
}
