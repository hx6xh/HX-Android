package com.example.hx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogAdapter(private val logEntries: MutableList<LogEntry>) : RecyclerView.Adapter<LogAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val textEvent: TextView = itemView.findViewById(R.id.textEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val logEntry = logEntries[position]
        holder.textDate.text = logEntry.date
        holder.textEvent.text = logEntry.event
    }

    override fun getItemCount(): Int {
        return logEntries.size
    }

    fun addLog(logEntry: LogEntry) {
        logEntries.add(logEntry)
        logEntries.sortBy { it.date }
        notifyDataSetChanged()
    }

    fun removeLog(position: Int) {
        logEntries.removeAt(position)
        notifyItemRemoved(position)
    }
}