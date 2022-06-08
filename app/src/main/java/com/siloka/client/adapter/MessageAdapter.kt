package com.siloka.client.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siloka.client.R
import com.siloka.client.data.models.MessageModel

class MessageAdapter(
    private val messageModelArrayList: ArrayList<MessageModel>,
    context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = when (viewType == 0) {
            true -> LayoutInflater.from(parent.context).inflate(
                R.layout.user_msg, parent, false)
            false -> LayoutInflater.from(parent.context).inflate(
                R.layout.bot_msg, parent, false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageModelArrayList[position]
        message.message = message.message
        (holder as ViewHolder).message.text = message.message
    }

    override fun getItemCount(): Int {
        // return the size of array list
        return messageModelArrayList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var message: TextView = view.findViewById(R.id.message) as TextView
    }
}
