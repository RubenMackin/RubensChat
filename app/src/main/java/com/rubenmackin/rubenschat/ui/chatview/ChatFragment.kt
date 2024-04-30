package com.rubenmackin.rubenschat.ui.chatview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rubenmackin.rubenschat.R
import com.rubenmackin.rubenschat.databinding.FragmentChatBinding
import com.rubenmackin.rubenschat.domain.model.MessageModel
import com.rubenmackin.rubenschat.ui.chatview.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val viewmodel by viewModels<ChatViewModel>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)
        binding.ivBack.setOnClickListener {
            viewmodel.logout { findNavController().navigate(R.id.action_chat_fragment_to_main_fragment) }

        }

        binding.btnSendMsg.setOnClickListener {
            val msg = binding.etChat.text.toString()
            if (msg.isNotEmpty()) {
                viewmodel.sendMessage(msg)
            } else {
                Toast.makeText(context, "No puede estar el mensaje vacio", Toast.LENGTH_SHORT)
                    .show()
            }

            binding.etChat.text.clear()

        }

        setUpUI()

        return binding.root
    }

    private fun setUpUI() {
        setUpMessages()
        //Enganchar al flow
        subscriteToMessages()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        binding.tvTitle.text = viewmodel.name
    }

    private fun setUpMessages() {
        chatAdapter = ChatAdapter(mutableListOf())
        binding.rvMsg.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscriteToMessages() {
        lifecycleScope.launch {
            viewmodel.messageList.collect {
                setUpToolbar()
                chatAdapter.updateList(it.toMutableList(), viewmodel.name)
                binding.rvMsg.scrollToPosition(chatAdapter.messageList.size - 1)
            }
        }
    }

}