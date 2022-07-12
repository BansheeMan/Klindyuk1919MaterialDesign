package com.example.materialdesign.hw6

interface OnListItemClickListener {

    fun onItemClick(data: Data)
    fun onAddBtnClick(position: Int)
    fun onRemoveBtnClick(position: Int)
    fun onMoveBtnClick(oldPosition: Int, newPosition: Int)
    fun onFavoriteBtnClick(position: Int)
}