package ru.anlyashenko.atmosphereapp.core.design_system.elements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    initialHour: Int = 18,
    initialMinute: Int = 40,
    itemHeight: Dp = 48.dp,
    visibleItemsCount: Int = 3,
    onTimeChanged: (hour: Int, minute: Int) -> Unit
) {
    var selectedHour by remember { mutableIntStateOf(initialHour) }
    var selectedMinute by remember { mutableIntStateOf(initialMinute) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WheelPickerCore(
            items = (0..23).toList(),
            currentValue = selectedHour,
            onValueChange = {
                selectedHour = it
                onTimeChanged(selectedHour, selectedMinute)
            },
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            format = { "%02d".format(it) }
        )

        WheelPickerCore(
            items = (0..59).toList(),
            currentValue = selectedMinute,
            onValueChange = {
                selectedMinute = it
                onTimeChanged(selectedHour, selectedMinute)
            },
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            format = { "%02d".format(it) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WheelPickerCore(
    items: List<Int>,
    currentValue: Int,
    onValueChange: (Int) -> Unit,
    itemHeight: Dp,
    visibleItemsCount: Int,
    format: (Int) -> String
) {
    val paddedItems = remember(items) {
        val padding = visibleItemsCount / 2
        val emptyList = List(padding) { null }
        emptyList + items + emptyList
    }

    val initialIndex = remember { items.indexOf(currentValue).coerceAtLeast(0) }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                val selectedItem = paddedItems.getOrNull(index + visibleItemsCount / 2)
                if (selectedItem != null && selectedItem != currentValue) {
                    onValueChange(selectedItem)
                }
            }
    }

    LaunchedEffect(currentValue) {
        val targetIndex = items.indexOf(currentValue)
        if (targetIndex != -1 && listState.firstVisibleItemIndex != targetIndex) {
            coroutineScope.launch { listState.animateScrollToItem(targetIndex) }
        }
    }

    Box(
        modifier = Modifier
            .height(itemHeight * visibleItemsCount)
            .width(64.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = Modifier.fillMaxSize()
        ) {
            items(paddedItems.size) { index ->
                val item = paddedItems[index]
                val isSelected = index == listState.firstVisibleItemIndex + visibleItemsCount / 2

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (item != null) {
                        Text(
                            text = format(item),
                            fontSize = if (isSelected) 32.sp else 24.sp,
                            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
    }
}