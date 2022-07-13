package com.example.materialdesign.hw6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.materialdesign.databinding.FragmentRecyclerBinding

class RecyclerFragment : Fragment(), OnListItemClickListener {


    private var _binding: FragmentRecyclerBinding? = null
    private val binding: FragmentRecyclerBinding
        get() = _binding!!
    private lateinit var adapter: RecyclerFragmentAdapter

    private var isNewList = false

    private val list = arrayListOf(
        Pair(Data(0,"HEADER", "", TYPE_HEADER, 2), false),
        Pair(Data(1,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(2,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(3,"Mars", "Mars des", TYPE_MARS), false),
        Pair(Data(4,"Mars", "Mars des", TYPE_MARS), false),
        Pair(Data(5,"Mars", "Mars des", TYPE_MARS), false),
        Pair(Data(6,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(7,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(8,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(9,"Mars", "Mars des", TYPE_MARS), false)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerFragmentAdapter(list,this)
        adapter.setList(list)
        binding.recyclerView.adapter = adapter

        binding.recyclerActivityFAB.setOnClickListener {
            onAddBtnClick(1)
        }
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)

        binding.recyclerActivityDiffUtilFAB.setOnClickListener {
            changeAdapterData()
        }
    }

    private fun changeAdapterData() {
        adapter.setList(createItemList(isNewList).map { it })
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Mars", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Mars", ""), false),
                Pair(Data(5, "Mars", ""), false),
                Pair(Data(6, "Mars", ""), false)
            )
            true -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Jupiter", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Neptune", ""), false),
                Pair(Data(5, "Saturn", ""), false),
                Pair(Data(6, "Mars", ""), false)
            )
        }
    }

    override fun onAddBtnClick(position: Int) {
        list.add(position, Pair(Data(0,"Mars", "Mars New", TYPE_MARS), false))
        adapter.setAddToList(list, position)
    }

    override fun onRemoveBtnClick(position: Int) {
        list.removeAt(position)
        adapter.setRemoveToList(list, position)
    }

    override fun onMoveBtnClick(oldPosition: Int, newPosition: Int) {
        try {
            if (newPosition != 0) {
                list.removeAt(oldPosition).apply {
                    list.add(newPosition, this)
                }
                adapter.moveItemToList(list, oldPosition, newPosition)
            }
        } catch (e: IndexOutOfBoundsException) {
            Toast.makeText(
                requireContext(),
                "Вы пытаетесь выйти за рамки массива",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onFavoriteBtnClick(position: Int) {
        if (list[position].first.weight == 0) {
            list[position].first.weight = 1
        } else {
            list[position].first.weight = 0
        }
        list.sortByDescending { it.first.weight }
        adapter.favoriteItemToList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}