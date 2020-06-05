package me.xujichang.lib.paging.test;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.ResourceMaybeObserver;
import io.reactivex.plugins.RxJavaPlugins;
import me.xujichang.lib.common.base.ListStatus;
import me.xujichang.lib.paging.callback.ListDataSource;
import me.xujichang.lib.paging.callback.ListStatusPageKeyedDataCallBack;
import me.xujichang.lib.paging.callback.ListStatusPageKeyedDataSource;

/**
 * @author xujichang on 2020/5/21.
 */
public class TestDataSource extends ListDataSource<Integer, TestData> {

    private static final String TAG = "TestDataSource";

    public TestDataSource(MutableLiveData<ListStatus> pStatusLiveData) {
        super(pStatusLiveData);
    }

    @Override
    protected void onLoadData(final Integer pPage, final Integer pNext, final int pRequestedLoadSize, final ListStatusPageKeyedDataCallBack<Integer, TestData> pCallback) {

        Maybe.create(new MaybeOnSubscribe<List<TestData>>() {
            @Override
            public void subscribe(MaybeEmitter<List<TestData>> emitter) throws Exception {
                List<TestData> vList = new ArrayList<>();
                for (int index = 0; index < pRequestedLoadSize; index++) {
                    vList.add(new TestData(pPage * pRequestedLoadSize + index));
                }
                emitter.onSuccess(vList);
                emitter.onComplete();
            }
        }).delay(3000, TimeUnit.MILLISECONDS)
                .subscribe(new ResourceMaybeObserver<List<TestData>>() {

                    @Override
                    public void onSuccess(List<TestData> pData) {
                        pCallback.onResult(pData, pPage, pNext);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected Integer onInitialPage() {
        return 1;
    }

    @Override
    protected Integer onPreviousKey(Integer pKey) {
        return pKey - 1;
    }

    @Override
    protected Integer onNextKey(Integer pKey) {
        return pKey + 1;
    }
}
