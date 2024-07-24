package com.spectra.consumer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spectra.consumer.Models.Recent
import com.spectra.consumer.R
import com.spectra.consumer.databinding.AdapterRecentSearchBinding

class RecentSearchAdapter(private val mContext: Context, private val onClick: (Recent) -> Unit) : RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>() {

    companion object {
        const val TAG = "RecentSearchAdapter"
    }

    private val differCallback = object : DiffUtil.ItemCallback<Recent>() {
        override fun areItemsTheSame(
                oldItem: Recent,
                newItem: Recent
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
                oldItem: Recent,
                newItem: Recent
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        return RecentSearchViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.adapter_recent_search,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.binding.tvRecentSearch.text = differ.currentList[position].keyWord
        holder.binding.clRecentSearch.setOnClickListener {
            onClick(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    class RecentSearchViewHolder(val binding: AdapterRecentSearchBinding) : RecyclerView.ViewHolder(binding.root)

}