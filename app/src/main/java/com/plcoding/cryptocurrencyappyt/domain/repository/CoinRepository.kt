package com.plcoding.cryptocurrencyappyt.domain.repository

import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail

interface CoinRepository {

    suspend fun getAllCoins(): List<Coin>

    suspend fun getCoinById(coinId: String): CoinDetail
}