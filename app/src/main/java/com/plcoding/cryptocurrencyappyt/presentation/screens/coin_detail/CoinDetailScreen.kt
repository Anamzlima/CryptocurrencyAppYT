package com.plcoding.cryptocurrencyappyt.presentation.screens.coin_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.presentation.screens.coin_detail.components.CoinTag
import com.plcoding.cryptocurrencyappyt.presentation.screens.coin_detail.components.TeamListItem

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Box(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(20.dp)
    ) {
        state.coin?.let { coin ->
            CoinDetailTitle(coinDetail = coin)

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = coin.description,
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Tags",
                style = MaterialTheme.typography.h3
            )

            Spacer(modifier = Modifier.height(15.dp))

            FlowRow(
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                coin.tags.forEach{ tag ->
                    CoinTag(tag = tag)

                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Team members",
                style = MaterialTheme.typography.h3
            )

            Spacer(modifier = Modifier.height(15.dp))

            coin.team.forEach{teamMember ->
                TeamListItem(
                    teamMember = teamMember,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Divider()
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun CoinDetailTitle(
    coinDetail: CoinDetail,

) {
    coinDetail.let { coin ->
        Column(
            modifier = Modifier
                .padding(PaddingValues(20.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "${coin.rank}. " + "${coin.name} " + "(${coin.symbol})",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.weight(8f)
                )
                Text(
                    text = if (coin.isActive) "active" else "not active",
                    color = if (coin.isActive) Color.Green else Color.Red,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}