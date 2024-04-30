package com.rubenmackin.rubenschat.ui.mainview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rubenmackin.rubenschat.R
import com.rubenmackin.rubenschat.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.btnChat.setOnClickListener {
            if (!binding.tietName.text.isNullOrEmpty()) {
                viewModel.saveNickName(binding.tietName.text.toString())
                findNavController().navigate(R.id.action_main_fragment_to_chat_fragment)
            } else {
                Toast.makeText(context, "Falta usuario", Toast.LENGTH_SHORT).show()
            }
        }

        subscribeToState()

        return binding.root
    }

    private fun subscribeToState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    MainViewState.LOADING -> {
                        binding.pbLoading.isVisible = true
                    }

                    MainViewState.REGISTERED -> {
                        findNavController().navigate(R.id.action_main_fragment_to_chat_fragment)
                    }

                    MainViewState.UNREGISTERED -> binding.pbLoading.isVisible = false
                }
            }
        }
    }

}