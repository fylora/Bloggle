package com.fylora.blog.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.blog.presentation.search.components.AccountComp
import com.fylora.blog.presentation.search.components.SearchTextField
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.DarkBackground
import com.fylora.core.ui.theme.LightGray

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToAccount: (userId: String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column {
        Box(
            modifier = Modifier
                .background(DarkBackground)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { focusManager.clearFocus() }
                ),
        ) {
            SearchTextField(
                value = viewModel.query.value,
                isHintVisible = viewModel.isHintVisible.value,
                onValueChange = {
                    viewModel.onEvent(
                        SearchEvent.OnSearchChange(it)
                    )
                },
                onFocusChange = {
                    viewModel.onEvent(
                        SearchEvent.OnFocusChange(it)
                    )
                },
                onSearch = {
                    viewModel.onEvent(
                        SearchEvent.OnSearch
                    )
                },
                modifier = Modifier.padding(
                    vertical = 35.dp,
                    horizontal = 20.dp
                )
            )
        }

        Box(modifier = Modifier.weight(1f)){
            LazyColumn {
                items(
                    items = viewModel.accounts.value,
                    key = { account -> account.userId }
                ) { account ->
                    AccountComp(username = account.username) {
                        onNavigateToAccount(
                            account.userId
                        )
                    }
                }
            }

            if (viewModel.error.value.isNotBlank()) {
                Text(
                    text = viewModel.error.value,
                    textAlign = TextAlign.Center,
                    color = LightGray,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }

    }
}