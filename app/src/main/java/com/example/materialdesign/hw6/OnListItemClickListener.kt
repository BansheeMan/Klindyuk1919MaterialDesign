package com.example.materialdesign.hw6

import com.example.myandroidnotes.recycle.Data

interface OnListItemClickListener {

    fun onItemClick(data: Data)
    fun onAddBtnClick(position: Int)
    fun onRemoveBtnClick(position: Int)
    fun onMoveBtnClick(oldPosition: Int, newPosition: Int)
}