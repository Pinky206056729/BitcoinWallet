package com.fixer.btc.presentation.vm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fixer.btc.presentation.vm.components.CurrencyItem
import com.fixer.btc.presentation.vm.components.NumericTextField

@Composable
fun CurrencyListScreen(
    viewModel: BitcoinRateCheckViewModel
) {
    val state = viewModel.uiState.collectAsState()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        NumericTextField(
            value = viewModel.number.value,
            onValueChange = viewModel::onNumberChanged,
            buttonText = "Go",
            onButtonClick = viewModel::onButtonClick,
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)) {
                items(state.value.currency) {
                    CurrencyItem(currency = it)
                }
            }
            if (state.value.error?.isNotBlank() == true) {
                Text(
                    text = state.value.error!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.value.loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}