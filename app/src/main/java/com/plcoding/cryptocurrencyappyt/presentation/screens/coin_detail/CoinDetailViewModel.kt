package com.plcoding.cryptocurrencyappyt.presentation.screens.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Constants.PARAM_COIN_ID
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_cases.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: State<CoinDetailState>
        get() = _state

    private var _state = mutableStateOf(CoinDetailState())

    //TODO: tentar mudar isso para um StateFlow igual no boomerang
//    val state: StateFlow<CoinListState>
//        get() = _state.asStateFlow()
//
//    private var _state = MutableStateFlow(CoinListState())

    init {
        savedStateHandle.get<String>(PARAM_COIN_ID)?.let { coinId ->
            getCoinById(coinId)
        }
    }

    private fun getCoinById(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinDetailState(coin = result.data)
//                    _state.value = _state.value.copy(coins = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value =
                        CoinDetailState(error = result.message ?: "An unexpected error occurred")
//                    _state.value = _state.value.copy(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)
//                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}