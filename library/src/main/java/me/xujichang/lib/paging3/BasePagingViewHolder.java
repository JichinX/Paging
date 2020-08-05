package me.xujichang.lib.paging3;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BasePagingViewHolder<T> extends RecyclerView.ViewHolder {
    protected T mData;
    protected ItemClickListener<T> mItemClickListener;
    private final String TAG = getClass().getName();

    public BasePagingViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public BasePagingViewHolder(@NonNull View itemView, ItemClickListener<T> pItemClickListener) {
        super(itemView);
        mItemClickListener = pItemClickListener;
    }

    protected void bindData(T pData) {
        mData = pData;
        if (null == pData) {
            onBindEmpty();
            return;
        }
        Log.d(TAG, getAbsoluteAdapterPosition() + "  onBindData() called");
        onBindData(pData);
    }

    /**
     * 空布局
     */
    protected void onBindEmpty() {
        Log.d(TAG, getAbsoluteAdapterPosition() + "  onBindEmpty() called");
    }

    protected abstract void onBindData(T pData);
}
