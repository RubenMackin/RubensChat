package com.rubenmackin.rubenschat.ui.chatview.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.rubenmackin.rubenschat.databinding.ItemChatMeBinding
import com.rubenmackin.rubenschat.databinding.ItemChatOtherBinding
import com.rubenmackin.rubenschat.domain.model.MessageModel

class ChatViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(messageModel: MessageModel, itemViewType: Int) {
        when (itemViewType) {
            ChatAdapter.SENT_MESSAGE -> bindSentMessage(messageModel)
            ChatAdapter.RECEIVED_MESSAGE -> bindReceivedMessage(messageModel)
        }
    }

    private fun bindReceivedMessage(messageModel: MessageModel) {
        val currentBinding = binding as ItemChatOtherBinding
        currentBinding.tvDate.text = messageModel.date
        currentBinding.tvChat.text = messageModel.msg
        currentBinding.tvName.text = messageModel.user.userName
        currentBinding.tvHour.text = messageModel.hour

        //IS ADMIN PONER UNA ESTRELLA SI ES TRUE
    }

    private fun bindSentMessage(messageModel: MessageModel) {
        val currentBinding = binding as ItemChatMeBinding

        currentBinding.tvDateMe.text = messageModel.date
        currentBinding.tvChatMe.text = messageModel.msg
        currentBinding.tvHour.text = messageModel.hour

        //IS ADMIN PONER UNA ESTRELLA SI ES TRUE
    }
}