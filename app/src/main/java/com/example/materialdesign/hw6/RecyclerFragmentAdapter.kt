package com.example.materialdesign.hw6

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialdesign.databinding.RecyclerItemEarthBinding
import com.example.materialdesign.databinding.RecyclerItemHeaderBinding
import com.example.materialdesign.databinding.RecyclerItemMarsBinding
import com.example.myandroidnotes.recycle.Data

const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_HEADER = 3

class RecyclerFragmentAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    private lateinit var list: MutableList<Pair<Data, Boolean>>

    fun setList(newList: List<Pair<Data, Boolean>>) {
        this.list = newList.toMutableList()
    }

    fun setAddToList(newList: List<Pair<Data, Boolean>>, position: Int) {
        this.list = newList.toMutableList()
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: List<Pair<Data, Boolean>>, position: Int) {
        this.list = newList.toMutableList()
        notifyItemRemoved(position)
    }

    fun moveItemToList(newList: List<Pair<Data, Boolean>>, oldPosition: Int, newPosition: Int) {
        this.list = newList.toMutableList()
        notifyItemMoved(oldPosition, newPosition)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].first.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val view =
                    RecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(view.root)
            }
            TYPE_MARS -> {
                val view =
                    RecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(view.root)
            }
            TYPE_HEADER -> {
                val view =
                    RecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(view.root)
            }
            else -> {
                val view =
                    RecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(view.root)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.myBind(list[position])
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun myBind(listItem: Pair<Data, Boolean>) {
            (RecyclerItemMarsBinding.bind(itemView)).apply {
                title.text = listItem.first.someText
                addItemImageView.setOnClickListener {
                    onListItemClickListener.onAddBtnClick(layoutPosition)
                }
                removeItemImageView.setOnClickListener {
                    onListItemClickListener.onRemoveBtnClick(layoutPosition)
                }
                moveItemDown.setOnClickListener {
                    onListItemClickListener.onMoveBtnClick(layoutPosition, layoutPosition + 1)
                }
                moveItemUp.setOnClickListener {
                    onListItemClickListener.onMoveBtnClick(layoutPosition, layoutPosition - 1)
                }
                marsImageView.setOnClickListener {
                    list[layoutPosition] = list[layoutPosition].let {
                        it.first to !it.second
                    }
                    marsDescriptionTextView.visibility =
                        if (list[layoutPosition].second) View.VISIBLE else View.GONE
                    //notifyItemChanged(layoutPosition) // лагает при первом нажатии
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        onListItemClickListener.onMoveBtnClick(fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        onListItemClickListener.onRemoveBtnClick(position)
        notifyItemRemoved(position)
    }
}

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun myBind(listItem: Pair<Data, Boolean>)
}

class HeaderViewHolder(view: View) : BaseViewHolder(view) {
    override fun myBind(listItem: Pair<Data, Boolean>) {
        (RecyclerItemHeaderBinding.bind(itemView)).apply {
            header.text = listItem.first.someText
        }
    }
}

class EarthViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
    override fun myBind(listItem: Pair<Data, Boolean>) {
        (RecyclerItemEarthBinding.bind(itemView)).apply {
            title.text = listItem.first.someText
            descriptionTextView.text = listItem.first.someDescription
        }
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)
    }
}

