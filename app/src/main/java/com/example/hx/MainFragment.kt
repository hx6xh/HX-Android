package com.example.hx

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment(R.layout.test_main) {

    lateinit var logAdapter: LogAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        logAdapter = LogAdapter(mutableListOf())
        recyclerView.adapter = logAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        btnAdd.setOnClickListener {
            Log.d("MainFragment","hhhhhhh")
            findNavController().navigate(R.id.addLogFragment)
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                AlertDialog.Builder(requireContext())
                    .setMessage("确定要删除这条事件吗？")
                    .setPositiveButton("确定") { _, _ ->
                        logAdapter.removeLog(position)
                    }
                    .setNegativeButton("取消") { _, _ ->
                        logAdapter.notifyItemChanged(position) // 点击取消恢复原状
                    }
                    .setOnCancelListener {
                        logAdapter.notifyItemChanged(position) // 点击空白区域恢复原状
                    }
                    .show()
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
