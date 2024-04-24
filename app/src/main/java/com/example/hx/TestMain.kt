/*第三次作业*/
package com.example.hx

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TestMain : AppCompatActivity() {

    private lateinit var logAdapter: LogAdapter
    private val logEntries = mutableListOf<LogEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_main)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        logAdapter = LogAdapter(logEntries)
        recyclerView.adapter = logAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAdd.setOnClickListener {
            showAddLogDialog()
        }
    }


    private fun showAddLogDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_log_fragment, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val editTextEvent = dialogView.findViewById<EditText>(R.id.editTextEvent)
        val btnAddLog = dialogView.findViewById<Button>(R.id.btnAddLog)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val calendar = Calendar.getInstance()
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnAddLog.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth
            val currentCalendar = Calendar.getInstance()
            currentCalendar.set(year, month - 1, day) // month 是从 0 开始计数的，所以要减去 1
            val selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentCalendar.time)

            val event = editTextEvent.text.toString().trim()

            if (event.isNotEmpty()) {
                val logEntry = LogEntry(selectedDate, event)
                logAdapter.addLog(logEntry)
                dialog.dismiss()
            } else {
                editTextEvent.error = "请输入事件"
            }
        }
        dialog.show()
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                AlertDialog.Builder(this@TestMain)
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