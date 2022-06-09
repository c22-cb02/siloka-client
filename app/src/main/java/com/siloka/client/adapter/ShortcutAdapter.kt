package com.siloka.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siloka.client.R
import com.siloka.client.data.models.TopicModel

class ShortcutAdapter(
    private val topicsList: ArrayList<TopicModel>
): RecyclerView.Adapter<ShortcutAdapter.TopicViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic, parent, false)
        return TopicViewHolder(view)
    }

    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTopic: TextView = itemView.findViewById(R.id.tv_topic)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val question = topicsList[position].question
        holder.tvTopic.text = question
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(topicsList[holder.adapterPosition].question) }
    }

    override fun getItemCount(): Int = topicsList.size

    interface OnItemClickCallback {
        fun onItemClicked(question: String)
    }
}