package com.example.currencytracking.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencytracking.databinding.FragmentPopularBinding
import com.example.currencytracking.databinding.SettingsFragmentBinding
import com.example.currencytracking.ui.popular.PopularViewModel
import com.example.currencytracking.utils.bindingLifecycleError
import com.example.currencytracking.utils.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding ?: bindingLifecycleError()

    private val settingViewModel by viewModel(SettingsViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingViewModel.getSortingSettings()
        sortByAlphabetical()
        sortByValue()
    }

    private fun sortByValue() {
        viewLifecycleOwner.lifecycleScope.launch {
            settingViewModel.ascendingOrder.collect {
                binding.ascendingOrder.isChecked = it
                binding.descendingOrder.isChecked = !it
            }
        }

        binding.ascendingOrder.setOnCheckedChangeListener { _, value ->
            settingViewModel.setAscendingOrderSetting(value)
        }
    }

    private fun sortByAlphabetical() {
        viewLifecycleOwner.lifecycleScope.launch {
            settingViewModel.alphabeticalSorting.collect {
                binding.alphabeticalSorting.isChecked = it
                binding.valueSorting.isChecked = !it
            }
        }

        binding.alphabeticalSorting.setOnCheckedChangeListener { _, value ->
            settingViewModel.setAlphabeticalSortingSetting(value)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}