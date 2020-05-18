package me.xujichang.lib.paging.callback;

import java.util.List;

/**
 * @author xujichang on 2020/5/18.
 */
public interface ListStatusPageKeyedDataCallBack<Key, Value> {
    public void onResult(List<Value> pData, Key pPage, Key pNext);

    public void onError(String message);
}
