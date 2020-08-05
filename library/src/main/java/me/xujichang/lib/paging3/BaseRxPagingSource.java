package me.xujichang.lib.paging3;

import android.util.Log;

import androidx.paging.rxjava2.RxPagingSource;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @param <IN>    接口返回的数据
 * @param <VALUE> Paging中要使用到的数据类型
 */
public abstract class BaseRxPagingSource<IN, VALUE> extends RxPagingSource<Integer, VALUE> implements ISourceCheck<IN> {
    private static final String TAG = "BaseRxPagingSource";
    private final AtomicBoolean mHasMore = new AtomicBoolean(true);
    private final AtomicInteger mPagerControl = new AtomicInteger(1);

    public void checkMoreData(List<?> pList, int pLoadSize) {
        mHasMore.set(pList.size() == pLoadSize);
    }

    protected void checkPageOrRefresh(LoadParams<Integer> pLoadParams) {
        resetPage(pLoadParams);
    }

    private void resetPage(LoadParams<Integer> pLoadParams) {
        Integer refreshPage = pLoadParams.getKey();
        if (null == refreshPage) {
            mPagerControl.set(1);
        } else {
            mPagerControl.set(refreshPage);
        }
    }

    protected Integer getNextPage() {
        if (!mHasMore.get()) {
            return null;
        }
        return mPagerControl.incrementAndGet();
    }

    protected Integer getPage() {
        return mPagerControl.get();
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, VALUE>> loadSingle(@NotNull LoadParams<Integer> pLoadParams) {
        Log.d(TAG, "loadSingle() called with: pLoadParams = [" + pLoadParams + "]");
        checkPageOrRefresh(pLoadParams);
        int vLoadSize = pLoadParams.getLoadSize();
        return createRequest(getPage(), pLoadParams.getLoadSize())
                .subscribeOn(Schedulers.io())
                .map(new Function<IN, LoadResult<Integer, VALUE>>() {
                    @Override
                    public LoadResult<Integer, VALUE> apply(@NonNull IN pList) throws Exception {
                        checkResult(pList);
                        List<VALUE> vSituations = convertResult(pList);
                        checkMoreData(vSituations, vLoadSize);
                        return new LoadResult.Page<>(vSituations, null, getNextPage());
                    }
                })
                .onErrorReturn(LoadResult.Error::new);
    }

    /**
     * 创建数据来源
     *
     * @param pPage
     * @param pSize
     * @return
     */
    protected abstract Single<IN> createRequest(Integer pPage, int pSize);

    /**
     * 数据转换，将接口返回的数据转换为要返回给Adapterd的List数据
     *
     * @param pList
     * @return
     */
    protected abstract List<VALUE> convertResult(IN pList);
}
