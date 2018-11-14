package com.rascarlo.arch.packages.adapters;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.ResultAdapterCallback;
import com.rascarlo.arch.packages.databinding.ResultItemBinding;
import com.rascarlo.arch.packages.viewholders.ResultAdapterViewHolder;

public class ResultAdapter extends PagedListAdapter<Result, ResultAdapterViewHolder> {

    private final ResultAdapterCallback resultAdapterCallback;

    public ResultAdapter(ResultAdapterCallback resultAdapterCallback) {
        super(DIFF_CALLBACK);
        this.resultAdapterCallback = resultAdapterCallback;
    }

    private static final DiffUtil.ItemCallback<Result> DIFF_CALLBACK = new DiffUtil.ItemCallback<Result>() {
        @Override
        public boolean areItemsTheSame(Result oldItem, Result newItem) {
            return TextUtils.equals(oldItem.getPkgname(), newItem.getPkgname())
                    && TextUtils.equals(oldItem.getPkgver(), newItem.getPkgrel())
                    && TextUtils.equals(oldItem.getFilename(), newItem.getFilename())
                    && TextUtils.equals(oldItem.getArch(), newItem.getArch())
                    && TextUtils.equals(oldItem.getRepo(), newItem.getRepo());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ResultAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ResultItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.result_item, viewGroup, false);
        binding.setResultAdapterCallback(resultAdapterCallback);
        return new ResultAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapterViewHolder resultAdapterViewHolder, int i) {
        if (getItem(i) != null) {
            Result result = getItem(i);
            resultAdapterViewHolder.binding.setResult(result);
            resultAdapterViewHolder.bindResult(result);
        }
    }
}