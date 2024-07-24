package com.spectra.consumer.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spectra.consumer.Models.KeyWord
import com.spectra.consumer.R
import com.spectra.consumer.databinding.AdapterKeywordBinding

class KeyWordAdapter(private val mContext: Context, private val onClick: (KeyWord) -> Unit) : RecyclerView.Adapter<KeyWordAdapter.KeyWordViewHolder>() {

    companion object {
        const val TAG = "KeyWordAdapter"
    }

    private val differCallback = object : DiffUtil.ItemCallback<KeyWord>() {
        override fun areItemsTheSame(
                oldItem: KeyWord,
                newItem: KeyWord
        ): Boolean {
            return oldItem.id == newItem.id;
        }

        override fun areContentsTheSame(
                oldItem: KeyWord,
                newItem: KeyWord
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyWordViewHolder {
        return KeyWordViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.adapter_keyword,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: KeyWordViewHolder, position: Int) {
        holder.binding.tvKeyword.text = differ.currentList[position].keyWord;
        holder.binding.cvKeyword.setOnClickListener {
            onClick(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class KeyWordViewHolder(val binding: AdapterKeywordBinding) : RecyclerView.ViewHolder(binding.root)

}