package com.example.materialdesign.hw6

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.materialdesign.R
import com.example.materialdesign.databinding.RecyclerItemEarthBinding
import com.example.materialdesign.databinding.RecyclerItemHeaderBinding
import com.example.materialdesign.databinding.RecyclerItemMarsBinding

const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_HEADER = 3

class RecyclerFragmentAdapter(
    private var list: MutableList<Pair<Data, Boolean>>,
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {


    fun setList(newList: List<Pair<Data, Boolean>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(list,newList))
        result.dispatchUpdatesTo(this)
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

    fun favoriteItemToList(newList: List<Pair<Data, Boolean>>) {
        this.list = newList.toMutableList()
        notifyDataSetChanged()
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

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val res = createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
            if(res.oldData.first.someText!=res.newData.first.someText)
                (holder as MarsViewHolder).itemView.findViewById<TextView>(R.id.title).text =res.newData.first.someText
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.myBind(list[position])
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun myBind(listItem: Pair<Data, Boolean>) {
            (RecyclerItemMarsBinding.bind(itemView)).apply {
                title.text = listItem.first.someText
                marsImageView.setImageResource(R.drawable.bg_mars)
                if (list[layoutPosition].first.weight == 1)
                    favorite.setImageResource(R.drawable.favorite) else {
                    favorite.setImageResource(R.drawable.favorite_border)
                }
                addItemImageView.setOnClickListener {
                    onListItemClickListener.onAddBtnClick(layoutPosition)
                }
                removeItemImageView.setOnClickListener {
                    onListItemClickListener.onRemoveBtnClick(layoutPosition)
                }
                moveItemDown.setOnClickListener {
                    if(layoutPosition == itemCount-1) {
                        onListItemClickListener.onMoveBtnClick(layoutPosition, 1)
                    } else {
                        onListItemClickListener.onMoveBtnClick(layoutPosition, layoutPosition + 1)
                    }
                }
                moveItemUp.setOnClickListener {
                    if(layoutPosition == 1) {
                        onListItemClickListener.onMoveBtnClick(layoutPosition, itemCount - 1)
                    } else {
                        onListItemClickListener.onMoveBtnClick(layoutPosition, layoutPosition - 1)
                    }
                }
                marsImageView.setOnClickListener {
                    list[layoutPosition] = list[layoutPosition].let {
                        it.first to !it.second
                    }
                    marsDescriptionTextView.visibility =
                        if (list[layoutPosition].second) View.VISIBLE else View.GONE
                    //notifyItemChanged(layoutPosition) // лагает при первом нажатии
                }
                favorite.setOnClickListener {
                    onListItemClickListener.onFavoriteBtnClick(layoutPosition)
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

    inner class EarthViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun myBind(listItem: Pair<Data, Boolean>) {
            (RecyclerItemEarthBinding.bind(itemView)).apply {
                title.text = listItem.first.someText
                if (list[layoutPosition].first.weight == 1)
                    favorite.setImageResource(R.drawable.favorite) else {
                    favorite.setImageResource(R.drawable.favorite_border)
                }
                descriptionTextView.text = listItem.first.someDescription
                favorite.setOnClickListener {
                    onListItemClickListener.onFavoriteBtnClick(layoutPosition)
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



