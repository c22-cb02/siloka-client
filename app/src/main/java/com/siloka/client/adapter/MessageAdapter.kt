package com.siloka.client.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.siloka.client.data.models.MessageModel
import com.siloka.client.databinding.BotMsgBinding
import com.siloka.client.databinding.UserMsgBinding
import com.siloka.client.views.main.MainActivity

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messagesList = mutableListOf<MessageModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding: ViewBinding = when (viewType) {
            0 -> BotMsgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> UserMsgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messageObj: MessageModel = messagesList[position]
        when (messageObj.viewType) {
            0 -> holder.bindBotMsg(messageObj)
            else -> holder.bindUserMsg(messageObj)
        }
    }

    override fun getItemCount(): Int = messagesList.size

    override fun getItemViewType(position: Int): Int {
        Log.d("INFO", messagesList[position].viewType.toString())
        return messagesList[position].viewType
    }

    fun insertMessage(message: MessageModel) {
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }

    inner class MessageViewHolder(
        private val binding: ViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindBotMsg(message: MessageModel) {
            (binding as BotMsgBinding).tvBotMsg.text = message.message
        }

        fun bindUserMsg(message: MessageModel) {
            (binding as UserMsgBinding).tvUserMsg.text = message.message
        }
    }
}
