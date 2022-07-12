package com.example.materialdesign.hw6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.materialdesign.databinding.FragmentRecyclerBinding
import com.example.myandroidnotes.recycle.Data

class RecyclerFragment : Fragment(), OnListItemClickListener {


    private var _binding: FragmentRecyclerBinding? = null
    private val binding: FragmentRecyclerBinding
        get() = _binding!!
    lateinit var adapter: RecyclerFragmentAdapter

    private val list = arrayListOf(
        Pair(Data("HEADER", "", TYPE_HEADER), false),
        Pair(Data("Earth1", "Earth des", TYPE_EARTH), false),
        Pair(Data("Earth2", "Earth des", TYPE_EARTH), false),
        Pair(Data("Mars3", "Mars des", TYPE_MARS), false),
        Pair(Data("Earth4", "Earth des", TYPE_EARTH), false),
        Pair(Data("Earth5", "Earth des", TYPE_EARTH), false),
        Pair(Data("Earth6", "Earth des", TYPE_EARTH), false),
        Pair(Data("Mars7", "Mars des", TYPE_MARS), false)
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
        adapter = RecyclerFragmentAdapter(this)
        adapter.setList(list)
        binding.recyclerView.adapter = adapter

        binding.recyclerActivityFAB.setOnClickListener {
            onAddBtnClick(1)
        }
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
    }

    override fun onItemClick(data: Data) {

    }

    override fun onAddBtnClick(position: Int) {
        list.add(position, Pair(Data("Mars", "Mars New", TYPE_MARS), false))
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

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}