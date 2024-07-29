package com.plcoding.cryptocurrencyappyt.presentation.screens.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_cases.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    val state: State<CoinListState>
        get() = _state

    private var _state = mutableStateOf(CoinListState())

    //TODO: tentar mudar isso para um StateFlow igual no boomerang
//    val state: StateFlow<CoinListState>
//        get() = _state.asStateFlow()
//
//    private var _state = MutableStateFlow(CoinListState())

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
//                    _state.value = _state.value.copy(coins = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value =
                        CoinListState(error = result.message ?: "An unexpected error occurred")
//                    _state.value = _state.value.copy(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
//                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}