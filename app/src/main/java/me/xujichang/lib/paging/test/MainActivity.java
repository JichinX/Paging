package me.xujichang.lib.paging.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import me.xujichang.lib.common.base.ListStatus;
import me.xujichang.lib.paging.databinding.ActivityMainBinding;
import me.xujichang.lib.paging.util.PagingUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;
    private TestDataAdapter mAdapter;
    private MutableLiveData<ListStatus> mStatusMutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initRv(mBinding.rvData);
        initData();
    }

    private void initData() {
        TestDataFactory vFactory = new TestDataFactory(mStatusMutableLiveData);
        LiveData<PagedList<TestData>> vLiveData = PagingUtil.makePagedList(vFactory, vFactory.getStatusLiveData(), 10);
        bindList(this, mAdapter, vLiveData, mStatusMutableLiveData);
    }

    private void initRv(RecyclerView pRecyclerView) {
        mAdapter = new TestDataAdapter();
        pRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pRecyclerView.setAdapter(mAdapter);
    }

    public static <T, VH extends RecyclerView.ViewHolder> void bindList(LifecycleOwner pFragment, final PagedListAdapter<T, VH> pAdapter, LiveData<PagedList<T>> pObjects, LiveData<ListStatus> pStatusLiveData) {
        pObjects
                .observe(pFragment, new Observer<PagedList<T>>() {
                    @Override
                    public void onChanged(PagedList<T> pTS) {
                        pAdapter.submitList(pTS);
                    }
                });
        pStatusLiveData
                .observe(pFragment, new Observer<ListStatus>() {
                    @Override
                    public void onChanged(ListStatus pStatus) {
                        Log.i(TAG, "onChanged: " + pStatus);
                    }
                });
    }
}