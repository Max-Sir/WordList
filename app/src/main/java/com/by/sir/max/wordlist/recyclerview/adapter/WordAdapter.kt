package com.by.sir.max.wordlist.recyclerview.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.by.sir.max.wordlist.R
import com.by.sir.max.wordlist.entity.Word
import kotlinx.android.synthetic.main.main_list_item.view.*

class WordAdapter() : ListAdapter<Word, WordAdapter.WordViewHolder>(DiffCallback) {
    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    companion object {
        object DiffCallback : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean =
                oldItem === newItem


            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean =
                oldItem.word == newItem.word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.main_list_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.apply {
            item_button.setOnClickListener { }
            item_text.text = Editable.Factory.getInstance().newEditable(item.word)
        }
    }
}
