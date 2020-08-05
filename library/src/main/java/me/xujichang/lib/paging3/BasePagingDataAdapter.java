package me.xujichang.lib.paging3;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

/**
 * @author xujichang on 2020/5/27.
 */
public abstract class BasePagingDataAdapter<V, VH extends BasePagingViewHolder<V>> extends PagingDataAdapter<V, VH> {

    public void setItemClickListener(ItemClickListener<V> pClickListener) {
        mItemClickListener = pClickListener;
    }

    protected ItemClickListener<V> mItemClickListener;

    public BasePagingDataAdapter() {
        super(new DiffUtil.ItemCallback<V>() {
            @Override
            public boolean areItemsTheSame(@NonNull V oldItem, @NonNull V newItem) {
                //判断对象是否是一样的
                return oldItem.equals(newItem);
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull V oldItem, @NonNull V newItem) {
                //判断数据是否是相同的
                return oldItem.equals(newItem);
            }
        });
    }

    protected View inflate(ViewGroup pContext, int pRes) {
        return LayoutInflater.from(pContext.getContext()).inflate(pRes, pContext, false);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bindData(getItem(position));
    }
}
