package com.example.hx

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class AddLogFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 这里设置对话框样式和其他初始化操作
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_log_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
        val editTextEvent = view.findViewById<EditText>(R.id.editTextEvent)
        val btnAddLog = view.findViewById<Button>(R.id.btnAddLog)

        val calendar = Calendar.getInstance()
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null)

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
                (requireActivity() as MainFragment).logAdapter.addLog(logEntry)
                // 关闭对话框
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            } else {
                editTextEvent.error = "请输入事件"
            }
        }
    }

}
