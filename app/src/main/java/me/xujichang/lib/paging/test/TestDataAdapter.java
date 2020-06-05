package me.xujichang.lib.paging.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import me.xujichang.lib.paging.databinding.ItemTestDataBinding;

/**
 * @author xujichang on 2020/5/21.
 */
public class TestDataAdapter extends PagedListAdapter<TestData, TestDataAdapter.Holder> {
    protected TestDataAdapter() {
        super(new DiffUtil.ItemCallback<TestData>() {
            @Override
            public boolean areItemsTheSame(@NonNull TestData oldItem, @NonNull TestData newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull TestData oldItem, @NonNull TestData newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemTestDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindData(getItem(position));
    }

    static class Holder extends RecyclerView.ViewHolder {
        private ItemTestDataBinding mBinding;

        public Holder(ItemTestDataBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void bindData(TestData pItem) {
            mBinding.tvDisplay.setText(pItem.getDisplayName());
        }
    }
}
