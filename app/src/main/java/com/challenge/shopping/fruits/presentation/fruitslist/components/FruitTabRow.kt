package com.challenge.shopping.fruits.presentation.fruitslist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.challenge.shopping.fruits.common.presentation.DesertWhite
import com.challenge.shopping.fruits.common.presentation.Red
import com.challenge.shopping.fruits.presentation.fruitslist.FruitsTab

@Composable
fun FruitTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier
            .padding(top = 12.dp)
            .widthIn(max = 700.dp)
            .fillMaxWidth(),
        containerColor = DesertWhite,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                color = Red,
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        }
    ) {
        FruitsTab.entries.forEachIndexed { index, tab ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                selectedContentColor = Red,
                unselectedContentColor = Color.Black.copy(alpha = 0.5f)
            ) {
                Text(
                    text = stringResource(tab.titleRes),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
    }
}