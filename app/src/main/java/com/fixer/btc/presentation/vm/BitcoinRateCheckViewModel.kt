package com.fixer.btc.presentation.vm

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fixer.btc.domain.model.CurrentRateUiModel
import com.fixer.btc.domain.model.use_case.GetConvertedCurrencies
import com.fixer.btc.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BitcoinRateCheckViewModel @Inject constructor(
    private val getConvertedCurrencies: GetConvertedCurrencies,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrentRateUiModel())
    val uiState= _uiState.asStateFlow()

    val number = mutableStateOf(TextFieldValue())

    fun onNumberChanged(value: TextFieldValue) {
        number.value = value
    }

     fun onButtonClick() {
         viewModelScope.launch {
             _uiState.value = CurrentRateUiModel(loading = true)
             getConverted(number.value.text.toDouble())
         }
    }

    init {
        if (savedStateHandle.contains("amount")) {
            viewModelScope.launch {
                savedStateHandle.get<Double>("amount")?.toDouble()?.let { getConverted(it) }
            }
        }
    }

    private suspend fun getConverted(amount: Double) {

        when (val result = getConvertedCurrencies.invoke(amount)) {
            is Resource.Error -> {
                _uiState.value = CurrentRateUiModel(error = result.message)
            }

            is Resource.Loading -> {
                _uiState.value = CurrentRateUiModel(loading = true)
            }

            is Resource.Success -> {
                _uiState.value = CurrentRateUiModel(currency = result.data?: emptyList())
            }
        }


        savedStateHandle["amount"] = amount
    }
}