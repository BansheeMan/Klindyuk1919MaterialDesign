package com.example.materialdesign.hw6

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(private val itemTouchHelperAdapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (viewHolder is HeaderViewHolder) {
            makeMovementFlags(0,0)

        } else {
            val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
            makeMovementFlags(dragFlag, swipeFlag)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        itemTouchHelperAdapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemTouchHelperAdapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            when(viewHolder){
                is EarthViewHolder -> {
                    viewHolder.onItemSelected()
                }
                is RecyclerFragmentAdapter.MarsViewHolder -> {
                    viewHolder.onItemSelected()
                }
            }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        when(viewHolder){
            is EarthViewHolder -> {
                viewHolder.onItemClear()
            }
            is RecyclerFragmentAdapter.MarsViewHolder -> {
                viewHolder.onItemClear()
            }
        }
        super.clearView(recyclerView, viewHolder)
    }

}