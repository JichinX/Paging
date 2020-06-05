package me.xujichang.lib.paging.test;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import me.xujichang.lib.common.base.ListStatus;
import me.xujichang.lib.paging.callback.ListStatusDataSourceFactory;

/**
 * @author xujichang on 2020/5/21.
 */
public class TestDataFactory extends ListStatusDataSourceFactory<Integer,TestData> {

    public TestDataFactory(MutableLiveData<ListStatus> pStatusMutableLiveData) {
        super(pStatusMutableLiveData);
    }

    @Override
    protected DataSource<Integer, TestData> onCreate() {
        return new TestDataSource(getStatusLiveData());
    }
}
