package com.mt.roomsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mt.roomsample.model.Word
import com.mt.roomsample.R

/**
 * time : 8/19/25
 *
 * - WordViewHolder 类，让我们能够将文本绑定到 TextView。此类会公开用于处理布局扩充问题的 create() 静态函数。
 * - WordsComparator 定义了在两个字词相同或内容相同的情况下应如何计算。
 * - WordListAdapter 将在 onCreateViewHolder 中创建 WordViewHolder，并将其绑定到 onBindViewHolder。
 */
class WordListAdapter : ListAdapter<Word, WordViewHolder>(WordsComparator()) {

	init {
		setHasStableIds(true)
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int,
	): WordViewHolder {
		return WordViewHolder.create(parent)
	}

	override fun onBindViewHolder(
		holder: WordViewHolder,
		position: Int,
	) {
		val current = getItem(position)
		holder.bind(current.word)
	}
}

class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	private val wordItemView: TextView = itemView.findViewById(R.id.textView)

	fun bind(text: String?) {
		wordItemView.text = text
	}

	companion object {
		fun create(parent: ViewGroup): WordViewHolder {
			val view: View = LayoutInflater.from(parent.context)
				.inflate(R.layout.layout_recycler_item, parent, false)
			return WordViewHolder(view)
		}
	}
}

class WordsComparator : DiffUtil.ItemCallback<Word>() {
	override fun areItemsTheSame(
		oldItem: Word,
		newItem: Word,
	): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(
		oldItem: Word,
		newItem: Word,
	): Boolean {
		return oldItem.word == newItem.word
	}
}