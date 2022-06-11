package com.siloka.client.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.siloka.client.R
import com.siloka.client.data.models.MessageModel
import com.siloka.client.databinding.*
import com.siloka.client.views.main.MainActivity

class MessageAdapter (
    private val context: Context
): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messagesList = mutableListOf<MessageModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding: ViewBinding = when (viewType) {
            1 -> UserMsgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            2 -> ShortcutHcBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            3 -> ResponseFeedbackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            4 -> DirectToCsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            5 -> BotLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> BotMsgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return MessageViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messageObj: MessageModel = messagesList[position]
        when (messageObj.viewType) {
            1 -> holder.bindUserMsg(messageObj)
            2 -> holder.bindShortcutHc()
            3 -> holder.bindResponseFeedback()
            4 -> holder.bindDirectToCs()
            5 -> holder.bindBotLoading()
            else -> holder.bindBotMsg(messageObj)
        }
    }

    override fun getItemCount(): Int = messagesList.size

    override fun getItemViewType(position: Int): Int = messagesList[position].viewType

    fun insertMessage(message: MessageModel) {
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }

    fun removeMessage(message: MessageModel) {
        val messagePosition = messagesList.indexOf(message)
        this.messagesList.removeAt(messagePosition)
        notifyItemRemoved(messagePosition)
    }

    inner class MessageViewHolder(
        private val binding: ViewBinding,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindUserMsg(message: MessageModel) {
            (binding as UserMsgBinding).tvUserMsg.text = message.message
        }

        fun bindShortcutHc() {
            (binding as ShortcutHcBinding).apply {
                tvTopic1.setOnClickListener {
                    (context as MainActivity).sendMessage(tvTopic1.text.toString())
                }
                tvTopic2.setOnClickListener {
                    (context as MainActivity).sendMessage(tvTopic2.text.toString())
                }
                tvTopic3.setOnClickListener {
                    (context as MainActivity).sendMessage(tvTopic3.text.toString())
                }
                tvTopic4.setOnClickListener {
                    (context as MainActivity).sendMessage(tvTopic4.text.toString())
                }
            }
        }

        fun bindResponseFeedback() {
            (binding as ResponseFeedbackBinding).apply {
                btnPromptYes.setOnClickListener {
                    (context as MainActivity).sendFeedback(true)
                    removeFeedbackOnClickListener()
                }
                btnPromptNo.setOnClickListener {
                    (context as MainActivity).sendFeedback(false)
                    removeFeedbackOnClickListener()
                }
            }
        }

        private fun removeFeedbackOnClickListener() {
            (binding as ResponseFeedbackBinding).apply {
                btnPromptYes.alpha = 0.5F
                btnPromptYes.setOnClickListener {
                    return@setOnClickListener
                }
                btnPromptNo.alpha = 0.5F
                btnPromptNo.setOnClickListener {
                    return@setOnClickListener
                }
            }
        }

        fun bindDirectToCs() {
            (binding as DirectToCsBinding).apply {
                btnPromptYes.setOnClickListener {
                    (context as MainActivity).sendToCs(true)
                    removeDirectToCsOnClickListener()
                }
                btnPromptNo.setOnClickListener {
                    (context as MainActivity).sendToCs(false)
                    removeDirectToCsOnClickListener()
                }
            }
        }

        private fun removeDirectToCsOnClickListener() {
            (binding as DirectToCsBinding).apply {
                btnPromptYes.alpha = 0.5F
                btnPromptYes.setOnClickListener {
                    return@setOnClickListener
                }
                btnPromptNo.alpha = 0.5F
                btnPromptNo.setOnClickListener {
                    return@setOnClickListener
                }
            }
        }

        fun bindBotMsg(message: MessageModel) {
            (binding as BotMsgBinding).tvBotMsg.text = message.message
        }

        fun bindBotLoading() {
            val botLoadingBinding = binding as BotLoadingBinding
            botLoadingBinding.llLoading.removeAllViewsInLayout()

            val lazyLoader = LazyLoader(context, 10, 6,
                ContextCompat.getColor(context, R.color.white),
                ContextCompat.getColor(context, R.color.gray_200),
                ContextCompat.getColor(context, R.color.gray_300))
                .apply {
                    id = R.id.lazy_loader
                    animDuration = 750
                    firstDelayDuration = 150
                    secondDelayDuration = 250
                    interpolator = DecelerateInterpolator()
                }

            botLoadingBinding.llLoading.addView(lazyLoader)
        }
    }
}
