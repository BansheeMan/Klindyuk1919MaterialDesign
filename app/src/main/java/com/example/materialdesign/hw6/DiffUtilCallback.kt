package com.example.materialdesign.hw6

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
    private val oldList: List<Pair<Data, Boolean>>,
    private val newList: List<Pair<Data, Boolean>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].first.id == newList[newItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].first.someText == newList[newItemPosition].first.someText)
                && ((oldList[oldItemPosition].first.someDescription == newList[newItemPosition].first.someDescription))
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return Change(oldItem, newItem)
    }
}